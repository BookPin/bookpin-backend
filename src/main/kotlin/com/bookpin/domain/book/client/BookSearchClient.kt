package com.bookpin.domain.book.client

interface BookSearchClient {

    suspend fun search(query: String): List<BookSearchResult>
}