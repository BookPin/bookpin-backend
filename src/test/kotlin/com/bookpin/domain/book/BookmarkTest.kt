package com.bookpin.domain.book

import com.bookpin.domain.fixture.BookmarkFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BookmarkTest {

    @Test
    fun `pageNumber가 0이면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookmarkFixture.generate(pageNumber = 0)
        }
    }

    @Test
    fun `pageNumber가 음수면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookmarkFixture.generate(pageNumber = -1)
        }
    }

    @Test
    fun `extractedText가 빈 문자열이면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookmarkFixture.generate(extractedText = "   ")
        }
    }

    @Test
    fun `extractedText가 2000자를 초과하면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookmarkFixture.generate(extractedText = "a".repeat(2001))
        }
    }

    @Test
    fun `note가 1000자를 초과하면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            BookmarkFixture.generate(note = "a".repeat(1001))
        }
    }

    @Test
    fun `note가 null이어도 정상적으로 생성된다`() {
        val bookmark = BookmarkFixture.generate(note = null)

        assertNull(bookmark.note)
    }

    @Test
    fun `이미지가 있으면 사진 북마크이다`() {
        val bookmark = BookmarkFixture.generatePhotoBookmark()

        assertTrue(bookmark.isPhotoBookmark())
        assertFalse(bookmark.isTextBookmark())
    }

    @Test
    fun `이미지가 없으면 텍스트 북마크이다`() {
        val bookmark = BookmarkFixture.generate(imageUrl = null)

        assertTrue(bookmark.isTextBookmark())
        assertFalse(bookmark.isPhotoBookmark())
    }

    @Test
    fun `메모를 업데이트할 수 있다`() {
        val bookmark = BookmarkFixture.generate(note = "original note")
        val newNote = "updated note"

        val updatedBookmark = bookmark.updateNote(newNote = newNote)

        assertEquals(newNote, updatedBookmark.note)
    }

    @Test
    fun `메모 업데이트 시 updatedAt이 갱신된다`() {
        val bookmark = BookmarkFixture.generate()
        val originalUpdatedAt = bookmark.updatedAt
        Thread.sleep(10)

        val updatedBookmark = bookmark.updateNote(newNote = "new note")

        assertNotEquals(originalUpdatedAt, updatedBookmark.updatedAt)
    }

    @Test
    fun `메모를 null로 업데이트할 수 있다`() {
        val bookmark = BookmarkFixture.generate(note = "original note")

        val updatedBookmark = bookmark.updateNote(newNote = null)

        assertNull(updatedBookmark.note)
    }

    @Test
    fun `메모 업데이트 시 1000자를 초과하면 예외가 발생한다`() {
        val bookmark = BookmarkFixture.generate()

        assertThrows<IllegalArgumentException> {
            bookmark.updateNote(newNote = "a".repeat(1001))
        }
    }
}
