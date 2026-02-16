package com.bookpin.app.book.response

import com.bookpin.app.book.response.BookshelfItem

data class BookshelfResponse(
    val id: Long,
    val title: String,
    val author: String,
    val imageUrl: String?,
    val totalPage: Int,
    val currentPage: Int,
    val progress: Double,
    val bookmarkCount: Int
) {

    companion object {

        fun from(item: BookshelfItem): BookshelfResponse {
            return BookshelfResponse(
                id = item.book.id,
                title = item.book.title,
                author = item.book.author,
                imageUrl = item.book.imageUrl,
                totalPage = item.book.totalPage,
                currentPage = item.book.currentPage,
                progress = item.progress,
                bookmarkCount = item.bookmarkCount
            )
        }
    }
}
