package com.bookpin.infrastructure.auth.feign.apple

data class ApplePublicKeyResponse(
    val keys: List<ApplePublicKey>
)
