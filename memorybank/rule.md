# AI 기반 SDD Memory Bank 전체 스펙

이 문서는 **툴에 의존하지 않고**, AI와 사람이 함께 일하기 위한
**경량 SDD(Specification-Driven Development) 규칙 모음**이다.

목표는 다음과 같다.

* 사고의 흔적을 남긴다
* 왜 그렇게 구현했는지 사라지지 않게 한다
* Kotlin 코드 품질을 일관되게 유지한다
* 형식보다 의도를 강제한다

---

## 1. Kotlin Coding Convention 요약 (AI + 인간 공용 Ground Rule)

본 규칙은 Kotlin 공식 문서([https://kotlinlang.org/docs/coding-conventions.html)를](https://kotlinlang.org/docs/coding-conventions.html%29를) 기반으로 하며,
**"AI가 코드를 생성할 때 반드시 지켜야 할 규칙"**을 기준으로 재정리하였다.

### 1.1 디렉토리 및 파일 구조

* 패키지 구조와 실제 디렉토리 구조는 반드시 일치해야 한다
* 패키지 이름은 모두 소문자를 사용한다
* 파일 이름은 해당 파일의 주요 클래스 또는 개념과 동일한 이름을 사용한다 (UpperCamelCase)
* 의미가 모호한 이름(Util, Helper, Manager 등)은 지양한다
* 서로 밀접하게 연관된 top-level 함수나 확장 함수는 같은 파일에 둔다

---

### 1.2 클래스 내부 구성 순서

클래스 내부 멤버는 **논리적 흐름 기준**으로 다음 순서를 권장한다.

1. 프로퍼티 선언 및 init 블록
2. 보조 생성자
3. 공개 메서드 → 내부 메서드 순
4. companion object

알파벳 순 정렬은 지양하며, 읽는 사람이 자연스럽게 흐름을 따라갈 수 있도록 배치한다.

---

### 1.3 네이밍 규칙

* 패키지: 소문자만 사용 (camelCase 허용, snake_case 금지)
* 클래스 / 인터페이스 / 객체: UpperCamelCase
* 함수 / 변수 / 프로퍼티: lowerCamelCase
* 상수(const val, top-level immutable): SCREAMING_SNAKE_CASE
* private backing property: `_propertyName`

#### 약어 사용 규칙

* 2글자 약어: 모두 대문자 (IOStream)
* 3글자 이상 약어: 첫 글자만 대문자 (HttpClient, XmlParser)

---

### 1.4 포맷팅 규칙

* 들여쓰기: 공백 4칸, 탭 사용 금지
* 중괄호는 Java 스타일을 따른다
* 이항 연산자 주변에는 공백을 둔다 (`a + b`)
* 제어문과 괄호 사이에는 공백을 둔다 (`if (condition)`)
* 함수 호출 및 선언에서 괄호 앞에는 공백을 두지 않는다
* `.` `?.` `!!` 주변에는 공백을 두지 않는다

---

### 1.5 함수 / 클래스 작성 스타일

* 짧고 단순한 함수는 expression body를 사용한다
* 파라미터가 길어질 경우 줄바꿈 후 4칸 들여쓰기
* 함수 오버로딩보다는 default parameter를 우선한다

---

### 1.6 Kotlin답게 작성하기 (중요)

* 가능한 한 `var` 대신 `val`을 사용한다
* 변경되지 않는 컬렉션은 `List`, `Set`, `Map` 인터페이스 타입으로 선언한다
* `if`, `when`, `try`는 statement가 아니라 expression으로 사용한다
* 단순 반복은 for-loop를 허용하되, 의미 있는 변환은 HOF(map, filter 등)를 사용한다
* 문자열 결합 대신 string template를 사용한다
* multiline string 사용 시 trimIndent 또는 trimMargin을 사용한다

---

## 2. AI 구현 히스토리 기록 포맷 (Memory Bank 고정 템플릿)

이 문서는 **"무엇을 만들었는지"가 아니라, "왜 그렇게 만들었는지"**를 남기기 위한 로그이다.

아래 포맷은 AI가 구현 후 반드시 남겨야 하는 **고정 질문형 템플릿**이다.

---

### 파일명 권장

* `AI_IMPLEMENTATION_LOG.md`
* 기능 단위 또는 모듈 단위로 생성 가능

---

### 템플릿

```md
# AI Implementation Log

## Context
- 기능 / 모듈 이름:
- 연관된 스펙 또는 ADR:
- 작성 일자:
- 작성 주체: AI (인간 리뷰 예정)

---

## Problem Statement
이 변경으로 해결하려는 문제는 무엇인가?
-

주어진 요구사항 또는 제약 조건은 무엇이었는가?
-

---

## Design Decisions
### 고려했던 선택지들
- 선택지 A:
- 선택지 B:
- 선택지 C:

### 최종 선택
- 이 방식을 선택한 이유:
- 다른 선택지를 포기한 이유:

---

## Trade-offs
- 얻은 장점:
- 감수한 단점:
- 장기적으로 발생할 수 있는 리스크:

---

## Key Implementation Notes
- 핵심 구조 또는 알고리즘 요약:
- Kotlin 컨벤션 관련 주의한 부분:
- 성능 / 동시성 / 확장성 고려 사항:

---

## Future Evolution Points
- 향후 변경 가능성이 높은 지점:
- 남겨둔 TODO 또는 기술 부채:

---

## AI Reflection (선택)
- 구현 중 확신이 없었던 부분:
- 인간 리뷰가 반드시 필요한 판단:
- 다음 번에 개선할 수 있는 아이디어:
```

---

## 3. 프로젝트 공통 Ground Rule (AI 헌법 문서)

이 문서는 프로젝트 전반에서 **AI와 사람이 공유하는 불변 규칙**이다.

### 파일명 권장

* `MEMORY_BANK_GROUND_RULES.md`

---

### 내용

```md
# Ground Rules for AI-assisted Development

## 핵심 원칙
- 모든 코드는 Kotlin 공식 코딩 컨벤션을 따른다
- 똑똑해 보이는 코드보다 읽기 쉬운 코드를 우선한다
- 불변성을 기본값으로 생각한다
- 사소하지 않은 결정은 반드시 구현 로그로 남긴다

---

## 문서화 원칙
- 기능 하나당 최소 하나의 AI_IMPLEMENTATION_LOG를 남긴다
- 문서는 "무엇"보다 "왜"에 집중한다
- 포맷은 고정하되, 내용은 자유롭게 작성한다

---

## 아키텍처 철학
- 명시적인 구조를 선호한다
- 마법 같은 추상화는 지양한다
- 필요해질 때까지 확장하지 않는다

---

## AI 행동 제약
- 근거 없는 패턴 도입 금지
- 요구사항에 없는 과도한 확장성 고려 금지
- 새로운 라이브러리 도입 시 반드시 이유를 문서로 남긴다
```

---

## 4. 이 시스템의 의도

이 Memory Bank 구조는 다음을 목표로 한다.

* 미래의 내가 코드를 다시 봤을 때 맥락을 빠르게 복원할 수 있도록 한다
* AI가 이전 결정들을 존중하며 일관성 있게 구현하도록 한다
* SDD의 본질(사고 → 결정 → 구현)을 최소 비용으로 유지한다

형식을 지키는 것이 목적이 아니라,
**생각을 남기는 것이 목적**이다.

## Software Architecture

presentation, app, domain, infrastructure 패키지 구조로 구현한다.

### Presentation

대게 Presentation에는 Controller가 위치한다.

### App

대게 App에는 Service가 위치한다.

### Domain

Domain에는 비즈니스 로직에 중심이 되는 클래스들 위주로 작성이 된다. (POJO (Plain old java object) 해야 한다. )

또한 외부 API를 사용하는 경우 ~Client, Persistence 계층에 접근하는 경우는 ~Repository라는 네이밍과 함께 Interface를 제공한다.

### Infrastructure

Domain 딴의 Client, Repository를 구현하는 계층이다.

이 계층에는 Entity가 위치할 수 있다. (Not Pojo)

Client와 Repository를 실질적으로 구현하는 ~Adapter가 위치할 수 있다.

Adapter 내부에서는 실제로 구체적인 FeignClient, JpaRepository등의 기술을 사용할 수 있다.

Entity와 Domain 객체간의 변환을 책임져 주는 Mapper를 해당 infrastructure에서 구현한다.

Presentation, App, Domain 모두 infrastructure에 의존성을 가져서는 안된다.

## 테스트

특정 도메인을 구현했을 때, 그 도메인에 사용되는 테스트를 무조건 작성한다.

도메인에 사용되는 테스트를 작성할 때에는 쉬운 객체 생성을 위해 Fixture라는 개념을 차용한다.

### RandomGenerator

값을 무작위로 제공하는 구현체이다.

```kotlin
object RandomGenerator {
    private val RANDOM = Random()
    private const val NULL_VALUE_PROBABILITY = 0.1

    fun generateNonNullString(length: Int): String {
        return RandomStringUtils.randomAlphabetic(length)
    }

    fun generateString(length: Int): String? {
        return generateNullAbleObject {
            RandomStringUtils.randomAlphabetic(
                length
            )
        }
    }

    fun generateNonNullNumeric(length: Int): Int {
        return RandomStringUtils.randomNumeric(length).toInt()
    }

    fun generateNumeric(length: Int): Int? {
        return generateNullAbleObject {
            RandomStringUtils.randomNumeric(
                length
            ).toInt()
        }
    }

    private fun <T> generateNullAbleObject(supplier: Supplier<T>): T? {
        val probability = RANDOM.nextDouble()

        if (probability < NULL_VALUE_PROBABILITY) {
            return null
        }

        return supplier.get()
    }

    fun generateNonNullDouble(numberLength: Int, decimalLength: Int): Double {
        return getRandomDouble(numberLength, decimalLength)
    }

    private fun getRandomDouble(numberLength: Int, decimalLength: Int): Double {
        return (RandomStringUtils.randomNumeric(numberLength) +
                "." + RandomStringUtils.randomNumeric(decimalLength)).toDouble()
    }

    fun generateDouble(numberLength: Int, decimalLength: Int): Double? {
        return generateNullAbleObject {
            getRandomDouble(
                numberLength,
                decimalLength
            )
        }
    }

    fun generateBoolean(): Boolean {
        return RANDOM.nextBoolean()
    }

    fun <ENUM : Enum<*>> generateEnum(enumClass: KClass<ENUM>): ENUM? {
        val enumConstants = enumClass.java.enumConstants
        return generateNullAbleObject { enumConstants[RANDOM.nextInt(enumConstants.size)] }
    }

    fun <ENUM : Enum<*>> generateNonNullEnum(enumClass: KClass<ENUM>): ENUM {
        val enumConstants = enumClass.java.enumConstants
        return enumConstants[RANDOM.nextInt(enumConstants.size)]
    }

    fun generateNonNullCharacter(): Char {
        return RandomStringUtils.randomAlphabetic(1)[0]
    }

    fun generateCharacter(): Char? {
        return generateNullAbleObject {
            RandomStringUtils.randomAlphabetic(
                1
            )[0]
        }
    }

    fun generateNonNullUUID(): UUID {
        return UUID.randomUUID()
    }

    fun generateUUID(): UUID? {
        return generateNullAbleObject { UUID.randomUUID() }
    }

}
```

아래와 같이 Fixture를 구현한다.

```kotlin
// User Class
data class User(
    val id: Long = 0,
    val socialId: String,
    val socialProvider: SocialType,
    val email: String? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    // ...
}

// Fixture
object UserFixture {

    fun generate(
        id: Long = RandomGenerator.generateNonNullNumeric(2).toLong(),
        socialId: String = RandomGenerator.generateNonNullString(5),
        socialProvider: SocialType = RandomGenerator.generateNonNullEnum(SocialType::class),
        email: String? = "${RandomGenerator.generateNonNullString(5)}@test.com",
        nickname: String? = RandomGenerator.generateNonNullString(5),
        profileImageUrl: String? = RandomGenerator.generateNonNullString(5),
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now()
    ): User {
        return User(
            id = id,
            socialId = socialId,
            socialProvider = socialProvider,
            email = email,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

}
```

실제 테스트 코드 작성 예시는 아래와 같다.

```kotlin
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
```

## 1개의 클래스 파일에는 1개의 클래스만이 존재해야한다.

## 1개의 메서드는 15줄을 넘어갈 수 없다.

## 메서드의 Argument가 2개 이상인 경우 Named Argument를 활용한다.

## companion object 같은 static resource는 가장 위에 작성한다.