package com.bookpin.infrastructure.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException

@Configuration
@ConditionalOnProperty(name = ["firebase.enabled"], havingValue = "true", matchIfMissing = false)
class FcmConfig {

    @Value("\${firebase.credentials-path}")
    private lateinit var credentialsPath: String

    @Bean
    fun firebaseApp(): FirebaseApp {
        if (FirebaseApp.getApps().isNotEmpty()) {
            return FirebaseApp.getInstance()
        }
        val options = buildFirebaseOptions()
        return FirebaseApp.initializeApp(options)
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging {
        return FirebaseMessaging.getInstance(firebaseApp)
    }

    private fun buildFirebaseOptions(): FirebaseOptions {
        val credentials = loadCredentials()
        return FirebaseOptions.builder()
            .setCredentials(credentials)
            .build()
    }

    private fun loadCredentials(): GoogleCredentials {
        try {
            val resource = ClassPathResource(credentialsPath)
            return GoogleCredentials.fromStream(resource.inputStream)
        } catch (e: IOException) {
            throw IllegalStateException("Failed to load Firebase credentials", e)
        }
    }

}
