import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.spring") version "2.2.0"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "7.0.3.Final"
}

val env = project.findProperty("env") as? String ?: "prod"

// hibernate 字节码增强
hibernate {
    enhancement {
        enableLazyInitialization = true     // 懒加载
        enableDirtyTracking = true          // 动态更新字段
        enableAssociationManagement = true  // 自动关联关系管理
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    sourceSets.main {
        // 排除非指定环境的 application.yml 配置文件
        resources.exclude {
            it.name.run { Regex("application-\\w+\\.yml").matches(this) && this != "application-$env.yml" }
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Jakarta
    implementation("jakarta.persistence", "jakarta.persistence-api", "3.2.0")

    // Spring
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-validation")
    implementation("org.springframework.boot", "spring-boot-starter-security")
    implementation("org.springframework.boot", "spring-boot-starter-data-redis")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-cache")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    testImplementation("org.springframework.security", "spring-security-test")
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    developmentOnly("org.springframework.boot", "spring-boot-devtools")
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor")

    // Mybatis
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.4")

    // Jackson
    implementation("com.fasterxml.uuid", "java-uuid-generator", "5.1.0")

    // Jose4j
    implementation("org.bitbucket.b_c", "jose4j", "0.9.6")

    // Postgres
    runtimeOnly("org.postgresql", "postgresql")

    // Junit
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

springBoot.mainClass = "com.yeshi.tjs.AppKt"

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.processResources {
    filesMatching("**/application.yml") {
        filteringCharset = "UTF-8"
        filter<ReplaceTokens>(
            "tokens" to mapOf(
                "project-name" to rootProject.name,
                "project-version" to rootProject.version,
            )
        )
    }
    filesMatching("banner.txt") {
        filteringCharset = "UTF-8"
        filter<ReplaceTokens>("tokens" to mapOf("project-version" to rootProject.version))
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
