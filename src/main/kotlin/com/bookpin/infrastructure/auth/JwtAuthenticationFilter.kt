package com.bookpin.infrastructure.auth

import com.bookpin.domain.auth.TokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime

@Component
class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(javaClass)

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
        log.info("[JWT Filter] Request: ${request.method} ${request.requestURI}")
        log.info("[JWT Filter] Authorization Header: ${request.getHeader(AUTHORIZATION_HEADER)}")

        val token = extractToken(request)

        if (token == null) {
            log.warn("[JWT Filter] Token is null, continuing without auth")
            filterChain.doFilter(request, response)
            return
        }

        log.info("[JWT Filter] Token extracted: ${token.take(20)}...")

        val isValid = tokenProvider.validateToken(token)
        if (!isValid) {
            log.warn("[JWT Filter] Token validation failed")
            sendUnauthorizedResponse(response, "Invalid or expired token")
            return
        }

        log.info("[JWT Filter] Token is valid, setting authentication")
        setAuthentication(token)
        filterChain.doFilter(request, response)
    }

    private fun sendUnauthorizedResponse(response: HttpServletResponse, message: String) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        val errorResponse = mapOf(
            "code" to "UNAUTHORIZED",
            "message" to message,
            "timestamp" to LocalDateTime.now().toString()
        )

        objectMapper.writeValue(response.writer, errorResponse)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
            ?: request.getHeader(AUTHORIZATION_HEADER.lowercase())

        if (bearerToken == null) {
            log.debug("Authorization header not found")
            return null
        }

        return if (bearerToken.startsWith(BEARER_PREFIX, ignoreCase = true)) {
            bearerToken.substring(BEARER_PREFIX.length).trim()
        } else {
            log.debug("Authorization header does not start with Bearer: $bearerToken")
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
