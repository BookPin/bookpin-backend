package com.book.Archiving.infrastructure.auth.feign.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoAccount(

    val email: String?,

    @JsonProperty("email_needs_agreement")
    val emailNeedsAgreement: Boolean?,

    @JsonProperty("is_email_valid")
    val isEmailValid: Boolean?,

    @JsonProperty("is_email_verified")
    val isEmailVerified: Boolean?,

    val profile: KakaoProfile?,

    @JsonProperty("profile_needs_agreement")
    val profileNeedsAgreement: Boolean?

)
