package com.bookpin.infrastructure.book

import com.bookpin.domain.book.Book
import com.bookpin.domain.book.repository.BookRepository
import com.bookpin.infrastructure.book.jpa.BookJpaRepository
import com.bookpin.infrastructure.book.mapper.BookMapper
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryAdapter(
    private val bookJpaRepository: BookJpaRepository
) : BookRepository {

    override fun save(book: Book): Book {
        val entity = BookMapper.toEntity(book)
        val savedEntity = bookJpaRepository.save(entity)
        return BookMapper.toDomain(savedEntity)
    }

    override fun findById(id: Long): Book? {
        return bookJpaRepository.findById(id)
            .map { BookMapper.toDomain(it) }
            .orElse(null)
    }

    override fun existsById(id: Long): Boolean {
        return bookJpaRepository.existsById(id)
    }

    override fun findByUserId(userId: Long): List<Book> {
        return bookJpaRepository.findByUserId(userId)
            .map { BookMapper.toDomain(it) }
    }

    override fun findByUserIdOrderByLatestBookmark(userId: Long): List<Book> {
        return bookJpaRepository.findByUserIdOrderByLatestBookmark(userId)
            .map { BookMapper.toDomain(it) }
    }

    override fun deleteById(id: Long) {
        bookJpaRepository.deleteById(id)
    }

    override fun incrementBookmarkCount(bookId: Long) {
        bookJpaRepository.incrementBookmarkCount(bookId)
    }

    override fun decrementBookmarkCount(bookId: Long) {
        bookJpaRepository.decrementBookmarkCount(bookId)
    }

    override fun incrementBookmarkCountAndUpdateCurrentPage(bookId: Long, pageNumber: Int) {
        bookJpaRepository.incrementBookmarkCountAndUpdateCurrentPage(bookId, pageNumber)
    }
}
