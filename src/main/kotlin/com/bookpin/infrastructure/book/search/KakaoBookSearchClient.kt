package com.bookpin.infrastructure.book.search

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakaoBookSearch", url = "https://dapi.kakao.com")
interface KakaoBookSearchClient {

    @GetMapping("/v3/search/book")
    fun search(
        @RequestParam("query") query: String,
        @RequestParam("size", defaultValue = "50") size: Int = 50,
        @RequestHeader("Authorization") authorization: String = "KakaoAK \${book.search.kakao.api-key}"
    ): KakaoBookSearchResponse
}

data class KakaoBookSearchResponse(
    val documents: List<KakaoBookDocument>
)

data class KakaoBookDocument(
    val title: String,
    val authors: List<String>,
    val thumbnail: String?,
    val isbn: String?,
    val publisher: String?
)
