package com.bookpin.infrastructure.book.search

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "naverBookSearch", url = "https://openapi.naver.com")
interface NaverBookSearchClient {

    @GetMapping("/v1/search/book.json")
    fun search(
        @RequestParam("query") query: String,
        @RequestParam("display", defaultValue = "100") display: Int = 100,
        @RequestHeader("X-Naver-Client-Id") clientId: String = "\${book.search.naver.client-id}",
        @RequestHeader("X-Naver-Client-Secret") clientSecret: String = "\${book.search.naver.client-secret}"
    ): NaverBookSearchResponse
}

data class NaverBookSearchResponse(
    val items: List<NaverBookItem>
)

data class NaverBookItem(
    val title: String,
    val author: String,
    val image: String?,
    val isbn: String?,
    val publisher: String?
)
