package com.bookpin.infrastructure.book

import com.bookpin.domain.book.Bookmark
import com.bookpin.domain.book.repository.BookmarkRepository
import com.bookpin.infrastructure.book.jpa.BookmarkJpaRepository
import com.bookpin.infrastructure.book.mapper.BookmarkMapper
import org.springframework.stereotype.Repository

@Repository
class BookmarkRepositoryAdapter(
    private val bookmarkJpaRepository: BookmarkJpaRepository
) : BookmarkRepository {

    override fun save(bookmark: Bookmark): Bookmark {
        val entity = BookmarkMapper.toEntity(bookmark)
        val savedEntity = bookmarkJpaRepository.save(entity)
        return BookmarkMapper.toDomain(savedEntity)
    }

    override fun findById(id: Long): Bookmark? {
        return bookmarkJpaRepository.findById(id)
            .map { BookmarkMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByBookId(bookId: Long): List<Bookmark> {
        return bookmarkJpaRepository.findByBookId(bookId)
            .map { BookmarkMapper.toDomain(it) }
    }

    override fun findTextBookmarksByBookId(bookId: Long): List<Bookmark> {
        return bookmarkJpaRepository.findByBookIdAndImageUrlIsNull(bookId)
            .map { BookmarkMapper.toDomain(it) }
    }

    override fun findPhotoBookmarksByBookId(bookId: Long): List<Bookmark> {
        return bookmarkJpaRepository.findByBookIdAndImageUrlIsNotNull(bookId)
            .map { BookmarkMapper.toDomain(it) }
    }

    override fun findLatestByUserId(userId: Long): Bookmark? {
        return bookmarkJpaRepository.findLatestByUserId(userId)
            ?.let { BookmarkMapper.toDomain(it) }
    }

    override fun countByBookId(bookId: Long): Int {
        return bookmarkJpaRepository.countByBookId(bookId)
    }

    override fun deleteById(id: Long) {
        bookmarkJpaRepository.deleteById(id)
    }
}
