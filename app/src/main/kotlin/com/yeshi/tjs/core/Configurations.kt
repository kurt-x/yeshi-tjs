package com.yeshi.tjs.core

// ====================
//  配置
// ====================

import com.yeshi.tjs.core.constants.CACHE_DB_USER
import com.yeshi.tjs.util.LOG
import jakarta.validation.Validation
import org.apache.ibatis.annotations.Mapper
import org.hibernate.validator.HibernateValidator
import org.mybatis.spring.annotation.MapperScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.web.SecurityFilterChain
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.Executor

/** # 基础配置 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
class BaseConfiguration
{
    @Bean
    fun validatorFactory() =
        Validation.byProvider(HibernateValidator::class.java)
            .configure()
            .run {
                failFast(true)
                LOG.info("配置 Validation 快速失败")
                buildValidatorFactory()
            }!!

    @Bean fun validator() = validatorFactory().validator!!
}

/** # 异步配置 */
@Configuration
@EnableAsync(proxyTargetClass = true)
class AsyncConfiguration
{
    @Bean
    fun taskExecutor() = LOG.info("配置 Async 通用虚拟线程执行器").run {
        Executor { Thread.ofVirtual().name("task-", 0).start(it) }
    }
}

/** # 数据库配置 */
@Configuration
@EnableCaching(proxyTargetClass = true)
@EnableRedisRepositories("com.yeshi.tjs.cache")
@EnableJpaRepositories(basePackages = ["com.yeshi.tjs.module"])
@MapperScan(basePackages = ["com.yeshi.tjs.module"], annotationClass = Mapper::class)
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider", auditorAwareRef = "auditorAware")
class DatabaseConfiguration
{
    /** 配置 `Redis` 模板 */
    @Bean
    fun redisTemplate(factory: RedisConnectionFactory) = LOG.info("配置 Redis 模板").run {
        RedisTemplate<String, Any>().apply {
            connectionFactory = factory
            keySerializer = StringRedisSerializer()
            valueSerializer = GenericJackson2JsonRedisSerializer()
        }
    }

    /** 使用 [Instant.now] 获取当前时间 */
    @Bean
    fun auditingDateTimeProvider() = LOG.info("配置审计时间自动填充").run {
        DateTimeProvider { Optional.of(Instant.now()) }
    }

    /** 从 [Authentication] 中获取用户 ID */
    @Bean
    fun auditorAware() = LOG.info("配置审计人 ID 自动填充").run {
        AuditorAware<Long> {
            TODO("实现审计人 ID 自动填充")
        }
    }

    /** 配置缓存管理器 */
    @Bean
    fun redisCacheManager(factory: RedisConnectionFactory) = LOG.info("配置缓存管理器").run {
        RedisCacheManager.builder(factory)
            .withInitialCacheConfigurations(
                HashMap<String, RedisCacheConfiguration>().apply {
                    // 用户表缓存两小时
                    put(CACHE_DB_USER, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(2)))
                }
            )
            .build()
    }
}

/** # 安全配置 */
@Configuration
@EnableWebSecurity
class SecurityConfiguration
{
    @Bean
    fun passwordEncoder() = LOG.info("配置密码编码器").run {
        PasswordEncoderFactories.createDelegatingPasswordEncoder()!!
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = LOG.info("配置 Security 责任链").run {
        http
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.POST, "/login/*").permitAll()
                    .anyRequest().authenticated()
            }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            // TODO 添加 token 过滤器
            .build()
    }
}
