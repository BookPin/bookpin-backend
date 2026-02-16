package com.bookpin.infrastructure.book.jpa

import com.bookpin.infrastructure.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "books")
class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false, length = 200)
    val title: String,

    @Column(nullable = false, length = 100)
    val author: String,

    @Column(length = 500)
    val imageUrl: String? = null,

    @Column(nullable = false)
    val totalPage: Int,

    @Column(nullable = false)
    var currentPage: Int = 0,

    @Column(nullable = false)
    var bookmarkCount: Int = 0,

    @Column(nullable = false)
    var isCompleted: Boolean = false
) : BaseEntity()
