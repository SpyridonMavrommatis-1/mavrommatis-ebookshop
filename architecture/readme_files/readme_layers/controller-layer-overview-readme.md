## 📦 `controller` Package — REST API Controllers Overview

This package contains all the REST controllers that expose endpoints to external API consumers. 
Controllers serve as the **entry point** of the application's flow, processing HTTP requests, 
delegating work to service classes, and returning properly structured responses.

They are categorized in **three subdomains**:

* `api.basic`: for entity CRUD endpoints (`Author`, `Book`, `Customer`, `BookReviews`, `AuthorBook`)
* `api.details`: for detail-view enriched resources (`AuthorDetails`, `BookDetails`, etc.)
* `system`: for cross-cutting concerns like authentication and error handling.

---

### 🚀 REST Controllers

#### 🔹 `AuthorRestController`

```
* **Path**: `/api/authors`


* **Role Access**:
    * `CUSTOMER`, `EMPLOYEE`, `ADMIN` → Read
    * `EMPLOYEE`, `ADMIN` → Create/Update
    * `ADMIN` → Delete


* **Responsibilities**:
    * Manage authors (create, fetch, update, delete)
    * Supports batch operations
```

#### 🔹 `AuthorDetailsRestController`

```
* **Path**: `/api/author-details`


* **Role Access**: Similar to `AuthorRestController`


* **Responsibilities**:
    * Manage extended author metadata
    * Uses a shared DTO (`AuthorDetailsDTO`) for both input and output
```


#### 🔹 `AuthorBookRestController`

```
* **Path**: `/api/author-books`


* **Role Access**:
    * `EMPLOYEE`, `ADMIN` → Read
    * (Planned) `EMPLOYEE`, `ADMIN` → Create
    * `ADMIN` → Delete* 


* **Responsibilities**:
    * Manage many-to-many relations between authors and books
    * Exposes composite key endpoints
    * Currently read-only, write/delete can be added
```


#### 🔹 `BookRestController`

```
* **Path**: `/api/books`


* **Role Access**:
    * `CUSTOMER`, `EMPLOYEE`, `ADMIN` → Read
    * `EMPLOYEE`, `ADMIN` → Create/Update
    * `ADMIN` → Delete


* **Responsibilities**:
    * Standard CRUD for books
    * Batch save/delete supported
```


#### 🔹 `BookDetailsRestController`

```
* **Path**: `/api/book-details`


* **Responsibilities**:
    * Extended view of books (authors, reviews)
    * Read-only access for now
```


#### 🔹 `BookReviewsRestController`

```
* **Path**: `/api/reviews`


* **Role Access**:
    * `CUSTOMER` → Create
    * `CUSTOMER` → Update/Delete only their own reviews
    * `EMPLOYEE`, `ADMIN` → Read all


* **Responsibilities**:
    * Manage book reviews
    * Enforces ownership rules and field visibility (e.g. customer/book ID hidden for `CUSTOMER`)
```

#### 🔹 `CustomerRestController`

```
* **Path**: `/api/customers`


* **Role Access**:
    * `CUSTOMER` → View/update self
    * `EMPLOYEE`, `ADMIN` → Manage all customers


* **Responsibilities**:
    * Customer registration and self-service
    * Ownership enforced using JWT subject identity
```

#### 🔹 `CustomerDetailsRestController`

```
* **Path**: `/api/customer-details`


* **Responsibilities**:
    * Detail view for customer info (e.g. address, phone)
    * Follows same access model as `CustomerRestController`
```

#### 🔹 `AuthRestController`

```
* **Path**: `/api/authenticate`


* **Role Access**: Public


* **Responsibilities**:
    * Authenticates username/password
    * Returns JWT token for secured access
    * Uses custom `JwtTokenProvider` for token generation
```

#### 🔹 `GlobalExceptionHandler`

```
* **Applies globally**


* **Responsibilities**:
    * Formats all error responses consistently
    * Handles custom exceptions (e.g. `ReviewNotFoundException`, `DuplicateReviewException`)
    * Handles `@Valid` violations
    * Produces uniform JSON payloads for 400/403/404/409/500 statuses
```
---

### 🧠 MVC Clarification

**MVC in a RESTful context**:

* **Model** → `Entity` + `DTO`
* **View** → JSON (via Spring Boot’s automatic Jackson serialization)
* **Controller** → All REST controllers

Using DTOs strengthens separation of concerns and protects the internal structure of the application.

