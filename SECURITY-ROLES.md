# 🔐 Role-based Access Matrix

This matrix outlines the permissions granted to each role (`CUSTOMER`, `EMPLOYEE`, `ADMIN`) in the **ebookshop** application. Each role has different levels of access based on the entity and the HTTP operation.

---

## 👥 Roles

* **CUSTOMER**: End-users of the system; can interact with their own data (reviews, profile) and publicly available data (books, authors).
* **EMPLOYEE**: Staff responsible for managing content (books, authors, reviews), with limited access to customer data. Cannot perform destructive operations like deletions.
* **ADMIN**: Full access to all system resources and operations, including sensitive customer data.

---

## 🔍 Access Matrix

| Entity / Action                    | CUSTOMER | EMPLOYEE | ADMIN |
| ---------------------------------- | -------- | -------- | ----- |
| 📖 **Book** (GET)                  | ✅        | ✅        | ✅     |
| 📖 **Book** (POST/PUT)             | ❌        | ✅        | ✅     |
| 📖 **Book** (DELETE)               | ❌        | ❌        | ✅     |
| 👤 **Customer** (GET others)       | ❌        | ❌        | ✅     |
| 👤 **Customer** (GET self only)    | ✅        | ✅        | ✅     |
| 👤 **Customer** (DELETE)           | ❌        | ❌        | ✅     |
| 📝 **BookReview** (GET all)        | ✅        | ✅        | ✅     |
| 📝 **BookReview** (POST/PUT own)   | ✅        | ✅        | ✅     |
| 📝 **BookReview** (DELETE)         | ✅\*      | ❌        | ✅     |
| 👨‍🏫 **Author** (GET)             | ✅        | ✅        | ✅     |
| 👨‍🏫 **Author** (POST/PUT)        | ❌        | ✅        | ✅     |
| 👨‍🏫 **Author** (DELETE)          | ❌        | ❌        | ✅     |
| 📘 **BookDetails** (GET)           | ✅        | ✅        | ✅     |
| 📘 **BookDetails** (POST/PUT)      | ❌        | ✅        | ✅     |
| 👨‍🏫 **AuthorDetails** (GET)      | ✅        | ✅        | ✅     |
| 👨‍🏫 **AuthorDetails** (POST/PUT) | ❌        | ✅        | ✅     |

---

### ✨ Legend

* ✅ = Access Allowed
* ❌ = Access Denied
* ✅\* = Can delete own review only

---

> ⚠️ **Note:** All access rules are enforced via Spring Security’s `SecurityFilterChain` and `@PreAuthorize` annotations.
> This table must always reflect the latest authorization logic defined at controller and configuration level.
