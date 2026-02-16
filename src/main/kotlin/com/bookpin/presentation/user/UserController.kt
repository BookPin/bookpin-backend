package com.bookpin.presentation.user

import com.bookpin.app.user.UserService
import com.bookpin.app.user.response.UserInfoResponse
import com.bookpin.domain.auth.UserContextHolder
import com.bookpin.presentation.user.swagger.UserControllerSwagger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val userContextHolder: UserContextHolder
) : UserControllerSwagger {

    @GetMapping("/me")
    override fun getUserInfo(): ResponseEntity<UserInfoResponse> {
        val userId = userContextHolder.getUserId()
        val userInfo = userService.getUserInfo(userId)
        return ResponseEntity.ok(UserInfoResponse.from(userInfo))
    }
}
