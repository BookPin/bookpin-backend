package com.bookpin.app.book.response

import com.bookpin.domain.book.Book

data class BookDetail(
    val book: Book,
    val bookmarkCount: Int,
    val progress: Double
)