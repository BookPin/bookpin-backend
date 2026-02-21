package com.bookpin.presentation.book.swagger

import com.bookpin.app.book.request.AddBookRequest
import com.bookpin.app.book.request.AddBookmarkRequest
import com.bookpin.app.book.response.BookResponse
import com.bookpin.app.book.response.BookSearchResponse
import com.bookpin.app.book.response.BookmarkResponse
import com.bookpin.app.book.response.BookshelfResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Book", description = "책 관련 API")
interface BookControllerSwagger {

    @Operation(summary = "책 검색", description = "제목으로 책을 검색합니다.")
    fun searchBooks(@RequestParam query: String): ResponseEntity<List<BookSearchResponse>>

    @Operation(summary = "책 추가", description = "책장에 새로운 책을 추가합니다.")
    fun addBook(@RequestBody request: AddBookRequest): ResponseEntity<BookResponse>

    @Operation(summary = "책장 목록 조회", description = "본인의 책장 목록을 조회합니다.")
    fun getBookshelf(): ResponseEntity<List<BookshelfResponse>>

    @Operation(summary = "책 상세 조회", description = "책의 상세 정보를 조회합니다.")
    fun getBookDetail(@PathVariable bookId: Long): ResponseEntity<BookResponse>

    @Operation(summary = "텍스트 책갈피 목록 조회", description = "책의 텍스트 책갈피 목록을 조회합니다.")
    fun getTextBookmarks(@PathVariable bookId: Long): ResponseEntity<List<BookmarkResponse>>

    @Operation(summary = "사진 책갈피 목록 조회", description = "책의 사진 책갈피 목록을 조회합니다.")
    fun getPhotoBookmarks(@PathVariable bookId: Long): ResponseEntity<List<BookmarkResponse>>

    @Operation(summary = "책갈피 추가", description = "책에 새로운 책갈피를 추가합니다.")
    fun addBookmark(
        @PathVariable bookId: Long,
        @RequestBody request: AddBookmarkRequest
    ): ResponseEntity<BookmarkResponse>

    @Operation(summary = "책 완독 처리", description = "책을 완독 처리합니다.")
    fun markBookAsCompleted(@PathVariable bookId: Long): ResponseEntity<BookResponse>

    @Operation(summary = "내가 기록한 순간", description = "가장 최근에 기록한 책갈피를 조회합니다.")
    fun getLatestBookmark(): ResponseEntity<BookmarkResponse>
}
