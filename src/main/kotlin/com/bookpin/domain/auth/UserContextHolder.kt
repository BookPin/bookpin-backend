package com.bookpin.domain.auth

interface UserContextHolder {
    fun getUserId(): Long
    fun getToken(): String
    fun isAuthenticated(): Boolean
}
