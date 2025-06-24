# 📃 Mappers Documentation - ebookshop

This section documents all MapStruct mappers used in the `ebookshop` project, responsible for 
converting between Entity classes and their respective Data Transfer Objects (DTOs). These mappers 
encapsulate transformation logic and promote a clean separation between the persistence layer and the 
service/API layer.

---

## 🖋️ AuthorMapper

Handles all mapping logic related to authors and their nested details.

### Supported Conversions:

* `AuthorRequestDTO` → `AuthorEntity`
* `AuthorEntity` → `AuthorResponseDTO`
* `AuthorDetailsDTO` ↔ `AuthorDetailsEntity`

### Notes:

* Nested mapping of `AuthorDetails` is handled automatically.
* Intended for full lifecycle use (CRUD).

---

## 🖋️ BookMapper

Responsible for mapping `BookEntity` objects and their nested `BookDetailsEntity` components.

### Supported Conversions:

* `BookRequestDTO` → `BookEntity` (author is stubbed from `authorId`)
* `BookEntity` → `BookResponseDTO` (author name is concatenated)
* `BookDetailsDTO` ↔ `BookDetailsEntity`

### Notes:

* Custom method `mapAuthor(Integer id)` is used to generate stub `AuthorEntity`.
* Update logic skips nulls via `@BeanMapping`.

---

## 🖋️ CustomerMapper

Handles transformation logic for customers and their nested `CustomerDetails`.

### Supported Conversions:

* `CustomerRequestDTO` → `CustomerEntity`
* `CustomerEntity` → `CustomerResponseDTO`
* `CustomerDetailsDTO` ↔ `CustomerDetailsEntity`

### Notes:

* Nested structure is mapped field-wise.
* Primarily used in authentication and profile logic.

---

## 🖋️ BookReviewsMapper

Converts review data between DTOs and entities. Handles ownership via manual service injection.

### Supported Conversions:

* `BookReviewsRequestDTO` → `BookReviewsEntity`
* `BookReviewsEntity` → `BookReviewsResponseDTO`

### Special Behaviors:

* Fields `reviewId`, `createdAt`, `updatedAt`, and `customer` are excluded from auto-mapping.
* `bookId` is resolved via stub `BookEntity` using `mapBook()` method.

### Notes:

* Service must inject the correct `CustomerEntity` manually based on authentication context.
* Response includes `bookId` and `customerId` explicitly.

---

## 🖋️ AuthorBookMapper

Maps the many-to-many relationship entity between authors and books.

### Supported Conversions:

* `AuthorBookEntity` → `AuthorBookResponseDTO`

### Notes:

* Extracts `authorId` and `bookId` from nested entities.
* Only used for read (GET) operations.

---

## ✅ Usage Guidelines

* All mappers are annotated with `@Mapper(componentModel = "spring")` and are automatically registered as Spring Beans.
* Avoid business logic inside mappers — keep them declarative.
* Prefer manual injection of security/user-related data.
* Use `@Named` methods when partial entity stubbing is needed (e.g., `mapAuthor`, `mapBook`).

---

## 🔎 Future Enhancements

* Consider introducing mapping context or cycle-avoiding logic if relationships grow more complex.
* Explore `@Decorated` mappers for injecting post-processing logic.

---

*This documentation ensures a clear understanding of the DTO-to-Entity transformation layer that 
underpins all service operations in the ebookshop project.*
