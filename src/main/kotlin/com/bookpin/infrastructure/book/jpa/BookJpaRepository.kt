package com.bookpin.infrastructure.book.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface BookJpaRepository : JpaRepository<BookEntity, Long> {

    fun findByUserId(userId: Long): List<BookEntity>

    @Query("""
        SELECT b FROM BookEntity b
        WHERE b.userId = :userId
        ORDER BY (
            SELECT MAX(bm.createdAt) FROM BookmarkEntity bm WHERE bm.bookId = b.id
        ) DESC NULLS LAST
    """)
    fun findByUserIdOrderByLatestBookmark(userId: Long): List<BookEntity>

    @Modifying
    @Query("UPDATE BookEntity b SET b.bookmarkCount = b.bookmarkCount + 1 WHERE b.id = :bookId")
    fun incrementBookmarkCount(bookId: Long): Int

    @Modifying
    @Query("UPDATE BookEntity b SET b.bookmarkCount = b.bookmarkCount - 1 WHERE b.id = :bookId AND b.bookmarkCount > 0")
    fun decrementBookmarkCount(bookId: Long): Int

    @Modifying
    @Query("""
        UPDATE BookEntity b
        SET b.bookmarkCount = b.bookmarkCount + 1,
            b.currentPage = CASE WHEN :pageNumber > b.currentPage THEN :pageNumber ELSE b.currentPage END
        WHERE b.id = :bookId
    """)
    fun incrementBookmarkCountAndUpdateCurrentPage(bookId: Long, pageNumber: Int): Int
}
