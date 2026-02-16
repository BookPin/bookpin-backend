package com.bookpin.domain.book

import java.time.LocalDateTime

data class Book(
    val id: Long = 0L,
    val userId: Long,
    val title: String,
    val author: String,
    val imageUrl: String? = null,
    val totalPage: Int,
    val currentPage: Int = 0,
    val bookmarkCount: Int = 0,
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = createdAt
) {

    companion object {
        private const val MIN_PAGE = 0
        private const val MAX_TITLE_LENGTH = 200
        private const val MAX_AUTHOR_LENGTH = 100
    }

    init {
        require(title.isNotBlank()) { "title must not be blank" }
        require(title.length <= MAX_TITLE_LENGTH) { "title must be $MAX_TITLE_LENGTH characters or less" }
        require(author.isNotBlank()) { "author must not be blank" }
        require(author.length <= MAX_AUTHOR_LENGTH) { "author must be $MAX_AUTHOR_LENGTH characters or less" }
        require(totalPage > MIN_PAGE) { "totalPage must be greater than $MIN_PAGE" }
        require(currentPage >= MIN_PAGE) { "currentPage must be $MIN_PAGE or greater" }
        require(currentPage <= totalPage) { "currentPage must be less than or equal to totalPage" }
        require(currentPage in MIN_PAGE..totalPage) {
            "현재 페이지는 ${MIN_PAGE}와 ${totalPage}사이에만 존재할 수 있습니다."
        }
    }

    fun calculateProgress(): Double {
        if (totalPage == 0) return 0.0
        val progress = (currentPage.toDouble() / totalPage) * 100
        return Math.round(progress * 10) / 10.0
    }

    fun updateCurrentPage(page: Int): Book {
        return copy(
            currentPage = page,
            updatedAt = LocalDateTime.now()
        )
    }

    fun markAsCompleted(): Book {
        return copy(
            currentPage = totalPage,
            isCompleted = true,
            updatedAt = LocalDateTime.now()
        )
    }

    fun isReadingInProgress(): Boolean {
        return currentPage > MIN_PAGE && !isCompleted
    }
}
