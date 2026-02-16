package com.bookpin.infrastructure.book.search

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "aladinBookSearch", url = "https://www.aladin.co.kr")
interface AladinBookSearchClient {

    @GetMapping("/ttb/api/ItemSearch.aspx")
    fun search(
        @RequestParam("Query") query: String,
        @RequestParam("ttbkey") ttbKey: String = "\${book.search.aladin.ttb-key}",
        @RequestParam("QueryType", defaultValue = "Title") queryType: String = "Title",
        @RequestParam("MaxResults", defaultValue = "100") maxResults: Int = 100,
        @RequestParam("Output", defaultValue = "JS") output: String = "JS",
        @RequestParam("Version", defaultValue = "20131101") version: String = "20131101"
    ): AladinBookSearchResponse
}

data class AladinBookSearchResponse(
    val item: List<AladinBookItem>
)

data class AladinBookItem(
    val title: String,
    val author: String,
    val cover: String?,
    val isbn: String?,
    val isbn13: String?,
    val publisher: String?,
    val subInfo: AladinSubInfo?
)

data class AladinSubInfo(
    val itemPage: Int?
)
