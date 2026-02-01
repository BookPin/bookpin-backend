package com.bookpin.infrastructure.auth.feign.apple

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "apple-auth-client",
    url = "https://appleid.apple.com"
)
interface AppleAuthFeignClient {

    @GetMapping("/auth/keys")
    fun getPublicKeys(): ApplePublicKeyResponse

    @PostMapping("/auth/token", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun getToken(
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("code") code: String,
        @RequestParam("grant_type") grantType: String = "authorization_code",
        @RequestParam("redirect_uri") redirectUri: String? = null
    ): AppleTokenResponse

    @PostMapping("/auth/token", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun refreshToken(
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("refresh_token") refreshToken: String,
        @RequestParam("grant_type") grantType: String = "refresh_token"
    ): AppleTokenResponse
}
