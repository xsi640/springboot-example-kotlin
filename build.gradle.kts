import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
}

allprojects {

    apply {
        plugin("java")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("kotlin")
        plugin("kotlin-spring")
    }

    group = "com.github.xsi640"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_1_8

    val user = System.getProperty("repoUser")
    val pwd = System.getProperty("repoPassword")

    repositories {
        mavenLocal()
        maven {
            credentials {
                username = user
                password = pwd
            }
            url = uri("http://172.16.11.231:8081/nexus/repository/maven2-group/")
        }
    }

    val vers = mapOf(
        "cglib" to "3.3.0",
        "commons_io" to "2.6",
        "commons_codec" to "1.15",
        "commons_lang" to "3.9"
    )

    dependencies {
        implementation("commons-io:commons-io:${vers["commons_io"]}")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}