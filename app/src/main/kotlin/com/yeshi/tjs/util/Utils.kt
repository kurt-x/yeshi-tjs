package com.yeshi.tjs.util

// ====================
//  工具
// ====================

import com.fasterxml.uuid.Generators
import com.yeshi.tjs.core.constants.DEFAULT_LOCAL_IP4
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.aop.framework.AopContext
import java.net.InetAddress
import java.util.*

/**
 * ### 日志记录器
 *
 * 获取当前对象对应的日志记录器
 *
 * **每次使用都会从日志记录器工厂获取，需复用场景不要使用**
 */
inline val <reified T> T.LOG: Logger get() = LoggerFactory.getLogger(T::class.java)

/** ### 当前类的代理对象 */
inline val <reified T> T.CURRENT_PROXY get() = AopContext.currentProxy() as T

private val ipHeaders =
    sequenceOf("Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP")

/**
 * ### 本地 IPv4
 *
 * 优先获取非回环地址，获取不到时返回回环地址 [DEFAULT_LOCAL_IP4]
 */
val LOCAL_IP4 = runCatching { InetAddress.getLocalHost() }.getOrNull()?.hostAddress ?: DEFAULT_LOCAL_IP4

/** ### 远程 IP */
val HttpServletRequest.REMOTE_IP
    get() = getHeader("X-Forwarded-For")
        ?.takeIf { it.isValidIp }
        ?.split(",")
        ?.map { it.trim() }
        ?.getOrNull(0)
        ?: ipHeaders
            .mapNotNull { getHeader(it)?.trim() }
            .firstOrNull { it.isValidIp }
        ?: remoteAddr.takeIf { it.isValidIp }
        ?: LOCAL_IP4

private val String?.isValidIp get() = !this.isNullOrBlank() && !"unknown".equals(this, ignoreCase = true)

/** @return 6 位验证码 */
val VERIFICATION_CODE get() = (1..6).map { (0..9).random() }.joinToString("")

/** @return [UUID] v4 */
val UUID4 get() = UUID.randomUUID()!!

@Volatile private var lastTimestamp = 0L

/** @return [UUID]v7 */
val UUID7: UUID
    get() = run {
        var currentTime = System.currentTimeMillis()
        while (currentTime < lastTimestamp) currentTime = Thread.sleep(1).let { System.currentTimeMillis() }
        lastTimestamp = currentTime
        Generators.timeBasedEpochRandomGenerator().generate()
    }
