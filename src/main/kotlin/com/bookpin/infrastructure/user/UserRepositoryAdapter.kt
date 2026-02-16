package com.bookpin.infrastructure.user

import com.bookpin.domain.user.SocialType
import com.bookpin.domain.user.LoginUser
import com.bookpin.domain.user.repository.UserRepository
import com.bookpin.infrastructure.user.jpa.UserJpaRepository
import com.bookpin.infrastructure.user.mapper.UserMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun save(loginUser: LoginUser): LoginUser {
        val entity = UserMapper.toEntity(loginUser)
        val savedEntity = userJpaRepository.save(entity)
        return UserMapper.toDomain(savedEntity)
    }

    override fun findById(id: Long): LoginUser? {
        return userJpaRepository.findByIdOrNull(id)?.let { UserMapper.toDomain(it) }
    }

    override fun findBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): LoginUser? {
        return userJpaRepository.findBySocialIdAndSocialProvider(socialId, socialProvider)
            ?.let { UserMapper.toDomain(it) }
    }

    override fun existsBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): Boolean {
        return userJpaRepository.existsBySocialIdAndSocialProvider(socialId, socialProvider)
    }
}