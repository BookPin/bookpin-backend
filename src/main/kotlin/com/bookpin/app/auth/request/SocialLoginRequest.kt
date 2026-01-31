package com.bookpin.app.auth.request

import com.bookpin.domain.user.SocialType

data class SocialLoginRequest(
    val socialType: SocialType,
    val accessToken: String
)
