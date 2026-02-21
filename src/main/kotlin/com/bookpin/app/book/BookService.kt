package com.bookpin.app.book

import com.bookpin.app.book.response.BookDetail
import com.bookpin.app.book.response.BookshelfItem
import com.bookpin.domain.book.Book
import com.bookpin.domain.book.repository.BookRepository
import com.bookpin.domain.book.client.BookSearchClient
import com.bookpin.domain.book.client.BookSearchResult
import com.bookpin.domain.book.Bookmark
import com.bookpin.domain.book.repository.BookmarkRepository
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookService(
    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val bookSearchClient: BookSearchClient
) {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun searchBooks(query: String): List<BookSearchResult> {
        return runBlocking {
            bookSearchClient.search(query)
        }
    }

    @Transactional
    fun addBook(
        userId: Long,
        title: String,
        author: String,
        imageUrl: String?,
        totalPage: Int
    ): Book {
        val book = Book(
            userId = userId,
            title = title,
            author = author,
            imageUrl = imageUrl,
            totalPage = totalPage
        )
        return bookRepository.save(book)
    }

    fun getBookshelf(userId: Long): List<BookshelfItem> {
        val books = bookRepository.findByUserIdOrderByLatestBookmark(userId)
        return books.map { book ->
            val bookmarkCount = bookmarkRepository.countByBookId(book.id)
            BookshelfItem(
                book = book,
                bookmarkCount = bookmarkCount,
                progress = book.calculateProgress()
            )
        }
    }

    fun getBookDetail(bookId: Long): BookDetail {
        val book = bookRepository.findById(bookId)
            ?: throw IllegalArgumentException("Book not found: $bookId")
        val bookmarkCount = bookmarkRepository.countByBookId(bookId)
        return BookDetail(
            book = book,
            bookmarkCount = bookmarkCount,
            progress = book.calculateProgress()
        )
    }

    fun getTextBookmarks(bookId: Long): List<Bookmark> {
        return bookmarkRepository.findTextBookmarksByBookId(bookId)
    }

    fun getPhotoBookmarks(bookId: Long): List<Bookmark> {
        return bookmarkRepository.findPhotoBookmarksByBookId(bookId)
    }

    @Transactional
    fun addBookmark(
        bookId: Long,
        pageNumber: Int,
        extractedText: String,
        note: String?,
        imageUrl: String?
    ): Bookmark {
        if (!bookRepository.existsById(bookId)) {
            throw IllegalArgumentException("Book not found: $bookId")
        }

        val bookmark = Bookmark(
            bookId = bookId,
            pageNumber = pageNumber,
            extractedText = extractedText,
            note = note,
            imageUrl = imageUrl
        )
        val savedBookmark = bookmarkRepository.save(bookmark)

        bookRepository.incrementBookmarkCountAndUpdateCurrentPage(bookId, pageNumber)

        return savedBookmark
    }

    @Transactional
    fun markBookAsCompleted(bookId: Long): Book {
        val book = bookRepository.findById(bookId)
            ?: throw IllegalArgumentException("Book not found: $bookId")
        val completedBook = book.markAsCompleted()
        return bookRepository.save(completedBook)
    }

    fun getLatestBookmark(userId: Long): Bookmark? {
        return bookmarkRepository.findLatestByUserId(userId)
    }
}
