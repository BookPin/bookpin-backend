# AI Implementation Log - UserContextHolder Interface

## Context
- 기능 / 모듈 이름: UserContextHolder (인증된 사용자 컨텍스트 조회 Interface)
- 패키지: `com.book.Archiving.domain.auth`
- 파일: `UserContextHolder.kt`
- 작성 일자: 2025-01-24
- 작성 주체: AI (인간 리뷰 예정)

---

## Problem Statement
이 변경으로 해결하려는 문제는 무엇인가?
- App/Domain 레이어에서 현재 인증된 사용자 정보를 조회해야 함
- Spring Security에 직접 의존하지 않고 사용자 정보를 가져올 수 있는 추상화 필요

주어진 요구사항 또는 제약 조건은 무엇이었는가?
- rule.md: "Domain에는 비즈니스 로직에 중심이 되는 클래스들 위주로 작성 (POJO)"
- rule.md: "외부 API를 사용하는 경우 ~Client, Persistence 계층에 접근하는 경우는 ~Repository"

---

## Design Decisions
### 고려했던 선택지들
- 선택지 A: `UserContextClient` 네이밍
- 선택지 B: `UserContextHolder` 네이밍
- 선택지 C: `AuthContext` 네이밍

### 최종 선택
- `UserContextHolder` 선택
- 이유: Spring의 `SecurityContextHolder`와 유사한 패턴으로 직관적
- Client는 외부 API 호출을 의미하므로 부적절

---

## Key Implementation Notes
```kotlin
interface UserContextHolder {
    fun getUserId(): Long
    fun getToken(): String
    fun isAuthenticated(): Boolean
}
```
- 최소한의 메서드만 정의하여 단순성 유지
- POJO 원칙 준수 (Spring 의존성 없음)
