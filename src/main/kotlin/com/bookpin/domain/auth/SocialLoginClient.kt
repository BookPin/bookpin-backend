package com.bookpin.domain.auth

import com.bookpin.domain.user.SocialType

interface SocialLoginClient {
    fun getProviderType(): SocialType
    fun getUserInfo(accessToken: String): SocialUserInfo
}
