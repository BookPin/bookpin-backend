package com.bookpin.infrastructure.user.jpa

import com.bookpin.domain.user.SocialType
import com.bookpin.infrastructure.user.jpa.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun findBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): UserEntity?

    fun existsBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): Boolean

}
