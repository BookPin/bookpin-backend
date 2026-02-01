package com.bookpin.domain.user

import java.time.LocalDateTime

data class User(
    val id: Long = 0,
    val socialId: String,
    val socialProvider: SocialType,
    val email: String? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(socialId.isNotBlank()) { "socialId must not be blank" }
        email?.let {
            require(it.contains("@")) { "email must contain @" }
        }
        nickname?.let {
            require(it.length <= 20) { "nickname must be 20 characters or less" }
        }
    }

    fun updateProfile(
        email: String? = this.email,
        nickname: String? = this.nickname,
        profileImageUrl: String? = this.profileImageUrl
    ): User {
        return copy(
            email = email,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            updatedAt = LocalDateTime.now()
        )
    }
}
