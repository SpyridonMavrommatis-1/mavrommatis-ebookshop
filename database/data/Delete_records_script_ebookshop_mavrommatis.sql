-- When I detect an issue that can be solved from the creating_records script I use this script
-- otherwise I try to solve it by rearrange the structure script 

-- Remove safety constraint
SET FOREIGN_KEY_CHECKS = 0;

-- Delete all records from all tables
TRUNCATE TABLE book_reviews;
TRUNCATE TABLE author_book;
TRUNCATE TABLE book_details;
TRUNCATE TABLE order_item;
TRUNCATE TABLE payment;
TRUNCATE TABLE orders;
TRUNCATE TABLE customer_details;
TRUNCATE TABLE book;
TRUNCATE TABLE author_details;
TRUNCATE TABLE customer;
TRUNCATE TABLE author;

-- Restore safety constraint 
SET FOREIGN_KEY_CHECKS = 1;







