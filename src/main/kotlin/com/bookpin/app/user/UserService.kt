package com.bookpin.app.user

import com.bookpin.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {

    companion object {
        const val DEFAULT_NICKNAME = "독서하는 사람"
        const val DEFAULT_PROFILE_IMAGE = "https://bookpin.app/default-profile.png"
    }

    fun getUserInfo(userId: Long): UserInfo {
        val user = userRepository.findById(userId)
        return UserInfo(
            id = userId,
            nickname = DEFAULT_NICKNAME,
            profileImageUrl = DEFAULT_PROFILE_IMAGE
        )
    }
}
