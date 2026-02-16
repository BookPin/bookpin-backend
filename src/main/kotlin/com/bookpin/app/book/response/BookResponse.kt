package com.bookpin.app.book.response

import com.bookpin.domain.book.Book
import java.time.LocalDateTime

data class BookResponse(
    val id: Long,
    val title: String,
    val author: String,
    val imageUrl: String?,
    val totalPage: Int,
    val currentPage: Int,
    val progress: Double,
    val isCompleted: Boolean,
    val createdAt: LocalDateTime
) {

    companion object {

        fun from(book: Book): BookResponse {
            return BookResponse(
                id = book.id,
                title = book.title,
                author = book.author,
                imageUrl = book.imageUrl,
                totalPage = book.totalPage,
                currentPage = book.currentPage,
                progress = book.calculateProgress(),
                isCompleted = book.isCompleted,
                createdAt = book.createdAt
            )
        }
    }
}
