# 📦 DTOs Documentation – *Ebookshop Project*

This module includes all **Data Transfer Objects (DTOs)** used for data exchange between the client and the server application. The system is designed with a clear separation of concerns based on DTO type.

---

## 📂 DTO Structure

DTOs are organized into three categories:
```
  com.mavrommatis.ebookshop.ebookshop.dao
  ├── details
  │   ├── AuthorDetailsDTO.java
  │   ├── BookDetailsDTO.java
  │   └─ CustomerDetailsDTO.java
  └── request
  │   ├── AuthorBookRequestDTO.java
  │   ├── AuthorRequestDTO.java
  │   ├── BookRequestDTO.java
  │   ├── BookReviewRequestDTO.java
  │   └── CustomerRequestDTO.java
  └── response
      ├── AuthorBookResponseDTO.java
      ├── AuthorResponseDTO.java
      ├── BookResponseDTO.java
      ├── BookReviewResponseDTO.java
      └── CustomerResponseDTO.java
```

---

## 🔧 Purpose and Usage

* **Request DTOs**: Handle input data from the client, containing validation constraints.
* **Response DTOs**: Provide only necessary fields to the client without validation.
* **Details DTOs**: Represent optional 1:1 metadata (e.g., profiles) embedded in other DTOs.

---

## 📌 Contents

### ✍️ Request DTOs

* `AuthorRequestDTO` – First name, email, and optional bio.
* `BookRequestDTO` – Title, language, genre, ISBN, and `authorId`.
* `CustomerRequestDTO` – Username, password, email, and profile.
* `BookReviewsRequestDTO` – `bookId`, `rating`, `comment`.
* `AuthorBookRequestDTO` – `authorId` + `bookId` for many-to-many relations.

👉 All fields are strictly validated with `@NotBlank`, `@Size`, `@Email`, and custom annotations 
(`@ValidURL`, `@ValidISBN`, `@UniqueEmail`, etc).

---

### 📤 Response DTOs

* `AuthorResponseDTO` – Client-visible author data, including timestamps and optional `authorDetails`.
* `BookResponseDTO` – Title, genre, author name (as full name), and `BookDetailsDTO`.
* `CustomerResponseDTO` – Username, email, and `CustomerDetailsDTO`.
* `BookReviewsResponseDTO` – Includes role-based filtering: `bookId` and `customerId` are hidden from `CUSTOMER` role.
* `AuthorBookResponseDTO` – Represents join between Author and Book, with timestamps.

---

### 🧹 Details DTOs

* `AuthorDetailsDTO`: biography, birthDate, website.
* `BookDetailsDTO`: pages, summary, dimensions, weight.
* `CustomerDetailsDTO`: firstName, lastName, address, phone.

📌 These are embedded using `@Valid` in their parent request DTOs.

---

## 🛡️ Validation Strategy

* **Request-only** DTOs include full validation annotations.
* **Response DTOs** do not use validation constraints.
* Custom annotations are implemented for specific validation needs:

    * `@ValidISBN` for correct ISBNs
    * `@ValidPhoneNumber` for phone formats
    * `@UniqueEmail` for uniqueness checks

---

## 🔐 Role-based Output Control

Some response DTOs (e.g., `BookReviewsResponseDTO`) dynamically hide fields depending on the 
user role using `SecurityContextHolder`.

---

## 💡 Best Practices

* DTOs **do not contain logic** — only data.
* They are used solely for **external communication**.
* Relations are handled via `id` references, not nested entities (e.g., `authorId` instead of `AuthorEntity`).


