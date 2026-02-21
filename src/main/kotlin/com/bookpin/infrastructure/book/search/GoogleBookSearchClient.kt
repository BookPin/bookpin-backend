package com.bookpin.infrastructure.book.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "googleBookSearch",
    url = "https://www.googleapis.com",
    configuration = [GoogleFeignConfig::class]
)
interface GoogleBookSearchClient {

    @GetMapping("/books/v1/volumes")
    fun search(
        @RequestParam("q") query: String,
    ): GoogleBookSearchResponse

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleBookSearchResponse(
    val items: List<GoogleBookItem>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleBookItem(
    val id: String?,
    val volumeInfo: GoogleVolumeInfo?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleVolumeInfo(
    val title: String?,
    val authors: List<String>? = null,
    val publishedDate: String?,
    val publisher: String?,
    val pageCount: Int?,
    val industryIdentifiers: List<IndustryIdentifier>? = null,
    val imageLinks: ImageLinks?,
    val language: String?
) {

    fun getThumbnail(): String? =
        imageLinks?.thumbnail?.replace("http://", "https://")

    fun getIsbn(): String? {
        val identifiers = industryIdentifiers ?: return null
        return identifiers.firstOrNull { it.type == "ISBN_13" }?.identifier
            ?: identifiers.firstOrNull { it.type == "ISBN_10" }?.identifier
            ?: identifiers.firstOrNull()?.identifier
    }

    fun getPageCount(): Int = pageCount ?: 0
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class IndustryIdentifier(
    val type: String?,
    val identifier: String?
)

