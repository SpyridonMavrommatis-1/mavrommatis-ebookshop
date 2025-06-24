# 🔐 Spring Boot Security Configuration (JWT-Based)

This module defines the complete **security layer** for the eBookShop application using **Spring Security** and **JWT**. It provides stateless authentication and role-based access control for all `/api/**` routes.

---

## ✨ Key Features

* **JWT Authentication** using HMAC-SHA256
* **In-memory user management** for demo/testing
* **Scope-to-role mapping** for access control
* **Custom role extraction** from JWT `scope` claim
* **Stateless sessions** (no cookies, no server-side state)
* **Utility methods** for access checks inside services/controllers

---

## 🔐 Authentication Flow

```
Client (login request)
   ⬇
POST /api/authenticate  --->  JwtTokenProvider.createToken()
                             (generates JWT containing sub + scope)
   ⬆
Response: Bearer Token
   ⬇
Subsequent API Request with Authorization: Bearer <token>
   ⬇
Spring Security FilterChain
   ⬇
JwtDecoder (validates token signature)
   ⬇
CustomJwtAuthenticationConverter (extracts roles from "scope")
   ⬇
SecurityContextHolder.setAuthentication(...)
```

---

## ⚖️ Configuration Overview

### 1. `BaseSecurityConfig`

* Declares `@EnableMethodSecurity` for `@PreAuthorize` support
* Provides `PasswordEncoder` (BCrypt)
* Sets up `InMemoryUserDetailsManager` with 3 users: `customer`, `employee`, `admin`

### 2. `ApiSecurityConfig`

* Configures a stateless security filter for `/api/**`
* Disables CSRF and HTTP Basic
* Permits unauthenticated access to:

    * `POST /api/authenticate`
    * `GET /api/book-reviews/**`
* Applies JWT authentication via custom converter

### 3. `JwtConfig`

* Loads `jwt.secret` from application properties
* Provides a `JwtDecoder` using HMAC (HS256)

### 4. `JwtTokenProvider`

* Generates signed JWT tokens post-login
* Embeds:

    * `sub`: the username
    * `scope`: user roles (space-separated)
    * `iat`, `exp`: for token validity

---

## ⚖️ Custom Components

### `CustomJwtAuthenticationConverter`

* Implements `Converter<Jwt, Collection<GrantedAuthority>>`
* Reads the `scope` claim

    * e.g. `"ROLE_ADMIN ROLE_EMPLOYEE"`
* Converts scopes to Spring authorities

```json
{
  "sub": "admin",
  "scope": "ROLE_ADMIN ROLE_EMPLOYEE"
}
```

### `SecurityUtils`

Utility class for:

* `getCurrentUsername()`
* `isCustomer()`
* `hasRole("ROLE_EMPLOYEE")`

Useful in service, mapper, or controller logic.

---

## 💡 Highlights

* **Roles** are not hardcoded in endpoints but dynamically checked
* **Token** is the single source of truth (sub + scope)
* **No sessions** are stored on server side
* Supports full `@PreAuthorize` granularity with custom authority resolver

---

## 👀 Example JWT Payload

```json
{
  "sub": "employee",
  "preferred_username": "employee",
  "scope": "ROLE_EMPLOYEE",
  "iat": 1710000000,
  "exp": 1710003600
}
```

---

## 📃 Dependencies

* Spring Security
* `spring-boot-starter-oauth2-resource-server`
* `jjwt` (API + Impl + Jackson)

---

> 💡 This setup provides a clean, extensible, and secure foundation for any stateless API with role-based access control.
