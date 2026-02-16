package com.bookpin.domain.book

import java.time.LocalDateTime

data class Bookmark(
    val id: Long = 0L,
    val bookId: Long,
    val pageNumber: Int,
    val extractedText: String,
    val note: String? = null,
    val imageUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = createdAt
) {

    companion object {
        private const val MIN_PAGE = 1
        private const val MAX_EXTRACTED_TEXT_LENGTH = 2000
        private const val MAX_NOTE_LENGTH = 1000
    }

    init {
        require(pageNumber >= MIN_PAGE) { "pageNumber must be $MIN_PAGE or greater" }
        require(extractedText.isNotBlank()) { "extractedText must not be blank" }
        require(extractedText.length <= MAX_EXTRACTED_TEXT_LENGTH) {
            "extractedText must be $MAX_EXTRACTED_TEXT_LENGTH characters or less"
        }
        note?.let {
            require(it.length <= MAX_NOTE_LENGTH) { "note must be $MAX_NOTE_LENGTH characters or less" }
        }
    }

    fun isPhotoBookmark(): Boolean {
        return imageUrl != null
    }

    fun isTextBookmark(): Boolean {
        return imageUrl == null
    }

    fun updateNote(newNote: String?): Bookmark {
        return copy(
            note = newNote,
            updatedAt = LocalDateTime.now()
        )
    }
}
