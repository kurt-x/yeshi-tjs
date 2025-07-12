package com.yeshi.tjs.util

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
object AppUtils : ApplicationContextAware
{
    private lateinit var context: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext)
    {
        context = applicationContext
    }

    @Throws(BeansException::class)
    fun <T> getBean(clazz: Class<T>) = context.getBean(clazz)

    @Throws(BeansException::class)
    @Suppress("UNCHECKED_CAST")
    fun <T> getBean(name: String) = context.getBean(name) as T
}
