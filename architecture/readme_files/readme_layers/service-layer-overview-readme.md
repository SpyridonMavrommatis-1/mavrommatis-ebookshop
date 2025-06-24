# 📚 Service Layer - Documentation Overview

This document outlines the core responsibilities, structure, and behavior of the Service Layer in the `ebookshop` application. It highlights how the layer acts as an abstraction between the controller (API) and the persistence layer (repositories), utilizing DTOs and Mappers to decouple domain logic from data access.

---

## 🧩 Structure & Organization

The service layer is split into two packages:

* `com.mavrommatis.ebookshop.ebookshop.service.basic`
* `com.mavrommatis.ebookshop.ebookshop.service.details`

Each service interface defines a contract for CRUD operations (plus custom logic where needed) 
using request/response DTOs. Implementations handle business rules, ownership checking, 
exception throwing, and transactional persistence.

---

### 🧱 Package Structure

```
com.mavrommatis.ebookshop.ebookshop.dao
├── basic
│   ├── AuthorBookService.java
│   ├── AuthorBookServiceImpl.java
│   ├── AuthorService.java
│   ├── AuthorServiceImpl.java
│   ├── BookReviewsService.java
│   ├── BookReviewsServiceImpl.java
│   ├── BookService.java
│   ├── BookServiceImpl.java
│   ├── CustomerService.java
│   └── CustomerServiceImpl.java
└── details
    ├── AuthorDetailsService.java
    ├── AuthorDetailsServiceImpl.java
    ├── BookDetailsService.java
    ├── BookDetailsServiceImpl.java
    ├── CustomerDetailsServiceImpl.java
    └── CustomerDetailsServiceImpl.java
```
---

## ✅ Design Principles

* **SOLID Principles**

    * **Single Responsibility**: Each service manages a specific domain entity.
    * **Interface Segregation**: Services expose only what clients need.
    * **Dependency Inversion**: Repositories and mappers are injected via constructor.

* **DTO Usage**: All service methods accept request DTOs and return response DTOs to encapsulate only the necessary data and isolate internal structure.

* **Exception Handling**: Domain-specific exceptions (e.g. `BookNotFoundException`, `UnauthorizedReviewActionException`) are thrown to signal client-side errors in a clean, semantic manner.

* **Security Checks**:

    * Some services (e.g. `CustomerServiceImpl`, `BookReviewsServiceImpl`) incorporate access control logic to enforce ownership or role-based constraints.

---

## 🔁 Mapping & Transformation

All entity ↔ DTO transformations are handled via dedicated `Mapper` classes. These mappers isolate mapping logic and enable clean, readable service code. Example:

```java
BookEntity book = bookMapper.toEntity(dto);
BookResponseDTO response = bookMapper.toResponse(book);
```

---

## 🛠 Notable Implementations

### `CustomerServiceImpl`

* Enforces that only admins or the user themselves can view, update, or delete a profile.
* Injects `CustomerRepository` and `CustomerMapper`.
* Sets `username` based on authenticated principal.

### `AuthorServiceImpl`

* Redacts sensitive data (`email`) for `CUSTOMER` role.
* Handles nested `AuthorDetailsEntity` persistence.

### `BookReviewsServiceImpl`

* Validates duplicate reviews via `existsByBook_bookIdAndCustomer_customerId()`.
* Automatically binds review to authenticated user.
* Throws domain exceptions (`DuplicateReviewException`, `UnauthorizedReviewActionException`).

### `BookServiceImpl`

* Validates author existence before setting `AuthorEntity` on a book.
* Sets up bi-directional relationship with `BookDetailsEntity`.

### `Details Services`

* Use `.saveAll()` for batch persistence.
* Enforce ownership (e.g. `CustomerDetailsServiceImpl` ensures that only owners or admins can mutate/view records).

---

## 🔐 Security Integration

Services that interact with user-related data use:

* `SecurityContextHolder` to extract authenticated `username`.
* Authority checks (e.g. `hasRole("ROLE_ADMIN")`, `getCurrentUserId()`).

---

## ⚠ Exception Strategy

Custom exceptions are defined per domain module:

* `book`: `BookNotFoundException`, `AuthorNotFoundException`
* `customer`: `CustomerNotFoundException`, `CustomerAccessDeniedException`
* `review`: `ReviewNotFoundException`, `DuplicateReviewException`, `UnauthorizedReviewActionException`

These allow granular control and consistent error messaging in controllers.

---

## 🧠 Suggested Reading Before Diving Deeper

* [JPA Relationships Overview](https://www.baeldung.com/spring-data-rest-relationships)
* [Serializable and serialVersionUID Explained](https://www.baeldung.com/java-serial-version-uid)

---

This README reflects a clean, layered architecture following modern Spring Boot best practices. 
Services act as the business logic hub — decoupled, testable, secure, and extensible.
