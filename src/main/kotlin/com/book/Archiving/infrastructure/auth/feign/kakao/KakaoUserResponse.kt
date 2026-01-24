package com.book.Archiving.infrastructure.auth.feign.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserResponse(

    val id: Long,

    @JsonProperty("connected_at")
    val connectedAt: String?,

    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount?

)
