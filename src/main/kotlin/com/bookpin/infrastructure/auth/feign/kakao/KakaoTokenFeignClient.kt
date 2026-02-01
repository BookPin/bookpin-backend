package com.bookpin.infrastructure.auth.feign.kakao

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "kakao-token-client",
    url = "https://kauth.kakao.com"
)
interface KakaoTokenFeignClient {

    @PostMapping(value = ["/oauth/token"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun getToken(
        @RequestParam("grant_type") grantType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("code") code: String,
        @RequestParam("client_secret", required = false) clientSecret: String? = null
    ): KakaoTokenResponse

}
