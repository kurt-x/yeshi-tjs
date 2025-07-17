package com.yeshi.tjs.core.exception

import com.yeshi.tjs.util.LOG
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/** # 全局异常处理器 */
@RestControllerAdvice
class GlobalExceptionHandler(messageSource: MessageSource, private val publisher: ApplicationEventPublisher) :
    ResponseEntityExceptionHandler()
{
    private val log = LOG

    init
    {
        log.info("加载全局异常处理器")
        setMessageSource(messageSource)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(CompletedNoticeException::class)
    fun handleCompletedNotice() = Unit

    /** 保底异常处理 */
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleUncaught(e: Exception, request: HttpServletRequest) =
        runCatching {
            log.error("--\uD83D\uDD25 Uncaught exception", e)
            publisher.publishEvent(UncaughtExceptionEvent(e))
        }.onFailure {
            System.err.printf("--\uD83D\uDEA8 Logging failed: %s%n", it.message)
            it.printStackTrace(System.err)
        }.let {
            messageSource!!.getMessage(
                "exception",
                null,
                "Server error",
                request.locale ?: LocaleContextHolder.getLocale()
            )
        }

    override fun handleHandlerMethodValidationException(
        e: HandlerMethodValidationException, headers: HttpHeaders, status: HttpStatusCode, req: WebRequest
    ): ResponseEntity<Any?>?
    {
        if (log.isDebugEnabled)
        {
            val validation = e.parameterValidationResults[0]
            log.debug(
                """
                验证失败信息：
                方法：{}
                参数名：{}，传入值：{}
            """.trimIndent(),
                e.method, validation.methodParameter.getParameterName(), validation.argument
            )
        }
        return super.handleHandlerMethodValidationException(e, headers, status, req)
    }
}
