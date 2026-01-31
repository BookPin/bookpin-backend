package com.bookpin.domain.auth

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)
