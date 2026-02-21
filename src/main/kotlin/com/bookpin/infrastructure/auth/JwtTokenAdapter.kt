package com.bookpin.infrastructure.auth

import com.bookpin.domain.auth.TokenProvider
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenAdapter(
    @Value("\${jwt.secret}")
    private val secret: String,
    @Value("\${jwt.access-token-validity}")
    private val accessTokenValidity: Long,
    @Value("\${jwt.refresh-token-validity}")
    private val refreshTokenValidity: Long
) : TokenProvider {

    private val log = LoggerFactory.getLogger(javaClass)

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    override fun createAccessToken(userId: Long): String {
        return createToken(userId, accessTokenValidity)
    }

    override fun createRefreshToken(userId: Long): String {
        return createToken(userId, refreshTokenValidity)
    }

    override fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
            !claims.payload.expiration.before(Date())
        } catch (exception: Exception) {
            log.warn("JWT validation failed: ${exception.message}")
            false
        }
    }

    override fun getUserIdFromToken(token: String): Long {
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims.subject.toLong()
    }

    private fun createToken(userId: Long, validity: Long): String {
        val now = Date()
        val expiration = Date(now.time + validity)

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(expiration)
            .signWith(secretKey)
            .compact()
    }
}
