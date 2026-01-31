package com.bookpin.infrastructure.notification

import com.bookpin.domain.notification.NotificationClient
import com.bookpin.domain.notification.NotificationMessage
import com.bookpin.domain.notification.NotificationResult
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(name = ["firebase.enabled"], havingValue = "true", matchIfMissing = false)
class FcmNotificationAdapter(
    private val firebaseMessaging: FirebaseMessaging
) : NotificationClient {

    companion object {
        private val logger = LoggerFactory.getLogger(FcmNotificationAdapter::class.java)
    }

    override fun send(message: NotificationMessage): NotificationResult {
        return try {
            val fcmMessage = buildFcmMessage(message)
            val messageId = firebaseMessaging.send(fcmMessage)
            NotificationResult(success = true, messageId = messageId)
        } catch (exception: Exception) {
            handleSendError(exception)
        }
    }

    override fun sendToMultiple(messages: List<NotificationMessage>): List<NotificationResult> {
        return messages.map { send(it) }
    }

    private fun buildFcmMessage(message: NotificationMessage): Message {
        return Message.builder()
            .setToken(message.token)
            .setNotification(buildNotification(message))
            .putAllData(message.data)
            .build()
    }

    private fun buildNotification(message: NotificationMessage): Notification {
        return Notification.builder()
            .setTitle(message.title)
            .setBody(message.body)
            .build()
    }

    private fun handleSendError(exception: Exception): NotificationResult {
        logger.error("Failed to send FCM notification", exception)
        return NotificationResult(success = false, errorMessage = exception.message)
    }
}
