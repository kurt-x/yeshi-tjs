package com.yeshi.tjs.core

import com.yeshi.tjs.core.constants.AUTH_TYPE_EMAIL
import com.yeshi.tjs.core.constants.AUTH_TYPE_PASSWORD
import com.yeshi.tjs.core.constants.AUTH_TYPE_SMS
import com.yeshi.tjs.core.constants.HEADER_AUTH_TYPE
import com.yeshi.tjs.core.exception.CompletedNoticeException
import com.yeshi.tjs.pojo.event.LoginEvent
import com.yeshi.tjs.util.LOG
import jakarta.validation.constraints.NotBlank
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * # 主控制器
 *
 * 包含一些通用的接口
 */
@RestController
@RequestMapping(name = "main")
class MainController(
    private val context: ApplicationContext,
)
{
    private val log = LOG

    init
    {
        log.info("加载主控制器")
    }

    @GetMapping("/", name = "index") fun hello() = "Hello!"

    /**
     * ### Ping
     *
     * 与 `ping` 命令相同
     */
    @RequestMapping("/", "/ping", name = "ping", method = [RequestMethod.HEAD]) fun ping() = Unit

    /**
     * ### 密码登录
     *
     * @param principal 账号（用户名/手机号/邮箱）
     * @param credentials 密码
     */
    @Validated
    @PostMapping(
        "/login",
        name = "loginByPassword",
        headers = ["$HEADER_AUTH_TYPE=$AUTH_TYPE_PASSWORD"],
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun loginByPassword(@NotBlank principal: String, @NotBlank credentials: String) =
        log.trace("密码登录：{}", principal).let {
            LoginEvent(AUTH_TYPE_PASSWORD, principal, credentials).run {
                context.publishEvent(this)
                token.apply { log.trace("登录成功: {}\ntoken: {}", principal, this) }
            }
        }

    /**
     * ### 短信登录
     *
     * @param principal 账号（手机号）
     * @param credentials 为空时表示需要获取验证码，不为空时应为用于登录的验证码
     */
    @Validated
    @PostMapping(
        "/login",
        name = "loginBySMS",
        headers = ["$HEADER_AUTH_TYPE=$AUTH_TYPE_SMS"],
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun loginBySMS(@NotBlank principal: String, @RequestParam(required = false) credentials: String?) =
        log.trace("短信登录：{}", principal).let {
            loginWithSender(AUTH_TYPE_SMS, principal, credentials) { TODO("发送短信") }
        }

    /**
     * ### 邮箱登录
     *
     * @param principal 账号（邮箱）
     * @param credentials 为空时表示需要获取验证码，不为空时应为用于登录的验证码
     */
    @Validated
    @PostMapping(
        "/login",
        name = "loginByPhone",
        headers = ["$HEADER_AUTH_TYPE=$AUTH_TYPE_EMAIL"],
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun loginByEmail(@NotBlank principal: String, @RequestParam(required = false) credentials: String?) =
        log.trace("邮件登录：{}", principal).let {
            loginWithSender(AUTH_TYPE_EMAIL, principal, credentials) { TODO("发送邮件") }
        }

    private inline fun loginWithSender(typ: String, prn: String, crd: String?, sender: () -> Unit) = crd?.let {
        LoginEvent(typ, prn, crd).run {
            context.publishEvent(this)
            token.apply { log.trace("登录成功: {}\ntoken: {}", prn, this) }
        }
    } ?: sender().let {
        log.trace("已发送验证码")
        throw CompletedNoticeException()
    }
}
