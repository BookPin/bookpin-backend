package com.bookpin.app.book.response

import com.bookpin.domain.book.client.BookSearchResult

data class BookSearchResponse(
    val title: String,
    val author: String,
    val imageUrl: String?,
    val totalPage: Int,
    val isbn: String?,
    val publisher: String?,
    val source: String
) {

    companion object {

        fun from(result: BookSearchResult): BookSearchResponse {
            return BookSearchResponse(
                title = result.title,
                author = result.author,
                imageUrl = result.imageUrl,
                totalPage = result.totalPage,
                isbn = result.isbn,
                publisher = result.publisher,
                source = result.source.name
            )
        }
    }
}
