package com.bookpin.infrastructure.book.mapper

import com.bookpin.domain.book.Book
import com.bookpin.infrastructure.book.jpa.BookEntity

object BookMapper {

    fun toEntity(book: Book): BookEntity {
        return BookEntity(
            id = book.id,
            userId = book.userId,
            title = book.title,
            author = book.author,
            imageUrl = book.imageUrl,
            totalPage = book.totalPage,
            currentPage = book.currentPage,
            bookmarkCount = book.bookmarkCount,
            isCompleted = book.isCompleted
        )
    }

    fun toDomain(entity: BookEntity): Book {
        return Book(
            id = entity.id,
            userId = entity.userId,
            title = entity.title,
            author = entity.author,
            imageUrl = entity.imageUrl,
            totalPage = entity.totalPage,
            currentPage = entity.currentPage,
            bookmarkCount = entity.bookmarkCount,
            isCompleted = entity.isCompleted,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}
