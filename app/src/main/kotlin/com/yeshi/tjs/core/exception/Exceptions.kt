package com.yeshi.tjs.core.exception

import org.springframework.context.ApplicationEvent
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponseException
import java.time.Clock

/** # 未捕获异常事件 */
class UncaughtExceptionEvent(source: Exception) : ApplicationEvent(source, Clock.systemDefaultZone())

/** # 服务异常 */
open class ServiceException(
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    body: ProblemDetail = ProblemDetail.forStatus(status),
    cause: Throwable? = null,
    code: String? = null,
    args: Array<out Any>? = null,
) : ErrorResponseException(status, body, cause, code, args)

/**
 * # 已处理完成声明
 *
 * 声明某件事已经完成，用于跳出处理程序，**并不是真正的异常**
 */
open class CompletedNoticeException() : RuntimeException()
