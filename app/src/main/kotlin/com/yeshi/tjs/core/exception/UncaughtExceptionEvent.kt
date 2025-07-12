package com.yeshi.tjs.core.exception

import org.springframework.context.ApplicationEvent
import java.time.Clock

/** # 未捕获异常事件 */
class UncaughtExceptionEvent(source: Exception) : ApplicationEvent(source, Clock.systemUTC())
