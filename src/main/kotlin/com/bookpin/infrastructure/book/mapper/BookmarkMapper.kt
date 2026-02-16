package com.bookpin.infrastructure.book.mapper

import com.bookpin.domain.book.Bookmark
import com.bookpin.infrastructure.book.jpa.BookmarkEntity

object BookmarkMapper {

    fun toEntity(bookmark: Bookmark): BookmarkEntity {
        return BookmarkEntity(
            id = bookmark.id,
            bookId = bookmark.bookId,
            pageNumber = bookmark.pageNumber,
            extractedText = bookmark.extractedText,
            note = bookmark.note,
            imageUrl = bookmark.imageUrl
        )
    }

    fun toDomain(entity: BookmarkEntity): Bookmark {
        return Bookmark(
            id = entity.id,
            bookId = entity.bookId,
            pageNumber = entity.pageNumber,
            extractedText = entity.extractedText,
            note = entity.note,
            imageUrl = entity.imageUrl,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}
