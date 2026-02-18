package com.bookpin.infrastructure.auth

import com.bookpin.domain.auth.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
        private val PERMIT_ALL_PATHS = listOf(
            "/api/v1/auth/",
            "/swagger-ui",
            "/v3/api-docs",
            "/health",
            "/webjars/",
        )
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return PERMIT_ALL_PATHS.any { path.startsWith(it) } || path == "/"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)

        if (token != null && tokenProvider.validateToken(token)) {
            setAuthentication(token)
        }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)

        return if (bearerToken?.startsWith(BEARER_PREFIX) == true) {
            bearerToken.substring(BEARER_PREFIX.length)
        } else {
            null
        }
    }

    private fun setAuthentication(token: String) {
        val userId = tokenProvider.getUserIdFromToken(token)
        val principal = UserPrincipal(userId = userId, token = token)
        val authentication = UsernamePasswordAuthenticationToken(principal, token, emptyList())
        SecurityContextHolder.getContext().authentication = authentication
    }
}
