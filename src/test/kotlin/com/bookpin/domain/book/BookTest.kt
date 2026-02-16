package com.bookpin.domain.book

import com.bookpin.domain.fixture.BookFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BookTest {

    @Test
    fun `title이 빈 문자열이면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookFixture.generate(title = "   ")
        }
    }

    @Test
    fun `title이 200자를 초과하면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookFixture.generate(title = "a".repeat(201))
        }
    }

    @Test
    fun `author가 빈 문자열이면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookFixture.generate(author = "   ")
        }
    }

    @Test
    fun `author가 100자를 초과하면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookFixture.generate(author = "a".repeat(101))
        }
    }

    @Test
    fun `totalPage가 0이면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookFixture.generate(totalPage = 0)
        }
    }

    @Test
    fun `currentPage가 음수면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookFixture.generate(currentPage = -1)
        }
    }

    @Test
    fun `currentPage가 totalPage보다 크면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookFixture.generate(
                totalPage = 100,
                currentPage = 101
            )
        }
    }

    @Test
    fun `진행률을 계산할 수 있다`() {
        val book = BookFixture.generate(
            totalPage = 200,
            currentPage = 50
        )

        val progress = book.calculateProgress()

        assertEquals(25.0, progress)
    }

    @Test
    fun `진행률은 소수점 첫째 자리까지 표현한다`() {
        val book = BookFixture.generate(
            totalPage = 300,
            currentPage = 100
        )

        val progress = book.calculateProgress()

        assertEquals(33.3, progress)
    }

    @Test
    fun `현재 페이지를 업데이트할 수 있다`() {
        val book = BookFixture.generate(
            totalPage = 200,
            currentPage = 0
        )

        val updatedBook = book.updateCurrentPage(page = 50)

        assertEquals(50, updatedBook.currentPage)
    }

    @Test
    fun `현재 페이지 업데이트 시 updatedAt이 갱신된다`() {
        val book = BookFixture.generate()
        val originalUpdatedAt = book.updatedAt
        Thread.sleep(10)

        val updatedBook = book.updateCurrentPage(page = 10)

        assertNotEquals(originalUpdatedAt, updatedBook.updatedAt)
    }

    @Test
    fun `현재 페이지를 totalPage보다 크게 업데이트하면 예외가 발생한다`() {
        val book = BookFixture.generate(totalPage = 100)

        assertThrows<IllegalArgumentException> {
            book.updateCurrentPage(page = 101)
        }
    }

    @Test
    fun `책을 완독 처리할 수 있다`() {
        val book = BookFixture.generate(
            totalPage = 200,
            currentPage = 100
        )

        val completedBook = book.markAsCompleted()

        assertTrue(completedBook.isCompleted)
        assertEquals(200, completedBook.currentPage)
    }

    @Test
    fun `완독 처리 시 updatedAt이 갱신된다`() {
        val book = BookFixture.generate()
        val originalUpdatedAt = book.updatedAt
        Thread.sleep(10)

        val completedBook = book.markAsCompleted()

        assertNotEquals(originalUpdatedAt, completedBook.updatedAt)
    }

    @Test
    fun `읽기 진행 중인지 확인할 수 있다`() {
        val book = BookFixture.generate(
            totalPage = 200,
            currentPage = 50,
            isCompleted = false
        )

        assertTrue(book.isReadingInProgress())
    }

    @Test
    fun `아직 읽기 시작하지 않은 책은 진행 중이 아니다`() {
        val book = BookFixture.generate(
            currentPage = 0,
            isCompleted = false
        )

        assertFalse(book.isReadingInProgress())
    }

    @Test
    fun `완독한 책은 진행 중이 아니다`() {
        val book = BookFixture.generate(
            currentPage = 100,
            isCompleted = true
        )

        assertFalse(book.isReadingInProgress())
    }
}
