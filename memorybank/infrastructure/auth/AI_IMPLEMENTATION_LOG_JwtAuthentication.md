# AI Implementation Log - JWT Authentication 구현체들

## Context
- 기능 / 모듈 이름: JWT 기반 인증 처리
- 패키지: `com.book.Archiving.infrastructure.auth`
- 파일들:
  - `UserPrincipal.kt`
  - `JwtAuthenticationFilter.kt`
  - `SecurityUserContextHolder.kt`
- 작성 일자: 2025-01-24
- 작성 주체: AI (인간 리뷰 예정)

---

## Problem Statement
이 변경으로 해결하려는 문제는 무엇인가?
- JWT 토큰을 파싱하여 Spring Security Context에 인증 정보 저장
- domain의 UserContextHolder 인터페이스 구현

주어진 요구사항 또는 제약 조건은 무엇이었는가?
- rule.md: "Infrastructure는 Domain의 Client, Repository를 구현하는 계층"
- rule.md: "~Adapter가 infrastructure에서 구현"
- 1 파일 = 1 클래스 규칙
- 1 메서드 = 15줄 이하 규칙

---

## Design Decisions

### UserPrincipal.kt
- Spring Security의 `UserDetails` 구현
- `userId`와 `token`만 보관 (최소 정보 원칙)
- data class 사용으로 불변성 보장

### JwtAuthenticationFilter.kt
- `OncePerRequestFilter` 상속 (요청당 한 번만 실행 보장)
- 15줄 규칙 준수를 위해 메서드 분리:
  - `extractToken()`: 헤더에서 토큰 추출
  - `setAuthentication()`: SecurityContext에 인증 정보 저장
- companion object로 상수 관리

### SecurityUserContextHolder.kt
- `UserContextHolder` 인터페이스 구현
- `@Component`로 Spring Bean 등록
- SecurityContextHolder에서 UserPrincipal 조회

---

## Trade-offs
- 얻은 장점:
  - 레이어 분리 달성 (domain은 infrastructure 모름)
  - 테스트 용이성 (UserContextHolder Mock 가능)
- 감수한 단점:
  - 파일 수 증가 (3개 파일)
- 장기적으로 발생할 수 있는 리스크:
  - 권한(Role) 추가 시 UserPrincipal 확장 필요

---

## Future Evolution Points
- 토큰 만료 시 적절한 에러 응답 처리 필요
- Refresh Token 자동 갱신 로직 미구현
- 커스텀 AuthenticationEntryPoint 구현 고려
