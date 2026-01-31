package com.bookpin.app.auth.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
