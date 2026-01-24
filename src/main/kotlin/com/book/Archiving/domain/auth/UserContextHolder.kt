package com.book.Archiving.domain.auth

interface UserContextHolder {

    fun getUserId(): Long

    fun getToken(): String

    fun isAuthenticated(): Boolean

}
