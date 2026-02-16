package com.bookpin.infrastructure.book.jpa

import com.bookpin.infrastructure.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "bookmarks")
class BookmarkEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val bookId: Long,

    @Column(nullable = false)
    val pageNumber: Int,

    @Column(nullable = false, length = 2000)
    val extractedText: String,

    @Column(length = 1000)
    var note: String? = null,

    @Column(length = 500)
    val imageUrl: String? = null
) : BaseEntity()
