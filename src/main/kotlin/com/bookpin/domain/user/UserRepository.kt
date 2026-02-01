package com.bookpin.domain.user

interface UserRepository {

    fun save(user: User): User

    fun findById(id: Long): User?

    fun findBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): User?

    fun existsBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): Boolean

}
