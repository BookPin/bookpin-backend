package com.bookpin.infrastructure.auth.feign.apple

import com.fasterxml.jackson.annotation.JsonProperty

data class ApplePublicKey(
    @JsonProperty("kty")
    val keyType: String,
    @JsonProperty("kid")
    val keyId: String,
    @JsonProperty("use")
    val use: String,
    @JsonProperty("alg")
    val algorithm: String,
    @JsonProperty("n")
    val modulus: String,
    @JsonProperty("e")
    val exponent: String
)
