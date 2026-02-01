package com.bookpin.domain.user

import com.bookpin.domain.fixture.UserFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserTest {

    @Test
    fun `socialId가 빈 문자열이면 예외가 발생한다`() {
        // given
        val blankSocialId = "   "

        // when & then
        assertThrows<IllegalArgumentException> {
            User(socialId = blankSocialId, socialProvider = SocialType.KAKAO)
        }
    }

    @Test
    fun `email에 @가 포함되지 않으면 예외가 발생한다`() {
        // given
        val invalidEmail = "invalid-email"

        // when & then
        assertThrows<IllegalArgumentException> {
            User(socialId = "12345", socialProvider = SocialType.KAKAO, email = invalidEmail)
        }
    }

    @Test
    fun `nickname이 20자를 초과하면 예외가 발생한다`() {
        // given
        val longNickname = "a".repeat(21)

        // when & then
        assertThrows<IllegalArgumentException> {
            User(socialId = "12345", socialProvider = SocialType.KAKAO, nickname = longNickname)
        }
    }

    @Test
    fun `프로필 정보를 업데이트할 수 있다`() {
        // given
        val user = UserFixture.generate()
        val newEmail = "new@example.com"
        val newNickname = "newNickname"
        val newProfileImageUrl = "https://example.com/new.jpg"

        // when
        val updatedUser = user.updateProfile(
            email = newEmail,
            nickname = newNickname,
            profileImageUrl = newProfileImageUrl
        )

        // then
        assertEquals(newEmail, updatedUser.email)
        assertEquals(newNickname, updatedUser.nickname)
        assertEquals(newProfileImageUrl, updatedUser.profileImageUrl)
    }

    @Test
    fun `일부 필드만 업데이트하면 나머지는 기존 값을 유지한다`() {
        // given
        val originalNickname = "originalNickname"
        val user = UserFixture.generate(nickname = originalNickname)
        val newEmail = "new@example.com"

        // when
        val updatedUser = user.updateProfile(email = newEmail)

        // then
        assertEquals(newEmail, updatedUser.email)
        assertEquals(originalNickname, updatedUser.nickname)
    }

    @Test
    fun `업데이트 시 updatedAt이 갱신된다`() {
        // given
        val user = UserFixture.generate()
        val originalUpdatedAt = user.updatedAt
        Thread.sleep(10)

        // when
        val updatedUser = user.updateProfile(nickname = "newNickname")

        // then
        assertNotEquals(originalUpdatedAt, updatedUser.updatedAt)
    }

    @Test
    fun `업데이트해도 불변 필드는 변경되지 않는다`() {
        // given
        val user = UserFixture.generate()

        // when
        val updatedUser = user.updateProfile(email = "new@example.com")

        // then
        assertEquals(user.id, updatedUser.id)
        assertEquals(user.socialId, updatedUser.socialId)
        assertEquals(user.socialProvider, updatedUser.socialProvider)
        assertEquals(user.createdAt, updatedUser.createdAt)
    }
}
