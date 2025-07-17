package com.yeshi.tjs.core

import com.yeshi.tjs.util.LOG
import com.yeshi.tjs.util.RequestAttributes
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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
    private val log = LOG

    init
    {
        log.info("加载入口过滤器")
    }

    override fun doFilter(req: HttpServletRequest, rsp: HttpServletResponse?, chain: FilterChain?)
    {
        log.trace("请求到达过滤器")
        val now = Instant.now()
        req.setAttribute(RequestAttributes.KEY_REQUEST_TIME, now)
        log.debug("Request: {} {} {}", now, req.method, req.requestURI)
        super.doFilter(req, rsp, chain)
    }
}
