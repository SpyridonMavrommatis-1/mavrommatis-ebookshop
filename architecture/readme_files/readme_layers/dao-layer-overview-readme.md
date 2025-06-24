## 📦 Repository Layer Overview (DAO)

This layer provides the data access abstraction in the project using **Spring Data JPA**. Each interface extends `JpaRepository`, allowing us to perform CRUD operations, pagination, sorting, and query derivation without boilerplate code.

---

### 🧱 Package Structure

```
com.mavrommatis.ebookshop.ebookshop.dao
├── basic
│   ├── AuthorBookRepository.java
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   ├── BookReviewsRepository.java
│   └── CustomerRepository.java
└── details
    ├── AuthorDetailsRepository.java
    ├── BookDetailsRepository.java
    └── CustomerDetailsRepository.java
```

---

### 📘 Basic Repositories

#### `AuthorRepository`

* Manages `AuthorEntity`.
* Custom method: `existsByEmailIgnoreCase(String email)` to validate unique author emails.

#### `BookRepository`

* Handles standard operations on `BookEntity`.

#### `CustomerRepository`

* Manages `CustomerEntity`.
* Custom method: `findByUsername(String username)` used for authentication/authorization.

#### `AuthorBookRepository`

* Handles the join table `AuthorBookEntity`.
* Uses composite key: `AuthorBookIdEntity`.

#### `BookReviewsRepository`

* Manages `BookReviewsEntity`.
* Custom method: `existsByBook_bookIdAndCustomer_customerId(Integer bookId, Integer customerId)` to enforce review uniqueness per customer/book pair.

---

### 📗 Details Repositories

These manage extended profile data, each tied one-to-one with their respective base entity.

#### `AuthorDetailsRepository`

* Manages `AuthorDetailsEntity` (biography, birth date, etc.).

#### `BookDetailsRepository`

* Manages `BookDetailsEntity` (summary, dimensions, etc.).

#### `CustomerDetailsRepository`

* Manages `CustomerDetailsEntity` (address, phone, etc.).

---

### ⚙️ Technical Notes

* All repositories use `Integer` as the ID type, except for `AuthorBookRepository`, which uses a composite key.
* Spring automatically implements these interfaces at runtime — no need for boilerplate SQL or DAO implementations.
* You can easily define custom queries using Spring Data method naming conventions.

---

### 🧠 Suggested Reading

* [Spring Data JPA Repositories](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories)
* [Query Creation from Method Names](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details)
* [Handling Composite Keys in Spring Data](https://www.baeldung.com/jpa-composite-primary-keys)
