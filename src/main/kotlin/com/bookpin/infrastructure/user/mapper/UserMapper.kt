package com.bookpin.infrastructure.user.mapper

import com.bookpin.domain.user.LoginUser
import com.bookpin.infrastructure.user.jpa.UserEntity

object UserMapper {

    fun toEntity(loginUser: LoginUser): UserEntity {
        return UserEntity(
            id = loginUser.id,
            socialId = loginUser.socialId,
            socialProvider = loginUser.socialProvider,
            email = loginUser.email,
            nickname = loginUser.nickname,
            profileImageUrl = loginUser.profileImageUrl
        )
    }

    fun toDomain(entity: UserEntity): LoginUser {
        return LoginUser(
            id = entity.id,
            socialId = entity.socialId,
            socialProvider = entity.socialProvider,
            email = entity.email,
            nickname = entity.nickname,
            profileImageUrl = entity.profileImageUrl,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}
