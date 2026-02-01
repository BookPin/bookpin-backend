package com.bookpin.infrastructure.user.mapper

import com.bookpin.domain.user.User
import com.bookpin.infrastructure.user.jpa.UserEntity

object UserMapper {

    fun toEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            socialId = user.socialId,
            socialProvider = user.socialProvider,
            email = user.email,
            nickname = user.nickname,
            profileImageUrl = user.profileImageUrl,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }

    fun toDomain(entity: UserEntity): User {
        return User(
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