package com.bookpin.infrastructure.auth.feign.kakao

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "kakao-auth-client",
    url = "https://kapi.kakao.com"
)
interface KakaoAuthFeignClient {

    @GetMapping("/v2/user/me")
    fun getUserInfo(
        @RequestHeader("Authorization") authorization: String
    ): KakaoUserResponse
}
