# 📘 Postman Testing Scenarios for `AuthorBook` Endpoints

This README outlines the testing lifecycle of the `/api/author-books` endpoints in Postman for supported roles (`EMPLOYEE`, `ADMIN`) under JWT-based authentication.

---

## 🔐 Authentication Setup

All roles are authenticated via `/api/authenticate` using a body with:

```json
{
  "username": "admin",
  "password": "admin"
}
```

The response provides a JWT token used in subsequent requests via the `Authorization: Bearer <token>` header.

---

## ⚙️ General Request Execution Path

Each `/api/author-books/**` request follows this flow:

```
🔻 Incoming HTTP Request (Postman, browser, client)
  ➞ 🔐 Spring Security Filter Chain
    ➞ JwtAuthenticationFilter: Decodes & validates token
    ➞ SecurityContext: Sets up authenticated Principal
  ➞ 🎯 Controller Layer (AuthorBookRestController)
    ➞ Role-validated via @PreAuthorize
    ➞ Delegates to Service Layer
  ➞ ⚙️ Service Layer (AuthorBookServiceImpl)
    ➞ Fetches or transforms association
    ➞ Uses AuthorBookMapper for DTO ⇔ Entity
  ➞ 📂 Repository Layer (AuthorBookRepository)
    ➞ DB operations via Spring Data JPA
  ➞ ↩️ Response serialized to JSON
```

---

## 🪙 Tested Endpoints

### 1. `GET /api/author-books`

**Access:** ✅ `EMPLOYEE`, `ADMIN`  
**Forbidden:** ❌ `CUSTOMER`

**Behavior:**  
Returns a list of all associations (authorId, bookId) currently in the `author_book` join table.

**Flow:**  
`AuthorBookRestController#getAll()` → `AuthorBookServiceImpl#findAll()` → mapping to response DTO

---

### 2. `GET /api/author-books/{authorId}/{bookId}`

**Access:** ✅ `EMPLOYEE`, `ADMIN`  
**Forbidden:** ❌ `CUSTOMER`

**Behavior:**  
Returns a single author-book association by composite key.  
Throws 404 (RuntimeException) if not found.

**Flow:**  
`AuthorBookRestController#getById()` → `AuthorBookServiceImpl#findById()`

---

## 🚫 Removed Endpoints

The following endpoints were **removed** as part of simplification, since the author-book association is created at book insertion time and shouldn't be modified arbitrarily.

- ❌ `POST /api/author-books/connect`
- ❌ `DELETE /api/author-books/{authorId}/{bookId}`
- ❌ `PUT /api/author-books/...`
- ❌ `DELETE /api/author-books/batch`

---

## ⚠️ Observations

- The author-book table is treated as a **pure association**.
- No direct creation or deletion is exposed to clients — it's implicitly managed.
- `CUSTOMER` role is denied all access to this resource.

---

## ✅ Conclusion

The `/api/author-books` endpoints serve read-only visibility for managing the join state between books and authors.  
Access is restricted to employees and admins, with all mutation operations delegated to internal logic.
