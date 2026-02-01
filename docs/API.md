# Bookpin API 명세

## 인증 (Auth)

### 소셜 로그인

소셜 로그인을 통해 사용자 인증을 수행합니다.

- **URL:** `POST /api/v1/auth/login`
- **Content-Type:** `application/json`

#### Request

```json
{
  "socialType": "KAKAO",
  "accessToken": "카카오/애플에서 발급받은 액세스 토큰"
}
```

#### Request Fields

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| socialType | String | O | `KAKAO` 또는 `APPLE` |
| accessToken | String | O | 소셜 로그인 제공자로부터 받은 액세스 토큰 |

#### Response

```json
{
  "userId": 1,
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzA...",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzA...",
  "isNewUser": true
}
```

#### Response Fields

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| userId | Long | 사용자 ID |
| accessToken | String | JWT 액세스 토큰 (API 호출용) |
| refreshToken | String | JWT 리프레시 토큰 (토큰 갱신용) |
| isNewUser | Boolean | 신규 가입 여부 (`true`: 신규, `false`: 기존) |

---

### 토큰 갱신

리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급받습니다.

- **URL:** `POST /api/v1/auth/refresh`
- **Content-Type:** `application/json`

#### Request

```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzA..."
}
```

#### Request Fields

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| refreshToken | String | O | 기존에 발급받은 리프레시 토큰 |

#### Response

```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzA...",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzA..."
}
```

#### Response Fields

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| accessToken | String | 새로 발급된 JWT 액세스 토큰 |
| refreshToken | String | 새로 발급된 JWT 리프레시 토큰 |
