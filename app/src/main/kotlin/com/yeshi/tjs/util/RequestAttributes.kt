package com.yeshi.tjs.util

import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import java.time.Instant

/**
 * # 请求属性
 *
 * 操作当前请求绑定的属性
 */
object RequestAttributes
{
    /** 索引键：请求时间 */
    const val KEY_REQUEST_TIME: String = "request.time"

    /** 请求时间 */
    var requestTime: Instant?
        get() = get(KEY_REQUEST_TIME)
        set(value) = set(KEY_REQUEST_TIME, value!!)

    /**
     * @param key 键
     * @param <T> 值类型
     * @return 请求上下文中存储的值
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String) = attributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST) as T?

    /** 向请求上下文中存储值
     *
     * @param key   键
     * @param value 值
     */
    fun set(key: String, value: Any) = attributes().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST)

    private fun attributes() =
        RequestContextHolder.getRequestAttributes() ?: throw NullPointerException("未获取到 RequestAttributes")
}