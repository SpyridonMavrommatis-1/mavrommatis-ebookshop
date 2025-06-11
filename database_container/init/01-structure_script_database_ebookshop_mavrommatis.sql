
-- Εnsure a fresh setup
DROP SCHEMA IF EXISTS `ebookshop_mavrommatis`;
CREATE SCHEMA `ebookshop_mavrommatis`;
-- Switch the current database
USE `ebookshop_mavrommatis`;
-- Temporarily disable foreign key checks for the current session.
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE book (
  book_id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL COMMENT 'Title of the book',
  language VARCHAR(100) NOT NULL COMMENT 'Language the book is written in',
  genre VARCHAR(100) NOT NULL COMMENT 'Genre of the book, e.g. fantasy, drama, thriller',
  literary_form VARCHAR(100) NOT NULL COMMENT 'Literary form, e.g. novel, poetry, essay',
  isbn VARCHAR(20) NOT NULL COMMENT 'International Standard Book Number, moved from book_details',
  is_collective BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Indicates if the book is a collective work',
  author_id INT NOT NULL COMMENT 'Foreign key referencing the main author of the book',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when the record was created',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp for the last update',
  PRIMARY KEY (book_id),
  CONSTRAINT fk_book_author
    FOREIGN KEY (author_id) REFERENCES author(author_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT uq_book_unique_entry
    UNIQUE (isbn, author_id, is_collective)
    COMMENT 'Ensures unique combination of ISBN, author, and collective status to prevent duplicates'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4
  COMMENT='Store main information about each book in the application';


CREATE TABLE book_details (
  book_id INT NOT NULL COMMENT 'Foreign key referencing book(book_id)',
  publish_date DATE COMMENT 'Publication date of the book',
  pages INT COMMENT 'Number of pages in the book',
  summary TEXT COMMENT 'Summary or synopsis of the book',
  dimensions VARCHAR(50) COMMENT 'Physical dimensions, e.g. "20x15x3 cm"',
  cover_type VARCHAR(50) COMMENT 'Type of book cover, e.g. paperback, hardcover',
  weight DECIMAL(6,2) COMMENT 'Weight of the book in kilograms',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when the record was created',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp for the last update',
  PRIMARY KEY (book_id),
  CONSTRAINT fk_book_details_book
    FOREIGN KEY (book_id) REFERENCES book(book_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Store additional details for each book';

CREATE TABLE author (
  author_id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(100) NOT NULL COMMENT 'Author’s first name',
  last_name VARCHAR(100) NOT NULL COMMENT 'Author’s last name',
  email VARCHAR(255) NOT NULL COMMENT 'Author email address, must be unique',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when the record was created',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp for the last update',
  PRIMARY KEY (author_id),
  CONSTRAINT uq_author_email
    UNIQUE (email)
    COMMENT 'Ensures each author has a unique email address to prevent duplicates'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4
  COMMENT='Store basic information about each author';


CREATE TABLE author_details (
  author_id INT NOT NULL COMMENT 'Foreign key referencing author(author_id)',
  biography TEXT COMMENT 'Author biography or background information',
  birth_date DATE COMMENT 'Author’s date of birth',
  website VARCHAR(255) COMMENT 'Author’s personal or official website URL',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when the record was created',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp for the last update',
  PRIMARY KEY (author_id),
  CONSTRAINT fk_author_details_author
    FOREIGN KEY (author_id) REFERENCES author(author_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Store additional information for each author';


CREATE TABLE author_book (
  author_id INT NOT NULL COMMENT 'Foreign key referencing author(author_id)',
  book_id INT NOT NULL COMMENT 'Foreign key referencing book(book_id)',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when the record was created',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp for the last update',
  PRIMARY KEY (author_id, book_id),
  CONSTRAINT fk_authorbook_author
    FOREIGN KEY (author_id) REFERENCES author(author_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT fk_authorbook_book
    FOREIGN KEY (book_id) REFERENCES book(book_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Junction table to link authors and books (many-to-many relation)';


CREATE TABLE book_reviews (
  review_id INT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for each review',
  book_id INT NOT NULL COMMENT 'Foreign key referencing the reviewed book',
  customer_id INT NOT NULL COMMENT 'Foreign key referencing the reviewing customer',
  rating INT NOT NULL COMMENT 'Numeric rating given by the customer',
  comment TEXT COMMENT 'Review comment provided by the customer (optional)',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when the review was posted',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp for the last update',
  PRIMARY KEY (review_id),
  CONSTRAINT uq_review_per_book_per_customer
    UNIQUE (book_id, customer_id)
    COMMENT 'Enforces that each customer can review a specific book only once',
  CONSTRAINT fk_review_book
    FOREIGN KEY (book_id) REFERENCES book(book_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT fk_review_customer
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4
  COMMENT='Store customer reviews for books';


CREATE TABLE customer (
  customer_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL COMMENT 'Username of the customer, must be unique',
  password VARCHAR(100) NOT NULL COMMENT 'Hashed password for account security',
  email VARCHAR(255) NOT NULL COMMENT 'Customer email address, must be unique',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when the record was created',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp for the last update',
  PRIMARY KEY (customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4
  COMMENT='Store main account information for customers';

CREATE TABLE customer_details (
  customer_id INT NOT NULL COMMENT 'Foreign key referencing customer(customer_id)',
  first_name VARCHAR(100) COMMENT 'Customer’s first name',
  last_name VARCHAR(100) COMMENT 'Customer’s last name',
  address VARCHAR(255) COMMENT 'Customer’s mailing address',
  phone VARCHAR(30) COMMENT 'Customer’s phone number',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when the record was created',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp for the last update',
  PRIMARY KEY (customer_id),
  CONSTRAINT fk_customer_details_customer
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Store additional information for each customer';