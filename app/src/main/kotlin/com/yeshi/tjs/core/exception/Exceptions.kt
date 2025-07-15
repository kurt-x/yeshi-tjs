package com.yeshi.tjs.core.exception

import org.springframework.context.ApplicationEvent
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponseException
import java.time.Clock

/** # 未捕获异常事件 */
class UncaughtExceptionEvent(source: Exception) : ApplicationEvent(source, Clock.systemUTC())

/** # 服务异常 */
open class ServiceException(
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    body: ProblemDetail = ProblemDetail.forStatus(status),
    cause: Throwable? = null,
    code: String? = null,
    args: Array<out Any>? = null,
) : ErrorResponseException(status, body, cause, code, args)
