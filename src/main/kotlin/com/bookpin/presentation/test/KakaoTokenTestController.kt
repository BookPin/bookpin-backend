package com.bookpin.presentation.test

import com.bookpin.infrastructure.auth.feign.kakao.KakaoTokenFeignClient
import com.bookpin.infrastructure.auth.feign.kakao.KakaoTokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test/api")
@Profile("local", "dev")
class KakaoTokenTestController(
    private val kakaoTokenFeignClient: KakaoTokenFeignClient,
    @Value("\${oauth.kakao.client-id}") private val kakaoClientId: String,
    @Value("\${oauth.kakao.redirect-uri}") private val kakaoRedirectUri: String,
    @Value("\${oauth.kakao.client-secret:}") private val kakaoClientSecret: String
) {

    @GetMapping("/kakao/token")
    fun getKakaoToken(
        @RequestParam("code") code: String
    ): ResponseEntity<KakaoTokenResponse> {
        val tokenResponse = kakaoTokenFeignClient.getToken(
            grantType = "authorization_code",
            clientId = kakaoClientId,
            redirectUri = kakaoRedirectUri,
            code = code,
            clientSecret = kakaoClientSecret.ifBlank { null }
        )
        return ResponseEntity.ok(tokenResponse)
    }
}
