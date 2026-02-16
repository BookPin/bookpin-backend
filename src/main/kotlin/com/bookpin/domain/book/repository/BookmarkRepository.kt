package com.bookpin.domain.book.repository

import com.bookpin.domain.book.Bookmark

interface BookmarkRepository {

    fun save(bookmark: Bookmark): Bookmark

    fun findById(id: Long): Bookmark?

    fun findByBookId(bookId: Long): List<Bookmark>

    fun findTextBookmarksByBookId(bookId: Long): List<Bookmark>

    fun findPhotoBookmarksByBookId(bookId: Long): List<Bookmark>

    fun findLatestByUserId(userId: Long): Bookmark?

    fun countByBookId(bookId: Long): Int

    fun deleteById(id: Long)
}
