package com.yeshi.tjs.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
object ContextUtils : ApplicationContextAware
{
    private lateinit var context: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext)
    {
        context = applicationContext
    }

    fun <T> getBean(clazz: Class<T>) = context.getBean(clazz)

    fun getBean(name: String) = context.getBean(name)
}
