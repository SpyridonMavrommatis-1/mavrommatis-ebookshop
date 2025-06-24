# 📦 `exception/` — Domain-Specific & Global Exception Handling

This package contains all custom exceptions used throughout the eBookShop backend.  
It is structured by domain (e.g., `author`, `book`, `customer`, `review`) and designed to support:

- Clear error semantics
- Role-based access control
- Ownership enforcement
- Enriched error responses via a global exception handler

---

## 📂 Structure Overview

```
exception/
├── author/
│   ├── AuthorNotFoundException.java
│   └── AuthorDetailsNotFoundException.java
├── book/
│   ├── BookNotFoundException.java
│   ├── BookDetailsNotFoundException.java
│   └── AuthorNotFoundException.java
├── customer/
│   ├── CustomerNotFoundException.java
│   ├── CustomerDetailsNotFoundException.java
│   └── CustomerAccessDeniedException.java
├── review/
│   ├── ReviewNotFoundException.java
│   ├── DuplicateReviewException.java
│   └── UnauthorizedReviewActionException.java
└── system/
    └── GlobalExceptionHandler.java 
```

---

## 🚨 Exception Philosophy

This project uses **custom exceptions** instead of generic ones (e.g., `RuntimeException`, 
`IllegalArgumentException`) to improve:

- **Error traceability**: Each exception maps to a specific domain issue
- **Granular handling**: The `GlobalExceptionHandler` can produce contextual HTTP responses
- **Readable service code**: Throwing `new CustomerNotFoundException(id)` is self-explanatory

---

## ✍️ Domain Exceptions

### 📚 `author/`
- `AuthorNotFoundException`: Thrown when an author entity is not found by ID.
- `AuthorDetailsNotFoundException`: Used for detailed metadata queries.

### 📘 `book/`
- `BookNotFoundException`: Thrown for missing books in CRUD operations.
- `BookDetailsNotFoundException`: Used in enriched, read-only book views.
- `AuthorNotFoundException`: Raised during book creation if the referenced author ID is invalid.

### 👤 `customer/`
- `CustomerNotFoundException`: Raised by both ID and username — used in registration, updates, and ownership checks.
- `CustomerDetailsNotFoundException`: Handles missing address/contact info.
- `CustomerAccessDeniedException`: Thrown when a user tries to access or modify data they don't own.

### ✒️ `review/`
- `ReviewNotFoundException`: Thrown when a review ID cannot be located.
- `DuplicateReviewException`: Enforces 1-review-per-customer-per-book policy.
- `UnauthorizedReviewActionException`: Extends Spring’s `AccessDeniedException` for ownership enforcement.

---

## 🧠 Usage & Integration

### ➤ Throwing Exceptions in Services

```java
public Author getAuthorById(Integer id) {
    return authorRepository.findById(id)
        .orElseThrow(() -> new AuthorNotFoundException(id));
}
```

### ➤ Handling with `GlobalExceptionHandler`

Each exception can be handled globally:

```java
@ExceptionHandler(BookNotFoundException.class)
public ResponseEntity<ApiError> handleBookNotFound(BookNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                         .body(new ApiError(404, ex.getMessage()));
}
```

---

## 🎯 Design Benefits

- ✅ Promotes single-responsibility in services
- ✅ Enables global, uniform JSON responses
- ✅ Aligns with REST semantics (`404`, `403`, `409`, etc.)
- ✅ Easily testable and extendable

---

## 🧩 Related Concepts

- **Spring’s `@RestControllerAdvice`**  
  = `@ControllerAdvice` + `@ResponseBody`
  Used to implement centralized exception handling by giving advises to all controllers on the subject of 
  handling error plus automatically respond to the client with JSON views.

- **Custom error payloads**  
  Can be returned using DTOs like `ApiError`, `ValidationError`, etc.

- **Security-aware exceptions**  
  Integrate `AccessDeniedException` types with Spring Security filters.

---

> 🗣️ *"Don't just fail loud. Fail clearly."*  
> Custom exceptions give services a voice.
