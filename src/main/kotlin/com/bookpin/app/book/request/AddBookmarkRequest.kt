package com.bookpin.app.book.request

data class AddBookmarkRequest(
    val pageNumber: Int,
    val extractedText: String,
    val note: String?,
    val imageUrl: String?
)
