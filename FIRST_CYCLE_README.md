# eBookshop Spring Boot Project

## Cycle 1: Project Bootstrapping & Setup

This repository contains the initial setup and foundational configuration for the e-Bookshop Spring Boot project.

---

### **What was accomplished in Cycle 1**

#### 1. **Database Design & Preparation**
- Designed a MySQL schema for an e-Bookshop, with clearly defined entities (Book, Author, Customer, Orders, etc.).
- Created SQL scripts for data creation and schema testing.
- Included EER diagrams (in both .mwb and .pdf format) to visually represent the relational structure.

#### 2. **Spring Boot Project Initialization**
- Generated a Maven-based Spring Boot project using Java 17 and Spring Boot 3.x.
- Added all core dependencies:
    - Spring Web, Spring Data JPA, MySQL Driver, Thymeleaf, Security, Validation, Lombok, DevTools, Spring AOP.

#### 3. **Configuration & Best Practices**
- Created a robust `.gitignore` tailored for Java/Maven projects.
- Wrote a detailed `application.properties` with proper database, logging, Thymeleaf, and devtool settings.
- Set up MIT License for open source usage.

#### 4. **GitHub & Version Control Workflow**
- Initialized local git repository and made the first structured commit.
- Connected local repo with a remote GitHub repository.
- Resolved merge conflicts (notably in `.gitignore`) to maintain clean version history.
- Renamed the main branch from `master` to `main` (modern convention).

#### 5. **Branching & Collaboration Strategy**
- Created a dedicated development branch (`dev/base-structure`) for ongoing work, leaving `main` always 
production-ready.
- Ensured both `main` and `dev/base-structure` are in sync (locally and on GitHub).
- Established the foundation for a professional branching workflow (feature/dev → main).

---

### **Next Steps**

- Implement entity, repository, and service layers.
- Continue development on dev/feature branches, merging into `main` when stable.
- Document all significant progress for transparency and maintainability.

---

**This README summarizes the initial bootstrapping and organizational steps that ensure the project 
is well-structured and ready for efficient, scalable development.**

