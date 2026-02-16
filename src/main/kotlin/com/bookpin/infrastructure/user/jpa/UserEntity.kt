package com.bookpin.infrastructure.user.jpa

import com.bookpin.domain.user.SocialType
import com.bookpin.infrastructure.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val socialId: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val socialProvider: SocialType,

    @Column
    var email: String? = null,

    @Column
    var nickname: String? = null,

    @Column
    var profileImageUrl: String? = null
) : BaseEntity()
