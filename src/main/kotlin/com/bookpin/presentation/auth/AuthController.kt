package com.bookpin.presentation.auth

import com.bookpin.app.auth.AuthService
import com.bookpin.app.auth.request.RefreshTokenRequest
import com.bookpin.app.auth.request.SocialLoginRequest
import com.bookpin.app.auth.request.SocialLoginResponse
import com.bookpin.app.auth.request.TokenResponse
import com.bookpin.domain.user.SocialType
import com.bookpin.infrastructure.auth.feign.kakao.KakaoTokenFeignClient
import com.bookpin.presentation.auth.swagger.AuthControllerSwagger
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val kakaoTokenFeignClient: KakaoTokenFeignClient,
    @Value("\${oauth.kakao.client-id}") private val kakaoClientId: String
) : AuthControllerSwagger {

    @PostMapping("/login")
    override fun socialLogin(@RequestBody request: SocialLoginRequest): ResponseEntity<SocialLoginResponse> {
        val result = authService.socialLogin(request.socialType, request.accessToken)

        return ResponseEntity.ok(
            SocialLoginResponse(
                userId = result.userId,
                accessToken = result.accessToken,
                refreshToken = result.refreshToken,
                isNewUser = result.isNewUser
            )
        )
    }

    @PostMapping("/refresh")
    override fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<TokenResponse> {
        val result = authService.refreshToken(request.refreshToken)

        return ResponseEntity.ok(
            TokenResponse(
                accessToken = result.accessToken,
                refreshToken = result.refreshToken
            )
        )
    }

    @GetMapping("/kakao/token")
    fun kakaoTokenExchange(
        @RequestParam("code") code: String,
        request: HttpServletRequest
    ): ResponseEntity<SocialLoginResponse> {
        val redirectUri = "${request.scheme}://${request.serverName}:${request.serverPort}/test/kakao/callback"
        val tokenResponse = kakaoTokenFeignClient.getToken(
            grantType = "authorization_code",
            clientId = kakaoClientId,
            redirectUri = redirectUri,
            code = code
        )

        val result = authService.socialLogin(
            provider = SocialType.KAKAO,
            accessToken = tokenResponse.accessToken
        )

        return ResponseEntity.ok(
            SocialLoginResponse(
                userId = result.userId,
                accessToken = result.accessToken,
                refreshToken = result.refreshToken,
                isNewUser = result.isNewUser
            )
        )
    }
}
