package com.bookpin.app.book.request

data class AddBookRequest(
    val title: String,
    val author: String,
    val imageUrl: String?,
    val totalPage: Int
)
