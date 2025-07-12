package com.yeshi.tjs.core.exception

import com.yeshi.tjs.core.constants.EXCEPTION_NAME_PATTERN
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponseException

/** # 服务异常 */
open class ServiceException(
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    body: ProblemDetail = ProblemDetail.forStatus(status),
    cause: Throwable? = null,
    private val suffix: String? = null,
    args: Array<out Any>? = null,
) : ErrorResponseException(status, body, cause, null, args)
{
    val name = EXCEPTION_NAME_PATTERN.matchEntire(this::class.simpleName!!)!!.groups["name"]!!.value

    override fun getTypeMessageCode() = "exception.type.$name"

    override fun getTitleMessageCode() = "exception.title.$name"

    override fun getDetailMessageCode() = "exception.$name" + if (suffix != null) ".$suffix" else ""
}
