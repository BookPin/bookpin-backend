package com.bookpin.infrastructure.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.IThrowableProxy
import ch.qos.logback.classic.spi.ThrowableProxyUtil
import ch.qos.logback.core.AppenderBase
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.time.Instant
import java.util.concurrent.Executors

class DiscordAppender : AppenderBase<ILoggingEvent>() {

    private val restTemplate = RestTemplate()
    private val executor = Executors.newSingleThreadExecutor()

    private val webhookUrl: String
        get() = ApplicationContextHolder.getProperty("discord.webhook.url") ?: ""

    private val profile: String
        get() = ApplicationContextHolder.getActiveProfile()

    override fun append(event: ILoggingEvent) {
        val url = webhookUrl
        if (url.isBlank()) {
            return
        }

        executor.submit {
            try {
                sendToDiscord(event, url)
            } catch (e: Exception) {
                println("Failed to send log to Discord: ${e.message}")
            }
        }
    }

    private fun sendToDiscord(event: ILoggingEvent, url: String) {
        val embed = buildEmbed(event)
        val payload = mapOf("embeds" to listOf(embed))

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val request = HttpEntity(payload, headers)
        restTemplate.postForEntity(url, request, String::class.java)
    }

    private fun buildEmbed(event: ILoggingEvent): Map<String, Any> {
        val fields = mutableListOf<Map<String, Any>>()

        fields.add(mapOf(
            "name" to "Environment",
            "value" to profile,
            "inline" to true
        ))

        fields.add(mapOf(
            "name" to "Level",
            "value" to event.level.toString(),
            "inline" to true
        ))

        fields.add(mapOf(
            "name" to "Logger",
            "value" to event.loggerName.takeLast(50),
            "inline" to true
        ))

        fields.add(mapOf(
            "name" to "Message",
            "value" to event.formattedMessage.take(1000),
            "inline" to false
        ))

        val throwableProxy = event.throwableProxy
        if (throwableProxy != null) {
            val stackTrace = formatThrowable(throwableProxy).take(1000)
            fields.add(mapOf(
                "name" to "Stack Trace",
                "value" to "```\n$stackTrace\n```",
                "inline" to false
            ))
        }

        val color = when (event.level.toString()) {
            "ERROR" -> 16711680  // Red
            "WARN" -> 16776960   // Yellow
            else -> 3447003      // Blue
        }

        return mapOf(
            "title" to "${event.level} Alert",
            "color" to color,
            "fields" to fields,
            "timestamp" to Instant.ofEpochMilli(event.timeStamp).toString()
        )
    }

    private fun formatThrowable(throwableProxy: IThrowableProxy): String {
        return ThrowableProxyUtil.asString(throwableProxy)
    }

    override fun stop() {
        super.stop()
        executor.shutdown()
    }
}
