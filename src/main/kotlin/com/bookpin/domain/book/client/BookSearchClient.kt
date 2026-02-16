package com.bookpin.domain.book.client

import com.bookpin.domain.book.client.BookSearchResult

interface BookSearchClient {

    suspend fun search(query: String): List<BookSearchResult>
}