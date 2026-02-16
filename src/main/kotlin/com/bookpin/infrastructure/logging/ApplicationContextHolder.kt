package com.bookpin.infrastructure.logging

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class ApplicationContextHolder : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        Companion.applicationContext = applicationContext
    }

    companion object {
        private var applicationContext: ApplicationContext? = null

        fun getBean(clazz: Class<*>): Any? {
            return applicationContext?.getBean(clazz)
        }

        fun getProperty(key: String): String? {
            return applicationContext?.environment?.getProperty(key)
        }

        fun getActiveProfile(): String {
            return applicationContext?.environment?.activeProfiles?.firstOrNull() ?: "unknown"
        }
    }
}
