package com.bookpin.infrastructure.book.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "naverBookSearch",
    url = "https://openapi.naver.com",
    configuration = [NaverFeignConfig::class]
)
interface NaverBookSearchClient {

    @GetMapping("/v1/search/book.json")
    fun search(
        @RequestParam("query") query: String,
        @RequestParam("display") display: Int = 100
    ): NaverBookSearchResponse
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class NaverBookSearchResponse(
    val items: List<NaverBookItem> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class NaverBookItem(
    val title: String,
    val author: String,
    val image: String?,
    val isbn: String?,
    val publisher: String?
)
