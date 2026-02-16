package com.bookpin.domain.user

import java.time.LocalDateTime

data class LoginUser(
    val id: Long = 0,
    val socialId: String,
    val socialProvider: SocialType,
    val email: String? = null,
    val nickname: String? = DEFAULT_NICK_NAME,
    val profileImageUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : User {
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
    ): LoginUser {
        return copy(
            email = email,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            updatedAt = LocalDateTime.now()
        )
    }

    override fun identifier(): String {
        return id.toString()
    }

    override fun isLogin(): Boolean {
        return true
    }

    override fun username(): String {
        return nickname ?: DEFAULT_NICK_NAME
    }

    companion object {
        const val DEFAULT_NICK_NAME = "독서하는 사람"
    }
}
