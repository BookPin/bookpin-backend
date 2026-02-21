package com.bookpin.presentation.book

import com.bookpin.app.book.BookService
import com.bookpin.app.book.request.AddBookRequest
import com.bookpin.app.book.request.AddBookmarkRequest
import com.bookpin.app.book.response.BookResponse
import com.bookpin.app.book.response.BookSearchResponse
import com.bookpin.app.book.response.BookmarkResponse
import com.bookpin.app.book.response.BookshelfResponse
import com.bookpin.domain.auth.UserContextHolder
import com.bookpin.presentation.book.swagger.BookControllerSwagger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/books")
class BookController(
    private val bookService: BookService,
    private val userContextHolder: UserContextHolder
) : BookControllerSwagger {

    @GetMapping("/search")
    override fun searchBooks(
        @RequestParam query: String
    ): ResponseEntity<List<BookSearchResponse>> {
        val results = bookService.searchBooks(query)
        return ResponseEntity.ok(results.map { BookSearchResponse.from(it) })
    }

    @PostMapping
    override fun addBook(
        @RequestBody request: AddBookRequest
    ): ResponseEntity<BookResponse> {
        val userId = userContextHolder.getUserId()
        val book = bookService.addBook(
            userId = userId,
            title = request.title,
            author = request.author,
            imageUrl = request.imageUrl,
            totalPage = request.totalPage
        )
        return ResponseEntity.ok(BookResponse.from(book))
    }

    @GetMapping
    override fun getBookshelf(): ResponseEntity<List<BookshelfResponse>> {
        val userId = userContextHolder.getUserId()
        val bookshelf = bookService.getBookshelf(userId)
        return ResponseEntity.ok(bookshelf.map { BookshelfResponse.from(it) })
    }

    @GetMapping("/{bookId}")
    override fun getBookDetail(
        @PathVariable bookId: Long
    ): ResponseEntity<BookResponse> {
        val detail = bookService.getBookDetail(bookId)
        return ResponseEntity.ok(BookResponse.from(detail.book))
    }

    @GetMapping("/{bookId}/bookmarks/text")
    override fun getTextBookmarks(
        @PathVariable bookId: Long
    ): ResponseEntity<List<BookmarkResponse>> {
        val bookmarks = bookService.getTextBookmarks(bookId)
        return ResponseEntity.ok(bookmarks.map { BookmarkResponse.from(it) })
    }

    @GetMapping("/{bookId}/bookmarks/photo")
    override fun getPhotoBookmarks(
        @PathVariable bookId: Long
    ): ResponseEntity<List<BookmarkResponse>> {
        val bookmarks = bookService.getPhotoBookmarks(bookId)
        return ResponseEntity.ok(bookmarks.map { BookmarkResponse.from(it) })
    }

    @PostMapping("/{bookId}/bookmarks")
    override fun addBookmark(
        @PathVariable bookId: Long,
        @RequestBody request: AddBookmarkRequest
    ): ResponseEntity<BookmarkResponse> {
        val bookmark = bookService.addBookmark(
            bookId = bookId,
            pageNumber = request.pageNumber,
            extractedText = request.extractedText,
            note = request.note,
            imageUrl = request.imageUrl
        )
        return ResponseEntity.ok(BookmarkResponse.from(bookmark))
    }

    @PostMapping("/{bookId}/complete")
    override fun markBookAsCompleted(
        @PathVariable bookId: Long
    ): ResponseEntity<BookResponse> {
        val book = bookService.markBookAsCompleted(bookId)
        return ResponseEntity.ok(BookResponse.from(book))
    }

    @GetMapping("/latest-bookmark")
    override fun getLatestBookmark(): ResponseEntity<BookmarkResponse> {
        val userId = userContextHolder.getUserId()
        val bookmark = bookService.getLatestBookmark(userId)
            ?: return ResponseEntity.noContent().build()
        return ResponseEntity.ok(BookmarkResponse.from(bookmark))
    }
}
