package com.bookpin.domain.book.client

import com.bookpin.domain.book.repository.BookSearchSource

data class BookSearchResult(
    val title: String,
    val author: String,
    val imageUrl: String?,
    val totalPage: Int,
    val isbn: String?,
    val publisher: String?,
    val source: BookSearchSource
)