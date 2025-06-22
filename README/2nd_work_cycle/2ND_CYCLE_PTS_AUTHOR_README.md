# 📘 Postman Testing Scenarios for `Author` Endpoints

This README outlines the testing lifecycle of the `/api/authors` endpoints in Postman for all supported roles (`CUSTOMER`, `EMPLOYEE`, `ADMIN`) under JWT-based authentication.

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

Each `/api/authors/**` request follows this flow:

```
🔻 Incoming HTTP Request (Postman, browser, client)
  ➞ 🔐 Spring Security Filter Chain
    ➞ JwtAuthenticationFilter: Decodes & validates token
    ➞ SecurityContext: Sets up authenticated Principal
  ➞ 🎯 Controller Layer (AuthorRestController)
    ➞ Role-validated via @PreAuthorize
    ➞ Delegates to Service Layer
  ➞ ⚙️ Service Layer (AuthorServiceImpl)
    ➞ Executes business logic and role-based field control (email hiding)
    ➞ Uses AuthorMapper for DTO ⇔ Entity
  ➞ 📂 Repository Layer (AuthorRepository)
    ➞ DB operations via Spring Data JPA
  ➞ ↩️ Response serialized to JSON
```

---

## 🪙 Tested Endpoints

### 1. `GET /api/authors`

**Access:** ✅ `CUSTOMER`, `EMPLOYEE`, `ADMIN`

**Behavior:**

* `CUSTOMER`: email field is hidden (nullified).
* `EMPLOYEE`, `ADMIN`: full author data returned.

**Flow:**
`AuthorRestController#findAll()` → `AuthorServiceImpl#findAll()` → map + conditional email hiding

---

### 2. `GET /api/authors/{id}`

**Access:** ✅ `CUSTOMER`, `EMPLOYEE`, `ADMIN`

**Behavior:**

* `CUSTOMER`: sees all fields except email.
* `EMPLOYEE`, `ADMIN`: see all fields.

**Flow:**
`AuthorRestController#findById()` → `AuthorServiceImpl#findById()` → conditional email nullification

---

### 3. `POST /api/authors`

**Access:** ✅ `ADMIN`, `EMPLOYEE`
**Forbidden:** ❌ `CUSTOMER`

**Behavior:**

* Accepts author data and nested `authorDetails`.

**Flow:**
`AuthorRestController#createAuthor()` → `AuthorServiceImpl#save()` → `.save()` via repository

---

### 4. `PUT /api/authors/{id}`

**Access:** ✅ `ADMIN`, `EMPLOYEE`
**Forbidden:** ❌ `CUSTOMER`

**Behavior:**

* Updates author fields and details.

**Flow:**
`AuthorRestController#updateAuthor()` → `AuthorServiceImpl#update()`

---

### 5. `DELETE /api/authors/{id}`

**Access:** ✅ `ADMIN`
**Forbidden:** ❌ `EMPLOYEE`, `CUSTOMER`

**Behavior:**

* Deletes an author if found.

**Flow:**
`AuthorRestController#deleteAuthor()` → `AuthorServiceImpl#deleteById()`

---

### 6. `POST /api/authors/batch`

**Access:** ✅ `ADMIN` only

**Behavior:**

* Accepts list of authors with or without details.

**Flow:**
`AuthorRestController#saveAllAuthors()` → looped `AuthorServiceImpl#save()`

---

### 7. `DELETE /api/authors/batch`

**Access:** ✅ `ADMIN` only

**Behavior:**

* Deletes all provided IDs if they exist.

**Flow:**
`AuthorRestController#deleteAllAuthors()` → `AuthorServiceImpl#deleteAllById()`

---

## ⚠️ Known Gaps / Observations

* ❌ **No DTO validation yet** – blank names/emails pass.
* ⛔ **CUSTOMER** cannot view emails (enforced in service).
* 📅 **Timestamps are auto-managed**.

---

## ✅ Conclusion

The `/api/authors` endpoints are correctly secured and operational.
Role-based visibility of sensitive fields (like `email`) is enforced in the service layer.

Next steps include validation annotations and error handling refinement.
