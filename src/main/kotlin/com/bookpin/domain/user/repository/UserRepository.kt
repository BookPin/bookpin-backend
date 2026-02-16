package com.bookpin.domain.user.repository

import com.bookpin.domain.user.LoginUser
import com.bookpin.domain.user.SocialType

interface UserRepository {

    fun save(loginUser: LoginUser): LoginUser

    fun findById(id: Long): LoginUser?

    fun findBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): LoginUser?

    fun existsBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): Boolean

}