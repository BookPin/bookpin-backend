package com.bookpin.domain.notification

import com.bookpin.domain.fixture.NotificationMessageFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NotificationMessageTest {

    @Test
    fun `token이 빈 문자열이면 예외가 발생한다`() {
        // given
        val blankToken = "   "

        // when & then
        assertThrows<IllegalArgumentException> {
            NotificationMessageFixture.generate(token = blankToken)
        }
    }

    @Test
    fun `title이 빈 문자열이면 예외가 발생한다`() {
        // given
        val blankTitle = "   "

        // when & then
        assertThrows<IllegalArgumentException> {
            NotificationMessageFixture.generate(title = blankTitle)
        }
    }

    @Test
    fun `body가 빈 문자열이면 예외가 발생한다`() {
        // given
        val blankBody = "   "

        // when & then
        assertThrows<IllegalArgumentException> {
            NotificationMessageFixture.generate(body = blankBody)
        }
    }

    @Test
    fun `유효한 값으로 NotificationMessage를 생성할 수 있다`() {
        // given
        val token = "valid-fcm-token"
        val title = "Test Title"
        val body = "Test Body"
        val data = mapOf("key" to "value")

        // when
        val message = NotificationMessage(
            token = token,
            title = title,
            body = body,
            data = data
        )

        // then
        assertEquals(token, message.token)
        assertEquals(title, message.title)
        assertEquals(body, message.body)
        assertEquals(data, message.data)
    }

    @Test
    fun `data를 지정하지 않으면 빈 Map이 설정된다`() {
        // given
        val token = "valid-fcm-token"
        val title = "Test Title"
        val body = "Test Body"

        // when
        val message = NotificationMessage(
            token = token,
            title = title,
            body = body
        )

        // then
        assertEquals(emptyMap<String, String>(), message.data)
    }

}
