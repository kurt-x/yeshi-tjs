package com.yeshi.tjs.pojo.event

import org.springframework.context.ApplicationEvent
import java.time.Clock

/**
 * # 登录事件
 *
 * @param type 登录认证类型（作为事件源）
 * @param principal 账户
 * @param credentials 登录凭证
 */
class LoginEvent(type: String, val principal: String, val credentials: String) :
    ApplicationEvent(type, Clock.systemUTC())
{
    /** 登录成功后生成的 token，不可为空，若登录失败或生成 token 失败必须抛出异常 */
    lateinit var token: String
}
