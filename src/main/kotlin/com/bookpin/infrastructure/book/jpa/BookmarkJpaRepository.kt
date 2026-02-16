package com.bookpin.infrastructure.book.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookmarkJpaRepository : JpaRepository<BookmarkEntity, Long> {

    fun findByBookId(bookId: Long): List<BookmarkEntity>

    fun findByBookIdAndImageUrlIsNull(bookId: Long): List<BookmarkEntity>

    fun findByBookIdAndImageUrlIsNotNull(bookId: Long): List<BookmarkEntity>

    fun countByBookId(bookId: Long): Int

    @Query("""
        SELECT bm FROM BookmarkEntity bm
        JOIN BookEntity b ON bm.bookId = b.id
        WHERE b.userId = :userId
        ORDER BY bm.createdAt DESC
        LIMIT 1
    """)
    fun findLatestByUserId(userId: Long): BookmarkEntity?
}
