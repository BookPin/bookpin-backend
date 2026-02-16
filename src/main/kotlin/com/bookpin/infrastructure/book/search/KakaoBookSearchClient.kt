package com.bookpin.infrastructure.book.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "kakaoBookSearch",
    url = "https://dapi.kakao.com",
    configuration = [KakaoFeignConfig::class]
)
interface KakaoBookSearchClient {

    @GetMapping("/v3/search/book")
    fun search(
        @RequestParam("query") query: String,
        @RequestParam("size") size: Int = 50
    ): KakaoBookSearchResponse
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoBookSearchResponse(
    val documents: List<KakaoBookDocument> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoBookDocument(
    val title: String,
    val authors: List<String> = emptyList(),
    val thumbnail: String?,
    val isbn: String?,
    val publisher: String?
)
