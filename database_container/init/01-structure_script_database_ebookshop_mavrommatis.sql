-- Ensure a fresh setup
DROP SCHEMA IF EXISTS `ebookshop_mavrommatis`;
CREATE SCHEMA `ebookshop_mavrommatis`;
USE `ebookshop_mavrommatis`;

-- Disable foreign key checks temporarily
SET FOREIGN_KEY_CHECKS = 0;

-- ============================
--       AUTHOR TABLES
-- ============================
CREATE TABLE author (
  author_id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(100) NOT NULL COMMENT 'Author’s first name',
  last_name VARCHAR(100) NOT NULL COMMENT 'Author’s last name',
  email VARCHAR(255) NOT NULL COMMENT 'Author email address, must be unique',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (author_id),
  CONSTRAINT uq_author_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE author_details (
  author_id INT NOT NULL,
  biography TEXT,
  birth_date DATE,
  website VARCHAR(255),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (author_id),
  CONSTRAINT fk_author_details_author FOREIGN KEY (author_id) REFERENCES author(author_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================
--       BOOK TABLES
-- ============================
CREATE TABLE book (
  book_id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  language VARCHAR(100) NOT NULL,
  genre VARCHAR(100) NOT NULL,
  literary_form VARCHAR(100) NOT NULL,
  isbn VARCHAR(20) NOT NULL,
  is_collective BOOLEAN NOT NULL DEFAULT FALSE,
  author_id INT NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (book_id),
  CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author(author_id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT uq_book_unique_entry UNIQUE (isbn, author_id, is_collective)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE book_details (
  book_id INT NOT NULL,
  publish_date DATE,
  pages INT,
  summary TEXT,
  dimensions VARCHAR(50),
  cover_type VARCHAR(50),
  weight DECIMAL(6,2),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (book_id),
  CONSTRAINT fk_book_details_book FOREIGN KEY (book_id) REFERENCES book(book_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE author_book (
  author_id INT NOT NULL,
  book_id INT NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (author_id, book_id),
  CONSTRAINT fk_authorbook_author FOREIGN KEY (author_id) REFERENCES author(author_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_authorbook_book FOREIGN KEY (book_id) REFERENCES book(book_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================
--      CUSTOMER TABLES
-- ============================
CREATE TABLE customer (
  customer_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL COMMENT 'Username of the customer, must be unique',
  password VARCHAR(100) NOT NULL COMMENT 'Hashed password for account security',
  email VARCHAR(255) NOT NULL COMMENT 'Customer email address, must be unique',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (customer_id),
  CONSTRAINT uq_customer_username UNIQUE (username),
  CONSTRAINT uq_customer_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE customer_details (
  customer_id INT NOT NULL,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  address VARCHAR(255),
  phone VARCHAR(30),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (customer_id),
  CONSTRAINT fk_customer_details_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================
--      BOOK REVIEWS TABLE
-- ============================
CREATE TABLE book_reviews (
  review_id INT NOT NULL AUTO_INCREMENT,
  book_id INT NOT NULL,
  customer_id INT NOT NULL,
  rating INT NOT NULL,
  comment TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (review_id),
  CONSTRAINT uq_review_per_book_per_customer UNIQUE (book_id, customer_id),
  CONSTRAINT fk_review_book FOREIGN KEY (book_id) REFERENCES book(book_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_review_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Re-enable FK checks
SET FOREIGN_KEY_CHECKS = 1;
