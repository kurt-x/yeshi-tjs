package com.yeshi.tjs

import com.yeshi.tjs.core.constants.DATETIME_FORMATTER
import com.yeshi.tjs.util.LOCAL_IP4
import com.yeshi.tjs.util.LOG
import org.springframework.boot.SpringApplication
import org.springframework.boot.SpringApplicationRunListener
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import java.lang.management.ManagementFactory
import java.time.Duration
import java.time.LocalDateTime

@SpringBootApplication
class App

class BootListener(app: SpringApplication, args: Array<String?>?) : SpringApplicationRunListener
{
    private val mainLogger = app.mainApplicationClass.LOG

    init
    {
        if (mainLogger.isDebugEnabled) mainLogger.debug("加载启动监听器；命令行参数：{}", args.contentToString())
    }

    override fun ready(context: ConfigurableApplicationContext, timeTaken: Duration)
    {
        if (!mainLogger.isInfoEnabled) return

        val env = context.environment
        val osInfo = ManagementFactory.getOperatingSystemMXBean()
        val jvmInfo = ManagementFactory.getRuntimeMXBean()

        val projectName = env.getProperty("spring.application.name", "project")
        val protocol = if (env.getProperty("server.ssl.enabled") in arrayOf("true", "on")) "https" else "http"
        val port = env.getProperty("server.port", "8080")

        mainLogger.info(
            """

                ---------------------------------------------------------------------------
                    (♥◠‿◠)ﾉﾞ [ $projectName ] 启动成功！ ლ(´ڡ`ლ)ﾞ
                         Version: ${env.getProperty("spring.application.version", "unknown")}
                       Java info: ${jvmInfo.vmName} v${jvmInfo.vmVersion}
                     Kotlin info: v${KotlinVersion.CURRENT}
                     System info: ${osInfo.name} ${osInfo.arch}
                    Started time: ${DATETIME_FORMATTER.format(LocalDateTime.now())}
                             PID: ${jvmInfo.pid}
                             Url: $protocol://$LOCAL_IP4:$port
                ---------------------------------------------------------------------------

                """.trimIndent()
        )
    }
}

// @fmt:off
fun main(args: Array<String>) { runApplication<App>(*args) }
