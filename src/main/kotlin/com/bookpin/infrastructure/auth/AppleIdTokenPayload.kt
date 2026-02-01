package com.bookpin.infrastructure.auth

data class AppleIdTokenPayload(
    val subject: String,
    val email: String?
)
