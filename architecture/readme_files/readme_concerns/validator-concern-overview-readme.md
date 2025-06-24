# ✅ `validator/` — Custom Validation Annotations & Logic

This package defines and implements **custom validation constraints** for domain-specific use cases within the eBookShop backend.  
It is split into two subpackages:

- `annotation/`: Contains custom annotations like `@ValidISBN`, `@UniqueEmail`, etc.
- `validation/`: Contains the corresponding `ConstraintValidator` implementations with the actual logic.

These validators extend the capabilities of Bean Validation (`jakarta.validation`) and enforce business rules at the DTO level.

---

## 📦 Package Structure

```
validator/
├── annotation/
│   ├── ValidISBN.java
│   ├── UniqueEmail.java
│   ├── ValidPhoneNumber.java
│   ├── ValidReviewContent.java
│   └── ValidURL.java
└── validation/
    ├── ValidISBNValidator.java
    ├── UniqueEmailValidator.java
    ├── ValidPhoneNumberValidator.java
    ├── ValidReviewContentValidator.java
    └── ValidURLValidator.java
```

---

## 🔍 Validators Overview

### 📘 `@ValidISBN`
- Validates whether a string is a valid ISBN-10 or ISBN-13
- Uses Modulo 11 (ISBN-10) and Modulo 10 (ISBN-13) checksum algorithms
- Ignores hyphens/whitespace
- ✅ Example:
  ```java
  @ValidISBN
  private String isbn;
  ```

### 📧 `@UniqueEmail`
- Ensures the given email does not already exist in the database (case-insensitive)
- Checks via `AuthorRepository`
- Meant to work with `@NotBlank` and `@Email`
- ✅ Example:
  ```java
  @NotBlank @Email @UniqueEmail
  private String email;
  ```

### 📞 `@ValidPhoneNumber`
- Accepts digit-only phone numbers with optional international prefix
- Accepts: `"2101234567"`, `"+302101234567"`, `"00302101234567"`
- Regex: `^(\\+|00)?\\d{8,20}$`

### ✍️ `@ValidReviewContent`
- Class-level validator for `BookReviewsRequestDTO`
- Requires comment if rating is below 3
- Ensures constructive feedback on low reviews
- ✅ Example usage:
  ```java
  @ValidReviewContent
  public class BookReviewsRequestDTO { ... }
  ```

### 🌐 `@ValidURL`
- Validates URLs using `java.net.URL`
- Must use `http` or `https`
- Must contain a domain (e.g. `"example.com"`)
- ✅ Example:
  ```java
  @ValidURL
  private String website;
  ```

---

## 🧠 Design Philosophy

- ✅ **Annotation-based**: declarative, simple, expressive
- ✅ **Separation of concerns**: annotation ≠ validation logic
- ✅ **Reusability**: shared validators for all relevant DTOs
- ✅ **Fail-safe null handling**: most validators return `true` for `null`/empty, expecting `@NotBlank` where needed

---

## 🛠️ Extension Ideas

- Add localized messages via `ValidationMessages.properties`
- Compose annotations using `@ReportAsSingleViolation`
- Support conditional validation groups

---

## 🧪 Sample Test Strategy

Each validator should have a dedicated unit test class.  
Example for `ValidISBNValidatorTest`:
```java
@Test
void shouldRejectInvalidISBN10Checksum() {
    assertFalse(validator.isValid(\"1234567890\", context));
}
```

---

> ✒️ *“Validation is the immune system of your application.”*  
> With these custom annotations, your DTOs stay clean, strict, and expressive.

