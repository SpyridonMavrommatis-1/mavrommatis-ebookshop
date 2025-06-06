


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
  author_id INT NOT NULL,                 -- Foreign key for One-to-Many: each book has one main author.
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,   -- Timestamp when the record was created.
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Timestamp for the last update.
  PRIMARY KEY (book_id),
  CONSTRAINT fk_book_author	
    FOREIGN KEY (author_id) REFERENCES author(author_id)   -- Creates One-to-Many relation
      ON DELETE RESTRICT                   -- Prevents deletion of author if books exist.
      ON UPDATE CASCADE                    -- Keeps relationship intact if author_id changes.
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  -- utf8mb4 preferred for full Unicode support

-- Create the 'book_details' table to store additional details for each book.
CREATE TABLE book_details (
  book_id INT NOT NULL,	-- Foreign key referencing book(book_id).
  isbn VARCHAR(20), -- ISBN of the book, must not be unique, because of double entries in collective works.
  -- Note: In the development section we must solve the issue of giving same isbn on non-collective works!!!
  publish_date DATE, 
  pages INT,
  summary TEXT,
  dimensions VARCHAR(50), -- The dimensions of a book are given as e.g. "20x15x3 cm" (length x width x height).
  cover_type VARCHAR(50),
  weight DECIMAL(6,2),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (book_id),
  CONSTRAINT fk_book_details_book	-- Primary key; also a foreign key.
    FOREIGN KEY (book_id) REFERENCES book(book_id)
    ON DELETE CASCADE	-- If a book is deleted, its details are also deleted.
    ON UPDATE CASCADE	-- If a book is updated, its details are also updated.
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create the 'author' table to store basic information about each author.
CREATE TABLE author (
  author_id INT NOT NULL AUTO_INCREMENT, 
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (author_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4; -- Preferable charset because of its wide range Unicode support.

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
  -- Note: These review fields must be filled neccesarily by the customer!!!
  comment TEXT,                                   -- Review comment (OPTIONAL: change to NOT NULL if you want it mandatory).
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Date/time review was posted.
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (review_id),
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


-- Create the 'orders' table to store all orders made by customers.
CREATE TABLE orders (
  order_id INT NOT NULL AUTO_INCREMENT,                    
  customer_id INT NOT NULL,                                -- Foreign key: the customer who placed the order.
  order_date DATETIME NOT NULL,        
  status VARCHAR(50),                                      
  total_amount DECIMAL(10,2),                              
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,           
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  PRIMARY KEY (order_id),                                 
  CONSTRAINT fk_orders_customer
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) -- Foreign key: links order to customer.
    ON DELETE RESTRICT                                     -- Prevents deleting customers with existing orders.
    ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


-- Create the 'order_item' table to store the individual books included in each order.
CREATE TABLE order_item (
  order_id INT NOT NULL,                                   -- Foreign key: the order containing the item.
  book_id INT NOT NULL,                                    -- Foreign key: the book being purchased.
  quantity INT NOT NULL DEFAULT 1,                         -- Number of copies of the book in the order.
  unit_price DECIMAL(8,2) NOT NULL,                        -- Price per book at the time of purchase.
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,           -- Timestamp for when the item was added to the order.
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  PRIMARY KEY (order_id, book_id),                         -- Composite primary key: ensures uniqueness of each book in each order.
  CONSTRAINT fk_orderitem_order
    FOREIGN KEY (order_id) REFERENCES orders(order_id)     -- Foreign key: links item to order.
    ON DELETE CASCADE                                      -- If an order is deleted, its items are deleted as well.
    ON UPDATE CASCADE,
  CONSTRAINT fk_orderitem_book
    FOREIGN KEY (book_id) REFERENCES book(book_id)         -- Foreign key: links item to book.
    ON DELETE RESTRICT                                     -- Prevents deleting books that are part of existing orders.
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE payment (
  payment_id INT NOT NULL AUTO_INCREMENT,
  order_id INT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  payment_method ENUM('Credit Card', 'PayPal', 'Bank Transfer', 'Wallet') NOT NULL,
  payment_status ENUM('pending', 'completed', 'failed') NOT NULL,
  payment_date DATETIME,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (payment_id),
  CONSTRAINT fk_payment_order
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Notes:
-- payment_method:
--   'Credit Card': The payment was made or attempted using a credit or debit card.
--   'PayPal': The payment was made or attempted through the PayPal service.
--   'Bank Transfer': The payment was made via direct bank transfer.
--   'Wallet': The payment was made using a digital wallet or stored credits.

-- payment_status:
--   'pending': The payment process has started but is not yet completed; the transaction is awaiting confirmation from the payment provider, or is being processed.
--   'completed': The payment has been successfully processed and confirmed by the payment provider.
--   'failed': The payment attempt was unsuccessful. This could be due to card rejection, insufficient funds, technical issues, cancellation, or timeout.

-- payment_date:
--   The date and time when the payment was actually completed. If the status is 'pending' or 'failed', this may be NULL.


SET FOREIGN_KEY_CHECKS = 1; -- Re-enable foreign key checks after table creation.