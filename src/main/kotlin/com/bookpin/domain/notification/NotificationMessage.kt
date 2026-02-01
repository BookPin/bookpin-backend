package com.bookpin.domain.notification

data class NotificationMessage(
    val token: String,
    val title: String,
    val body: String,
    val data: Map<String, String> = emptyMap()
) {
    init {
        require(token.isNotBlank()) { "FCM token must not be blank" }
        require(title.isNotBlank()) { "Notification title must not be blank" }
        require(body.isNotBlank()) { "Notification body must not be blank" }
    }
}
