package com.bookpin.presentation.user.swagger

import com.bookpin.app.user.response.UserInfoResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "User", description = "유저 관련 API")
interface UserControllerSwagger {

    @Operation(summary = "유저 정보 조회", description = "현재 로그인한 유저의 정보를 조회합니다.")
    fun getUserInfo(): ResponseEntity<UserInfoResponse>
}
