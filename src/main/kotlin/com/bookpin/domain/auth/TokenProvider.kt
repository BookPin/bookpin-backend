package com.bookpin.domain.auth

interface TokenProvider {
    fun createAccessToken(userId: Long): String
    fun createRefreshToken(userId: Long): String
    fun validateToken(token: String): Boolean
    fun getUserIdFromToken(token: String): Long
}
