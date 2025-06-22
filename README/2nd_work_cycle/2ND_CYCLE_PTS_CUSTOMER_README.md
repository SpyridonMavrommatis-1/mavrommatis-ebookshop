# 📘 Postman Testing Scenarios for `Customer` Endpoints

This README summarizes the full testing lifecycle of the `/api/customers` endpoints in Postman for all supported roles (`CUSTOMER`, `EMPLOYEE`, `ADMIN`) with working JWT-based authentication.

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

Every request targeting the `/api/customers/**` endpoints follows this high-level execution path:

```
🔽 Incoming HTTP Request (Postman, browser, client)
  ⇨ 🔐 Spring Security Filter Chain
    ⇨ JwtAuthenticationFilter: Decodes & validates token
    ⇨ SecurityContext: Sets up authenticated Principal
  ⇨ 🎯 Controller Layer (e.g., CustomerRestController)
    ⇨ Validates role-based access via @PreAuthorize
    ⇨ Delegates to Service Layer
  ⇨ ⚙️ Service Layer (CustomerServiceImpl)
    ⇨ Performs business logic + access ownership check
    ⇨ Uses Mapper to convert DTO ↔ Entity
  ⇨ 💾 Repository Layer (CustomerRepository)
    ⇨ Performs DB operations using Spring Data JPA
  ⇨ ↩️ Response marshaled back as JSON
```

---

## 🧪 Tested Endpoints

### 1. `POST /api/customers`

**Access:** ✅ `CUSTOMER`, `ADMIN`

**Behavior:**

* `CUSTOMER`: Username is overridden by token’s `preferred_username`.
* `ADMIN`: Can provide any valid username.

**Flow:**
`CustomerRestController#createCustomer()` → `CustomerServiceImpl#save()` → `CustomerMapper#toEntity()` → JPA `.save()`

---

### 2. `GET /api/customers`

**Access:** ✅ `CUSTOMER`, `EMPLOYEE`, `ADMIN`

**Behavior:**

* `CUSTOMER`: Only sees their own profile (filtered by username).
* `EMPLOYEE`, `ADMIN`: See all customers.

**Flow:**
`CustomerRestController#findAll()` → `CustomerServiceImpl#findAll()` → filter by role → map via `CustomerMapper`

---

### 3. `GET /api/customers/{id}`

**Access:** ✅ `CUSTOMER`, `EMPLOYEE`, `ADMIN`

**Forbidden if:**

* A `CUSTOMER` tries to access another user's data (403).

**Flow:**
`CustomerRestController#findById()` → `CustomerServiceImpl#findById()` → SecurityContext username check

---

### 4. `PUT /api/customers/{id}`

**Access:** ✅ `CUSTOMER`, `ADMIN`

**Behavior:**

* `CUSTOMER`: Can only update their own profile; cannot change username.
* `ADMIN`: Can update any profile and change usernames.

**Flow:**
`CustomerRestController#updateCustomer()` → `CustomerServiceImpl#update()` → map nested details

---

### 5. `DELETE /api/customers/{id}`

**Access:** ✅ `CUSTOMER`, `ADMIN`

**Behavior:**

* `CUSTOMER`: Can only delete their own profile.
* `ADMIN`: Can delete any profile.

**Flow:**
`CustomerRestController#deleteCustomer()` → `CustomerServiceImpl#deleteById()`

---

### 6. `POST /api/customers/batch`

**Access:** ✅ `ADMIN` only

**Behavior:**

* Accepts a list of `CustomerRequestDTO`s.
* No current validation for blank/invalid values.

**Flow:**
`CustomerRestController#saveAllCustomers()` → looped `CustomerServiceImpl#save()`

---

### 7. `DELETE /api/customers/batch`

**Access:** ✅ `ADMIN` only

**Behavior:**

* Accepts list of IDs (e.g. `[1, 2, 3]`).
* Fails if any ID does not exist.

**Flow:**
`CustomerRestController#deleteAllCustomers()` → `CustomerServiceImpl#deleteAllById()`

---

## ⚠️ Known Gaps / Observations

* ❌ No DTO validation: accepts blank or invalid values.
* 🔁 `CUSTOMER` users cannot spoof identity — token takes precedence.
* 🧾 `ADMIN` users can explicitly set usernames.

---

## ✅ Conclusion

All key functionality has been verified through Postman.
Authorization is enforced at both the controller (`@PreAuthorize`) and service (identity checks) layers.

Next steps: implement robust input validation.
