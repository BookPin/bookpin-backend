package com.bookpin.infrastructure.book.search

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "book.search")
data class BookSearchProperties(
    val kakao: KakaoProperties,
    val naver: NaverProperties,
    val aladin: AladinProperties,
    val google: GoogleProperties,
) {
    data class KakaoProperties(val apiKey: String)
    data class NaverProperties(val clientId: String, val clientSecret: String)
    data class AladinProperties(val ttbKey: String)
    data class GoogleProperties(val apiKey: String)
}
