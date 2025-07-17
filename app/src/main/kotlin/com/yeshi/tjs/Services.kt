package com.yeshi.tjs

import com.yeshi.tjs.util.LOG
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceResolvable
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import java.util.*

/** # 通用服务 */
@Service
class MainService

/** # 国际化消息服务 */
@Service
class MessageService(
    private val messageSource: MessageSource,
    private val request: HttpServletRequest,
)
{
    init
    {
        LOG.info("加载国际化消息服务")
    }

    /** @see MessageSource.getMessage */
    fun getMessage(code: String, args: Array<Any?>?, defaultMessage: String?) =
        messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale())

    /** @see MessageSource.getMessage */
    @Throws(NoSuchMessageException::class)
    fun getMessage(code: String, args: Array<Any?>?) = messageSource.getMessage(code, args, this.locale)

    /** @see MessageSource.getMessage */
    @Throws(NoSuchMessageException::class)
    fun getMessage(resolvable: MessageSourceResolvable) = messageSource.getMessage(resolvable, this.locale)

    /** 从 HTTP 请求获取 [Locale]，若为 `null`，继续从 [LocaleContextHolder.getLocale] 获取 */
    val locale: Locale get() = request.locale ?: LocaleContextHolder.getLocale()
}
