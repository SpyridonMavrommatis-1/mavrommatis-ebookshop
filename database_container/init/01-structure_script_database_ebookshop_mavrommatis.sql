
-- Εnsure a fresh setup
DROP SCHEMA IF EXISTS `ebookshop_mavrommatis`;
CREATE SCHEMA `ebookshop_mavrommatis`;
-- Switch the current database
USE `ebookshop_mavrommatis`;
-- Temporarily disable foreign key checks for the current session.
SET FOREIGN_KEY_CHECKS = 0;


-- Create the 'book' table to store the main information about each book.
CREATE TABLE book (
  book_id INT NOT NULL AUTO_INCREMENT, 
  title VARCHAR(255) NOT NULL,
  language VARCHAR(100) NOT NULL,
  genre VARCHAR(100) NOT NULL,            -- Genre of the book (e.g., fantasy, drama, thriller).
  literary_form VARCHAR(100) NOT NULL,    -- Literary form (e.g., novel, poetry, essay), required field.
  isbn VARCHAR(20) NOT NULL,                       -- ISBN of the book, moved from book_details.
  is_collective BOOLEAN NOT NULL DEFAULT FALSE,  -- Indicates if the book is a collective work.
  author_id INT NOT NULL,                 -- Foreign key for One-to-Many: each book has one main author.
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,   -- Timestamp when the record was created.
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Timestamp for the last update.
  PRIMARY KEY (book_id),
  CONSTRAINT fk_book_author
    FOREIGN KEY (author_id) REFERENCES author(author_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT uq_book_unique_entry
    UNIQUE (isbn, author_id, is_collective)  -- Conditional uniqueness emulation
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  -- utf8mb4 preferred for full Unicode support


-- Create the 'book_details' table to store additional details for each book.
CREATE TABLE book_details (
  book_id INT NOT NULL,  -- Foreign key referencing book(book_id).
  publish_date DATE,
  pages INT,
  summary TEXT,
  dimensions VARCHAR(50), -- The dimensions of a book are given as e.g. "20x15x3 cm" (length x width x height).
  cover_type VARCHAR(50),
  weight DECIMAL(6,2),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (book_id),
  CONSTRAINT fk_book_details_book  -- Primary key; also a foreign key.
    FOREIGN KEY (book_id) REFERENCES book(book_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- Create the 'author' table to store basic information about each author.
CREATE TABLE author (
  author_id INT NOT NULL AUTO_INCREMENT, 
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (author_id),
  CONSTRAINT uq_author_email UNIQUE (email)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- Create the 'author_details' table to store additional information about each author.
CREATE TABLE author_details (
  author_id INT NOT NULL, -- Again the primary key is the same with the foreign key (@OneToOne relation)
  biography TEXT,
  birth_date DATE,
  website VARCHAR(255),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (author_id),
  CONSTRAINT fk_author_details_author
    FOREIGN KEY (author_id) REFERENCES author(author_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- Create the 'author_book' junction table to establish a many-to-many relationship between authors and books.
CREATE TABLE author_book (
  author_id INT NOT NULL, -- References the unique identifier of an author.
  book_id INT NOT NULL,   -- References the unique identifier of a book.
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (author_id, book_id),
  CONSTRAINT fk_authorbook_author
    FOREIGN KEY (author_id) REFERENCES author(author_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_authorbook_book
    FOREIGN KEY (book_id) REFERENCES book(book_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- Create the 'book_reviews' table to store reviews for each book by registered customers.
-- One-to-Many: Each book can have many reviews, and each review is linked to a customer.
CREATE TABLE book_reviews (
  review_id INT NOT NULL AUTO_INCREMENT,          -- Unique identifier for each review.
  book_id INT NOT NULL,                           -- Foreign key: reviewed book.
  customer_id INT NOT NULL,                       -- Foreign key: customer who wrote the review.
  rating INT NOT NULL,                            -- Numeric rating given by the customer (REQUIRED).
  -- Note: These review fields must be filled necessarily by the customer!!!
  comment TEXT,                                   -- Review comment (OPTIONAL: change to NOT NULL if you want it mandatory).
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Date/time review was posted.
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (review_id),
  CONSTRAINT uq_review_per_book_per_customer UNIQUE (book_id, customer_id), -- A customer can review a specific book only once.
  CONSTRAINT fk_review_book
    FOREIGN KEY (book_id) REFERENCES book(book_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_review_customer
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


-- Create the 'customer' table to store the main account information for each customer.
CREATE TABLE customer (
  customer_id INT NOT NULL AUTO_INCREMENT,                 
  username VARCHAR(100) NOT NULL UNIQUE,                   
  password VARCHAR(100) NOT NULL,                          -- Password (should be stored hashed in a real application).
  email VARCHAR(255) NOT NULL UNIQUE,                      -- Customer's email address, must be unique.
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,           
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  PRIMARY KEY (customer_id)                               
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  

-- Create the 'customer_details' table to store additional information for each customer.
CREATE TABLE customer_details (
  customer_id INT NOT NULL,                                -- Foreign key, also primary key: One-to-One relationship.
  first_name VARCHAR(100),                                 
  last_name VARCHAR(100),                                  
  address VARCHAR(255),                                    
  phone VARCHAR(30),                                       
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,           
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  PRIMARY KEY (customer_id),                               -- Primary key, ensures only one set of details per customer.
  CONSTRAINT fk_customer_details_customer
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) -- Foreign key constraint: One-to-One with 'customer'.
    ON DELETE CASCADE                                      -- If the customer is deleted, their details are deleted as well.
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

