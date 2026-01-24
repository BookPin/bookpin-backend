package com.book.Archiving.infrastructure.auth.feign.apple

data class ApplePublicKeyResponse(
    val keys: List<ApplePublicKey>
)
