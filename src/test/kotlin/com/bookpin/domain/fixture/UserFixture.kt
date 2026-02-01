package com.bookpin.domain.fixture

import com.bookpin.domain.common.RandomGenerator
import com.bookpin.domain.user.SocialType
import com.bookpin.domain.user.User
import java.time.LocalDateTime

object UserFixture {

    fun generate(
        id: Long = RandomGenerator.generateNonNullNumeric(2).toLong(),
        socialId: String = RandomGenerator.generateNonNullString(5),
        socialProvider: SocialType = RandomGenerator.generateNonNullEnum(SocialType::class),
        email: String? = "${RandomGenerator.generateNonNullString(5)}@test.com",
        nickname: String? = RandomGenerator.generateNonNullString(5),
        profileImageUrl: String? = RandomGenerator.generateNonNullString(5),
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now()
    ): User {
        return User(
            id = id,
            socialId = socialId,
            socialProvider = socialProvider,
            email = email,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

}