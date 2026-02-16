package com.bookpin.infrastructure.book.search

import com.bookpin.domain.book.client.BookSearchResult
import com.bookpin.domain.book.repository.BookSearchSource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BookSearchMergerTest {

    @Test
    fun `같은 ISBN이면 우선순위가 높은 소스의 결과를 반환한다`() {
        val kakaoResult = createResult("978-89-123", BookSearchSource.KAKAO)
        val naverResult = createResult("978-89-123", BookSearchSource.NAVER)
        val aladinResult = createResult("978-89-123", BookSearchSource.ALADIN)

        val merged = BookSearchMerger.merge(listOf(kakaoResult, naverResult, aladinResult))

        assertEquals(1, merged.size)
        assertEquals(BookSearchSource.ALADIN, merged[0].source)
    }

    @Test
    fun `ISBN이 다르면 모두 유지된다`() {
        val book1 = createResult("978-89-001", BookSearchSource.KAKAO)
        val book2 = createResult("978-89-002", BookSearchSource.NAVER)
        val book3 = createResult("978-89-003", BookSearchSource.ALADIN)

        val merged = BookSearchMerger.merge(listOf(book1, book2, book3))

        assertEquals(3, merged.size)
    }

    @Test
    fun `빈 리스트는 빈 리스트를 반환한다`() {
        val merged = BookSearchMerger.merge(emptyList())

        assertEquals(0, merged.size)
    }

    @Test
    fun `ALADIN이 NAVER보다 우선순위가 높다`() {
        val naverResult = createResult("978-89-123", BookSearchSource.NAVER)
        val aladinResult = createResult("978-89-123", BookSearchSource.ALADIN)

        val merged = BookSearchMerger.merge(listOf(naverResult, aladinResult))

        assertEquals(BookSearchSource.ALADIN, merged[0].source)
    }

    @Test
    fun `NAVER가 KAKAO보다 우선순위가 높다`() {
        val kakaoResult = createResult("978-89-123", BookSearchSource.KAKAO)
        val naverResult = createResult("978-89-123", BookSearchSource.NAVER)

        val merged = BookSearchMerger.merge(listOf(kakaoResult, naverResult))

        assertEquals(BookSearchSource.NAVER, merged[0].source)
    }

    private fun createResult(isbn: String, source: BookSearchSource): BookSearchResult {
        return BookSearchResult(
            title = "테스트 책",
            author = "테스트 저자",
            imageUrl = "https://example.com/image.jpg",
            totalPage = 300,
            isbn = isbn,
            publisher = "테스트 출판사",
            source = source
        )
    }
}
