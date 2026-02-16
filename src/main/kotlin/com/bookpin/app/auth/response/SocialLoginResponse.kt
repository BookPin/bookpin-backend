package com.bookpin.app.auth.response

data class SocialLoginResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
)
