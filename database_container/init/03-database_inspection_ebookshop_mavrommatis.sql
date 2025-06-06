-- Get all the tables 
USE ebookshop_mavrommatis;
SHOW TABLES;

-- See the structure of each table
DESCRIBE author;
DESCRIBE author_book;
DESCRIBE author_details;
DESCRIBE book;
DESCRIBE book_details;
DESCRIBE book_reviews;
DESCRIBE customer;
DESCRIBE customer_details;
DESCRIBE order_item;
DESCRIBE orders;
DESCRIBE payment;


-- See all the books and their details one by one 
SELECT * FROM book;
SELECT * FROM book_details;


-- See all the authors and their details one by one 
SELECT * FROM author;
SELECT * FROM author_details;


-- See all the customer and their details at the same time 
SELECT c.*, cd.* 
FROM customer c
LEFT JOIN customer_details cd ON c.customer_id = cd.customer_id;


-- See certain columns from orders_item in relation with orders table
SELECT o.*, oi.book_id, oi.quantity, oi.unit_price 
FROM orders o
LEFT JOIN order_item oi ON o.order_id = oi.order_id;


--  Displays all book reviews along with the title of the book and the username of the customer who reviewed it
SELECT br.*, b.title, c.username
FROM book_reviews br
JOIN book b ON br.book_id = b.book_id
JOIN customer c ON br.customer_id = c.customer_id;


-- This query retrieves all payment records, along with the associated customer ID and username for each payment.
-- Note: Payments with 'pending' status represent orders that have been done but will be completed upon delivery of the order.
SELECT p.*, o.customer_id, c.username
FROM payment p
JOIN orders o ON p.order_id = o.order_id
JOIN customer c ON o.customer_id = c.customer_id;


-- see all the authors with their books
SELECT b.title, a.first_name, a.last_name
FROM book b
JOIN author_book ab ON b.book_id = ab.book_id
JOIN author a ON ab.author_id = a.author_id;


-- See all the orders of a customer with the books that has purchased
SELECT o.order_id, o.order_date, b.title, oi.quantity, oi.unit_price
FROM orders o
JOIN order_item oi ON o.order_id = oi.order_id
JOIN book b ON oi.book_id = b.book_id
WHERE o.customer_id = 1;  


-- See what books don't have details
SELECT b.*
FROM book b
LEFT JOIN book_details bd ON b.book_id = bd.book_id
WHERE bd.book_id IS NULL;


INSERT INTO book (title, language, genre, literary_form, author_id) VALUES ('Test Book', 'Greek', 'Test', 'TestForm', 4);
INSERT INTO book_details (book_id, isbn, publish_date, pages) VALUES (LAST_INSERT_ID(), '9999999999', '2024-01-01', 99);
INSERT INTO author_book (author_id, book_id) VALUES (4, 6);
-- NOTE: In this development approach, the cascading does not automate updates to the intermediate 'author_book' table. 
-- After inserting a new book, you must explicitly insert the (author_id, book_id) pair in 'author_book' to establish the many-to-many relationship.


-- See all foreign keys
SELECT table_name, column_name, constraint_name, referenced_table_name, referenced_column_name
FROM information_schema.key_column_usage
WHERE table_schema = 'ebookshop_mavrommatis'
  AND referenced_table_name IS NOT NULL;


-- See indexes
SHOW INDEX FROM book;
SHOW INDEX FROM book_details;
SHOW INDEX FROM author_book;
SHOW INDEX FROM author;
SHOW INDEX FROM author_details;
SHOW INDEX FROM customer;
SHOW INDEX FROM customer_details;




