package com.bookpin.domain.fixture

import com.bookpin.domain.book.Bookmark
import com.bookpin.domain.common.RandomGenerator
import java.time.LocalDateTime

object BookmarkFixture {

    fun generate(
        id: Long = RandomGenerator.generateNonNullNumeric(2).toLong(),
        bookId: Long = RandomGenerator.generateNonNullNumeric(2).toLong(),
        pageNumber: Int = RandomGenerator.generateNonNullNumeric(2).coerceIn(1, 99),
        extractedText: String = RandomGenerator.generateNonNullString(50),
        note: String? = RandomGenerator.generateNonNullString(20),
        imageUrl: String? = null,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = createdAt
    ): Bookmark {
        return Bookmark(
            id = id,
            bookId = bookId,
            pageNumber = pageNumber,
            extractedText = extractedText,
            note = note,
            imageUrl = imageUrl,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun generatePhotoBookmark(
        id: Long = RandomGenerator.generateNonNullNumeric(2).toLong(),
        bookId: Long = RandomGenerator.generateNonNullNumeric(2).toLong(),
        pageNumber: Int = RandomGenerator.generateNonNullNumeric(2).coerceIn(1, 99),
        extractedText: String = RandomGenerator.generateNonNullString(50),
        note: String? = RandomGenerator.generateNonNullString(20),
        imageUrl: String = "https://example.com/${RandomGenerator.generateNonNullString(5)}.jpg",
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = createdAt
    ): Bookmark {
        return Bookmark(
            id = id,
            bookId = bookId,
            pageNumber = pageNumber,
            extractedText = extractedText,
            note = note,
            imageUrl = imageUrl,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
