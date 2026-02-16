package com.bookpin.app.user.response

import com.bookpin.app.user.UserInfo

data class UserInfoResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String
) {

    companion object {

        fun from(userInfo: UserInfo): UserInfoResponse {
            return UserInfoResponse(
                id = userInfo.id,
                nickname = userInfo.nickname,
                profileImageUrl = userInfo.profileImageUrl
            )
        }
    }
}
