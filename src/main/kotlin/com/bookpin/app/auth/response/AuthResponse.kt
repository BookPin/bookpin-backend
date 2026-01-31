package com.bookpin.app.auth.response

data class AuthResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean
)
