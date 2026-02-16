package com.bookpin.infrastructure.logging

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "discord.webhook")
data class DiscordProperties(
    val url: String = "",
    val enabled: Boolean = true
)
