# 📦 Entity Layer Overview

This directory contains the core **JPA entity classes** used to model the domain of the ebookshop system.
Entities are grouped conceptually into the following domains:

---

### 🧱 Package Structure

```
com.mavrommatis.ebookshop.ebookshop.dao
├── basic
│   ├── AuthorBookEntity.java
│   ├── AuthorEntity.java
│   ├── BookEntity.java
│   ├── BookReviewsEntity.java
│   └── CustomerEntity.java
└── details
│   ├── AuthorDetailsEntity.java
│   ├── BookDetailsEntity.java
│   └── CustomerDetailsEntity.java
└── helper
    └── AuthorBookIdEntity.java
    
```

---

## 📚 Basic Entities

These represent the core business objects in the system.

| Entity Class         | Description                                                                 |
|----------------------|-----------------------------------------------------------------------------|
| `AuthorEntity`       | Represents an author with 1–N books and optional detailed metadata.         |
| `BookEntity`         | Represents a book, linked to a primary author and optional details.         |
| `CustomerEntity`     | Represents a registered user with credentials and optional profile info.    |
| `BookReviewsEntity`  | Represents a customer review for a book, including rating and comment.      |
| `AuthorBookEntity`   | Join entity for many-to-many relationship between books and authors.        |

---

## 🔍 Details Entities

These extend the basic entities with optional additional data.

| Entity Class             | Description                                                             |
|--------------------------|-------------------------------------------------------------------------|
| `AuthorDetailsEntity`    | Stores biography, birthdate, and website for an author.                 |
| `BookDetailsEntity`      | Contains extra info like dimensions, cover type, weight, and summary.   |
| `CustomerDetailsEntity`  | Stores address, phone, and full name of a customer.                     |

---

## 🔑 Helper / Embedded IDs

| Class Name             | Description                                                                |
|------------------------|----------------------------------------------------------------------------|
| `AuthorBookIdEntity`   | Composite primary key for `AuthorBookEntity`, combining author + book IDs. |

> 💡 All entities use `@PrePersist` and `@PreUpdate` lifecycle hooks to manage `createdAt` and `updatedAt` timestamps automatically.

---

## 🧩 Relationships Summary

- `Author` ↔ `Book`: One-to-Many
- `Book` ↔ `AuthorBook` ↔ `Author`: Many-to-Many via join entity
- `Customer` ↔ `BookReviews`: One-to-Many
- `Book` ↔ `BookDetails`: One-to-One
- `Author` ↔ `AuthorDetails`: One-to-One
- `Customer` ↔ `CustomerDetails`: One-to-One

---

## 📌 Notes

- JSON serialization handled using `@JsonManagedReference` / `@JsonBackReference` to prevent recursion.
- Entities follow **Lombok** pattern for boilerplate reduction (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@ToString`).
- Primary keys are typically `int` or `Integer`, using `GenerationType.IDENTITY` where applicable.
- Composite keys (`AuthorBookIdEntity`) implement `Serializable` and override `equals/hashCode`.

---

## 🧠 Suggested Reading Before Diving Deeper

- [JPA Relationships Overview](https://www.baeldung.com/spring-data-rest-relationships)
- [Serializable and serialVersionUID Explained](https://www.baeldung.com/java-serial-version-uid)
- [Understanding @JsonManagedReference and @JsonBackReference](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)
