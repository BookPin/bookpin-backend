package com.bookpin.infrastructure.book.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "aladinBookSearch",
    url = "https://www.aladin.co.kr",
    configuration = [AladinFeignConfig::class]
)
interface AladinBookSearchClient {

    @GetMapping("/ttb/api/ItemSearch.aspx")
    fun search(
        @RequestParam("Query") query: String,
        @RequestParam("QueryType") queryType: String = "Title",
        @RequestParam("MaxResults") maxResults: Int = 100,
        @RequestParam("Output") output: String = "JS",
        @RequestParam("Version") version: String = "20131101",
        @RequestParam("Cover") cover: String = "Big",
        @RequestParam("OptResult") optResult: String = "packing"
    ): AladinBookSearchResponse
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class AladinBookSearchResponse(
    val item: List<AladinBookItem> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AladinBookItem(
    val title: String,
    val author: String,
    val cover: String?,
    val isbn: String?,
    val isbn13: String?,
    val publisher: String?,
    val subInfo: AladinSubInfo?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AladinSubInfo(
    val itemPage: Int?,
    @JsonProperty("ppiNum") // 페이지 수가 ppiNum으로 올 수도 있음
    val ppiNum: Int?
) {
    fun getPageCount(): Int = itemPage ?: ppiNum ?: 0
}
