package com.github.xsi640.springboot.example.jpa

import com.github.xsi640.springboot.example.jpa.domain.Person
import com.github.xsi640.springboot.example.jpa.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*
import javax.persistence.EntityManager
import javax.sql.DataSource
import kotlin.random.Random


@SpringBootApplication
class Application : CommandLineRunner {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun run(vararg args: String?) {
//        jdbcTemplate.query(
//            { con ->
//                con.autoCommit = false
//                val stat =
//                    con.prepareStatement("select * from test", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
//                stat.fetchSize = 100
//                stat.fetchDirection = ResultSet.FETCH_FORWARD
//                stat
//            }, { rs ->
//                println(rs.getString(1))
//            }
//        )


    }


    @Transactional
    fun batchTest() {
        val persons = mutableListOf<Person>()
        val r = Random(100)
        for (i in 0 until 10000) {
            persons.add(
                Person(
                    id = id,
                    name = "aaa" + r.nextInt(),
                    age = r.nextInt(),
                    birthday = Date()
                )
            )
            id++
        }
        personRepository.saveAll(persons)
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
@EntityScan(basePackages = ["com.github.xsi640.springboot.example.jpa.domain"]) //1、实体扫描
//2、实体管理ref
//3、事务管理
@EnableJpaRepositories(
    basePackages = ["com.github.xsi640.springboot.example.jpa.repository"],
    entityManagerFactoryRef = "thirdEntityManagerFactoryBean",
    transactionManagerRef = "thirdTransactionManager"
)
@EnableTransactionManagement
class JpaThirdConfiguration {
    /**
     * 第二个数据源
     * @return
     */
    @Bean(name = ["dataSourceThird"])
    @ConfigurationProperties(prefix = "spring.datasource.third")
    fun dataSourceThird(): DataSource {
        return DataSourceBuilder.create().build()
    }

    //第三个数据源，必须加Qualifier
    @Autowired
    @Qualifier("dataSourceThird")
    private val dataSource: DataSource? = null

    //jpa其他参数配置
    @Autowired
    private val jpaProperties: JpaProperties? = null

    //实体管理工厂builder
    @Autowired
    private val factoryBuilder: EntityManagerFactoryBuilder? = null

    /**
     * 配置第二个实体管理工厂的bean
     * @return
     */
    @Bean(name = ["thirdEntityManagerFactoryBean"])
    fun entityManagerFactoryBean(): LocalContainerEntityManagerFactoryBean {
        return factoryBuilder!!.dataSource(dataSource)
            .properties(vendorProperties)
            .packages("com.github.xsi640.springboot.example.jpa.domain")
            .persistenceUnit("thirdPersistenceUnit")
            .build()
    }

    @Autowired
    private val hibernateProperties: HibernateProperties? = null

    // 为了解决分页查询时的错误
    // 因为使用的是clicakhouse数据库，但是没有clickhouse数据库的方言，但是进行分页查询时候，就需要设置一个方言
    // 又因为MySQLDialect的方言差不多，于是就设置了MySQLDialect
    private val vendorProperties: Map<String, Any>
        private get() {
            val properties = jpaProperties!!.properties

            // 为了解决分页查询时的错误
            // 因为使用的是clicakhouse数据库，但是没有clickhouse数据库的方言，但是进行分页查询时候，就需要设置一个方言
            // 又因为MySQLDialect的方言差不多，于是就设置了MySQLDialect
            properties["hibernate.dialect"] = "org.hibernate.dialect.MySQLDialect"
            return hibernateProperties!!.determineHibernateProperties(
                properties, HibernateSettings()
            )
        }

    /**
     * EntityManager不过解释，用过jpa的应该都了解
     * @return
     */
    @Bean(name = ["thirdEntityManager"])
    fun entityManager(): EntityManager {
        return entityManagerFactoryBean().getObject()!!.createEntityManager()
    }

    /**
     * jpa事务管理
     * @return
     */
    @Bean(name = ["thirdTransactionManager"])
    fun transactionManager(): JpaTransactionManager {
        val jpaTransactionManager = JpaTransactionManager()
        jpaTransactionManager.entityManagerFactory = entityManagerFactoryBean().getObject()
        return jpaTransactionManager
    }
}