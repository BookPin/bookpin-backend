package com.bookpin.infrastructure.auth

import com.bookpin.domain.auth.SocialLoginClient
import com.bookpin.domain.auth.SocialUserInfo
import com.bookpin.domain.user.SocialType
import com.bookpin.infrastructure.auth.feign.apple.AppleAuthFeignClient
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.*

@Component
class AppleSocialLoginAdapter(
    private val appleAuthFeignClient: AppleAuthFeignClient,
    private val objectMapper: ObjectMapper
) : SocialLoginClient {

    override fun getProviderType(): SocialType = SocialType.APPLE

    override fun getUserInfo(accessToken: String): SocialUserInfo {
        val payload = verifyAndDecodeIdToken(accessToken)
        return SocialUserInfo(
            socialId = payload.subject,
            email = payload.email,
            nickname = null,
            profileImageUrl = null
        )
    }

    private fun verifyAndDecodeIdToken(idToken: String): AppleIdTokenPayload {
        val keyId = extractKeyIdFromToken(idToken)
        val publicKey = getMatchingPublicKey(keyId)
        return parseIdToken(idToken, publicKey)
    }

    private fun extractKeyIdFromToken(idToken: String): String {
        val headerJson = String(Base64.getUrlDecoder().decode(idToken.split(".")[0]))
        val header = objectMapper.readValue(headerJson, Map::class.java)
        return header["kid"] as String
    }

    private fun getMatchingPublicKey(keyId: String): PublicKey {
        val publicKeys = appleAuthFeignClient.getPublicKeys()
        val matchingKey = publicKeys.keys.find { it.keyId == keyId }
            ?: throw IllegalArgumentException("Matching public key not found for keyId: $keyId")

        return generatePublicKey(matchingKey.modulus, matchingKey.exponent)
    }

    private fun parseIdToken(idToken: String, publicKey: PublicKey): AppleIdTokenPayload {
        val claims = Jwts.parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(idToken)
            .payload

        return AppleIdTokenPayload(
            subject = claims.subject,
            email = claims["email"] as? String
        )
    }

    private fun generatePublicKey(modulus: String, exponent: String): PublicKey {
        val modulusBytes = BigInteger(1, Base64.getUrlDecoder().decode(modulus))
        val exponentBytes = BigInteger(1, Base64.getUrlDecoder().decode(exponent))
        val spec = RSAPublicKeySpec(modulusBytes, exponentBytes)
        return KeyFactory.getInstance("RSA").generatePublic(spec)
    }
}
