package com.yeshi.tjs.core

import com.yeshi.tjs.util.RequestAttributes
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant

/**
 * # 入口过滤器
 *
 * **拥有最高优先级**
 */
@Component
class EntryFilter : HttpFilter()
{
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun doFilter(req: HttpServletRequest, rsp: HttpServletResponse?, chain: FilterChain?)
    {
        val now = Instant.now()
        req.setAttribute(RequestAttributes.KEY_REQUEST_TIME, now)
        log.debug("Request: {} {} {}", now, req.method, req.requestURI)
        super.doFilter(req, rsp, chain)
    }
}
