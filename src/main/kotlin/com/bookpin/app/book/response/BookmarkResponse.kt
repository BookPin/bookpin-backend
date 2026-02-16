package com.bookpin.app.book.response

import com.bookpin.domain.book.Bookmark
import java.time.LocalDateTime

data class BookmarkResponse(
    val id: Long,
    val bookId: Long,
    val pageNumber: Int,
    val extractedText: String,
    val note: String?,
    val imageUrl: String?,
    val createdAt: LocalDateTime
) {

    companion object {

        fun from(bookmark: Bookmark): BookmarkResponse {
            return BookmarkResponse(
                id = bookmark.id,
                bookId = bookmark.bookId,
                pageNumber = bookmark.pageNumber,
                extractedText = bookmark.extractedText,
                note = bookmark.note,
                imageUrl = bookmark.imageUrl,
                createdAt = bookmark.createdAt
            )
        }
    }
}
