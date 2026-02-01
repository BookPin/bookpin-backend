package com.bookpin.domain.notification

interface NotificationClient {
    fun send(message: NotificationMessage): NotificationResult
    fun sendToMultiple(messages: List<NotificationMessage>): List<NotificationResult>
}
