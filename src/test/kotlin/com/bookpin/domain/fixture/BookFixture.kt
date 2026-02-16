package com.bookpin.domain.fixture

import com.bookpin.domain.book.Book
import com.bookpin.domain.common.RandomGenerator
import java.time.LocalDateTime

object BookFixture {

    fun generate(
        id: Long = RandomGenerator.generateNonNullNumeric(2).toLong(),
        userId: Long = RandomGenerator.generateNonNullNumeric(2).toLong(),
        title: String = RandomGenerator.generateNonNullString(10),
        author: String = RandomGenerator.generateNonNullString(5),
        imageUrl: String? = "https://example.com/${RandomGenerator.generateNonNullString(5)}.jpg",
        totalPage: Int = RandomGenerator.generateNonNullNumeric(3).coerceIn(1, 999),
        currentPage: Int = 0,
        bookmarkCount: Int = 0,
        isCompleted: Boolean = false,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = createdAt
    ): Book {
        return Book(
            id = id,
            userId = userId,
            title = title,
            author = author,
            imageUrl = imageUrl,
            totalPage = totalPage,
            currentPage = currentPage,
            bookmarkCount = bookmarkCount,
            isCompleted = isCompleted,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
