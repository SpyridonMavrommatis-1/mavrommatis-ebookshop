# 📘 Postman Testing Scenarios for `Book` Endpoints

This README outlines the full testing lifecycle of the `/api/books` endpoints in Postman for all supported roles (`CUSTOMER`, `EMPLOYEE`, `ADMIN`) with JWT-based authentication.

---

## 🔐 Authentication Setup

> All roles authenticate via `/api/authenticate` using credentials like:

```json
{
  "username": "admin",
  "password": "admin"
}
```

> The response provides a JWT token used in subsequent requests via the `Authorization: Bearer <token>` header.

---

## ⚙️ General Request Execution Path

> Every request targeting the `/api/books/**` endpoints follows this high-level execution path:

```
🔽 Incoming HTTP Request (Postman, browser, client)
  ➞ 🔐 Spring Security Filter Chain
    ➞ JwtAuthenticationFilter: Decodes & validates token
    ➞ SecurityContext: Sets up authenticated Principal
  ➞ 🎯 Controller Layer (e.g., BookRestController)
    ➞ Validates role-based access via @PreAuthorize
    ➞ Delegates to Service Layer
  ➞ ⚙️ Service Layer (BookServiceImpl)
    ➞ Performs business logic (no ownership enforcement)
    ➞ Uses Mapper to convert DTO ↔ Entity
  ➞ 📂 Repository Layer (BookRepository)
    ➞ Performs DB operations via Spring Data JPA
  ➞ ↩️ Response marshaled back as JSON
```

---

## 🧪 Tested Endpoints

### 📗 `GET /api/books`

* ✅ Accessible by: `CUSTOMER`, `EMPLOYEE`, `ADMIN`
* 🧠 Behavior:

  * Returns list of all books.
* 🔄 Request Flow:

  * `BookRestController#findAll()` → `BookServiceImpl#findAll()` → `.findAll()` on repository

---

### 📘 `GET /api/books/{id}`

* ✅ Accessible by: `CUSTOMER`, `EMPLOYEE`, `ADMIN`
* ❌ Returns 404 if book ID not found
* 🧠 Behavior:

  * Returns a single book by ID.
* 🔄 Request Flow:

  * `BookRestController#findById()` → `BookServiceImpl#findById()` → `.findById()`

---

### 📙 `POST /api/books`

* ✅ Accessible by: `EMPLOYEE`, `ADMIN`
* ❌ Forbidden for: `CUSTOMER`
* 🧠 Behavior:

  * Persists new book if valid.
  * Accepts full JSON body with author\_id, title, etc.
* 🔄 Request Flow:

  * `BookRestController#createBook()` → `BookServiceImpl#save()` → `.save()`

---

### 📕 `PUT /api/books/{id}`

* ✅ Accessible by: `EMPLOYEE`, `ADMIN`
* ❌ Forbidden for: `CUSTOMER`
* 🧠 Behavior:

  * Updates fields of book with given ID.
  * Throws 404 if ID not found.
* 🔄 Request Flow:

  * `BookRestController#updateBook()` → `BookServiceImpl#update()` → `.save()`

---

### 🗑️ `DELETE /api/books/{id}`

* ✅ Accessible by: `ADMIN`
* ❌ Forbidden for: `CUSTOMER`, `EMPLOYEE`
* 🧠 Behavior:

  * Deletes a book by ID.
  * Throws 404 if not found.
* 🔄 Request Flow:

  * `BookRestController#deleteBook()` → `BookServiceImpl#deleteById()`

---

### 📦 `POST /api/books/batch`

* ✅ Accessible by: `ADMIN`
* 🧠 Behavior:

  * Accepts list of books to save.
* 🔄 Request Flow:

  * `BookRestController#saveAllBooks()` → loops `BookServiceImpl#save()`

---

### 🗃️ `DELETE /api/books/batch`

* ✅ Accessible by: `ADMIN`
* 🧠 Behavior:

  * Accepts list of book IDs to delete.
  * Fails if any ID is not found.
* 🔄 Request Flow:

  * `BookRestController#deleteAllBooks()` → `BookServiceImpl#deleteAllById()`

---

## ⚠️ Known Gaps / Observations

* ❌ **No DTO validation implemented yet**

  * Fields like `title`, `genre`, `language` accept blanks.

* ❌ **No constraints enforced** on JSON structure or types.

* ⚡️ **Ownership checks not applicable** since books aren't user-bound.

---

## ✅ Conclusion

The `/api/books` endpoints operate reliably across all roles based on configured access rules.

Validation of request data and constraint handling will be addressed in the next development cycle.

Spring Security and service logic correctly restrict operations based on user roles.
