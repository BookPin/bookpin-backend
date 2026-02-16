package com.bookpin.domain.book.repository

import com.bookpin.domain.book.Book

interface BookRepository {

    fun save(book: Book): Book

    fun findById(id: Long): Book?

    fun existsById(id: Long): Boolean

    fun findByUserId(userId: Long): List<Book>

    fun findByUserIdOrderByLatestBookmark(userId: Long): List<Book>

    fun deleteById(id: Long)

    fun incrementBookmarkCount(bookId: Long)

    fun decrementBookmarkCount(bookId: Long)

    fun incrementBookmarkCountAndUpdateCurrentPage(bookId: Long, pageNumber: Int)
}
