package com.github.xsi640.springboot.example.invocation

import org.springframework.beans.factory.BeanClassLoaderAware
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinitionHolder
import org.springframework.beans.factory.support.AbstractBeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.annotation.AnnotationAttributes
import org.springframework.core.env.Environment
import org.springframework.core.type.AnnotationMetadata
import org.springframework.core.type.filter.AnnotationTypeFilter
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class ClassPathScanner(useDefaultFilters: Boolean) : ClassPathScanningCandidateComponentProvider(useDefaultFilters) {
    override fun isCandidateComponent(beanDefinition: AnnotatedBeanDefinition): Boolean {
        return beanDefinition.metadata.isIndependent
    }
}

class WebServiceRegistrar : ImportBeanDefinitionRegistrar, BeanClassLoaderAware {

    private var classpathScanner: ClassPathScanner = ClassPathScanner(false)
    private var classLoader: ClassLoader? = null

    init {
        classpathScanner.addIncludeFilter(AnnotationTypeFilter(WebService::class.java))
    }

    override fun setBeanClassLoader(classLoader: ClassLoader) {
        this.classLoader = classLoader
    }

    override fun registerBeanDefinitions(importingClassMetadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        val attributes = AnnotationAttributes.fromMap(
            importingClassMetadata.getAnnotationAttributes(
                EnableWebService::class.java.name
            )
        )!!
        val scanBasePackages = attributes["basePackages"] as Array<String>
        for (basePackage in scanBasePackages) {
            registryRestRepository(basePackage, registry)
        }
    }

    private fun registryRestRepository(basePackage: String, registry: BeanDefinitionRegistry) {
        val beanDefinitions = classpathScanner.findCandidateComponents(basePackage)
        for (beanDefinition in beanDefinitions) {
            try {
                val clazz = classLoader!!.loadClass(beanDefinition.beanClassName)
                val builder = BeanDefinitionBuilder.genericBeanDefinition(WebServiceBeanFactory::class.java)
                builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                builder.addPropertyValue("classLoader", classLoader)
                builder.addPropertyValue("objectType", clazz)
                val bean = builder.beanDefinition
                val holder = BeanDefinitionHolder(bean, beanDefinition.beanClassName!!)
                BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}

class WebServiceBeanFactory : FactoryBean<Any> {

    var classLoader: ClassLoader? = null
    private var objectType: Class<*>? = null

    override fun getObjectType(): Class<*>? {
        return objectType
    }

    fun setObjectType(clazz: Class<*>) {
        objectType = clazz;
    }

    @Autowired
    private lateinit var environment: Environment

    override fun getObject(): Any? {
        return Proxy.newProxyInstance(
            classLoader,
            arrayOf(objectType!!),
            WebServiceInvokerProxy(objectType!!, environment)
        )
    }
}

class WebServiceInvokerProxy(
    private val objectType: Class<*>,
    private val environment: Environment
) : InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<Any>?): Any {
        return "invoke " + method.name
    }
}