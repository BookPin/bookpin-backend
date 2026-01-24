package com.book.Archiving.infrastructure.auth

import com.book.Archiving.domain.auth.UserContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityUserContextHolder : UserContextHolder {

    override fun getUserId(): Long {
        return getUserPrincipal().userId
    }

    override fun getToken(): String {
        return getUserPrincipal().token
    }

    override fun isAuthenticated(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication != null && authentication.principal is UserPrincipal
    }

    private fun getUserPrincipal(): UserPrincipal {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("No authentication found")
        return authentication.principal as? UserPrincipal
            ?: throw IllegalStateException("Invalid authentication principal")
    }

}
