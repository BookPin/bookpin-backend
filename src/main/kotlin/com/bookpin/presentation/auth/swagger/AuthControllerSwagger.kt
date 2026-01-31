package com.bookpin.presentation.auth.swagger

import com.bookpin.app.auth.request.RefreshTokenRequest
import com.bookpin.app.auth.request.SocialLoginRequest
import com.bookpin.app.auth.response.SocialLoginResponse
import com.bookpin.app.auth.response.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Auth", description = "인증 API")
interface AuthControllerSwagger {

    @Operation(summary = "소셜 로그인", description = "카카오 또는 애플 소셜 로그인을 수행합니다.")
    fun socialLogin(@RequestBody request: SocialLoginRequest): ResponseEntity<SocialLoginResponse>

    @Operation(summary = "토큰 갱신", description = "리프레시 토큰으로 새로운 토큰을 발급받습니다.")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<TokenResponse>

}