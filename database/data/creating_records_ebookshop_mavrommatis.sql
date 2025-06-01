-- Insert records into table: author
-- Adding five authors, including two fictional ones for demonstration purposes.
INSERT INTO author (first_name, last_name) VALUES 
('George', 'Orwell'),          -- author_id = 1
('Virginia', 'Woolf'),         -- author_id = 2
('J.K.', 'Rowling'),           -- author_id = 3
('Jack', 'Mavros'),            -- author_id = 4 (fictional)
('Eleni', 'Mavri');            -- author_id = 5 (fictional)

-- Insert records into table: author_details
-- Link detailed information to each author via author_id (One-to-One relationship)
INSERT INTO author_details (author_id, biography, birth_date, website) VALUES
(1, 'English novelist and journalist, author of "1984" and "Animal Farm".', '1903-06-25', 'https://en.wikipedia.org/wiki/George_Orwell'),
(2, 'English writer, one of the foremost modernists of the twentieth century.', '1882-01-25', 'https://en.wikipedia.org/wiki/Virginia_Woolf'),
(3, 'British author, best known for the Harry Potter series.', '1965-07-31', 'https://www.jkrowling.com/'),
(4, 'A poet from Ioannina, Greece.', '1988-11-05', NULL),   -- Jack Mavros (fictional)
(5, 'A poet from Ioannina, Greece.', '1988-12-05', NULL);   -- Eleni Mavri (fictional)

-- Insert records into table: customer
-- Creating two customers (users) for book purchases and reviews.
INSERT INTO customer (username, password, email) VALUES
('alice', 'password1', 'alice@example.com'),  -- customer_id = 1
('bob', 'password2', 'bob@example.com');      -- customer_id = 2

-- Insert records into table: customer_details
-- Storing personal and contact information for each customer.
INSERT INTO customer_details (customer_id, first_name, last_name, address, phone) VALUES
(1, 'Alice', 'Smith', '123 Main St, London', '555-1234'),
(2, 'Bob', 'Jones', '456 Elm St, Oxford', '555-5678');

-- Insert records into table: book
-- Each book references its main author via author_id (One-to-Many relationship).
INSERT INTO book (title, language, genre, literary_form, author_id) VALUES
('1984', 'English', 'Dystopian', 'Novel', 1),                              -- book_id = 1
('Mrs Dalloway', 'English', 'Modernist', 'Novel', 2),                      -- book_id = 2
('Harry Potter and the Philosopher''s Stone', 'English', 'Fantasy', 'Novel', 3), -- book_id = 3
('Poetic Collection', 'English', 'Romance', 'Collection', 4),            -- book_id = 4 (Jack Mavros)
('Poetic Collection', 'English', 'Romance', 'Collection', 5);            -- book_id = 4 (Eleni Mavri)


-- Insert records into table: book_details
-- Supplementary book information (some fields can be left NULL)
INSERT INTO book_details (book_id, isbn, publish_date, pages, summary, dimensions, cover_type, weight) VALUES
(1, '9780451524935', '1949-06-08', 328, 'A dystopian social science fiction novel.', '20x13x2 cm', 'Paperback', 0.30),
(2, '9780156628709', '1925-05-14', 296, 'A novel detailing a day in the life of Clarissa Dalloway.', '21x14x2 cm', 'Hardcover', 0.35),
(3, '9780747532699', '1997-06-26', 223, 'The first book in the Harry Potter series.', '19x12x2.5 cm', 'Paperback', 0.32),
(4, '9780747531239', '2022-06-26', 130, 'A romantic collection of poems', '19x12x2.5 cm', 'Paperback', 0.25),
(5, '9780747531239', '2022-06-26', 130, 'A romantic collection of poems', '19x12x2.5 cm', 'Paperback', 0.25);


-- Insert records into table: author_book
-- Many-to-Many relation: a book can have multiple authors and vice versa.
INSERT INTO author_book (author_id, book_id) VALUES
(1, 1),  -- George Orwell -> 1984
(2, 2),  -- Virginia Woolf -> Mrs Dalloway
(3, 3),  -- J.K. Rowling -> Harry Potter
(4, 5),  -- Jack Mavros -> Poetic Collection (Eleni Mavri also author)
(5, 4);  -- Eleni Mavri -> Poetic Collection (Jack Mavros also author)


-- Insert records into table: orders
INSERT INTO orders (customer_id, order_date, status, total_amount) VALUES
(1, '2024-05-27 09:00:00', 'completed', 19.98),     -- Alice: paid online, order completed
(2, '2024-05-27 10:30:00', 'pending', 14.50),       -- Bob: waiting for payment (not completed)
(1, '2024-05-28 12:00:00', 'pending', 25.00),       -- Alice: new order, not yet paid
(2, '2024-05-28 13:30:00', 'pending', 35.00);       -- Bob: new order, failed payment

-- Insert records into table: order_item
INSERT INTO order_item (order_id, book_id, quantity, unit_price) VALUES
(1, 1, 1, 10.99),   -- Alice: 1 copy of 1984
(1, 3, 1, 8.99),    -- Alice: 1 copy of Harry Potter
(2, 2, 1, 14.50),   -- Bob: 1 copy of Mrs Dalloway
(3, 4, 1, 25.00),   -- Alice: 1 copy of Poetic Collection
(4, 3, 2, 17.50);   -- Bob: 2 copies of Harry Potter (for new order)

-- Insert records into table: payment
INSERT INTO payment (order_id, amount, payment_method, payment_status, payment_date) VALUES
(1, 19.98, 'Credit Card', 'completed', '2024-05-27 09:10:00'),   -- Alice paid online, order completed
(2, 14.50, 'PayPal', 'pending', NULL),                          -- Bob chose PayPal, but hasn't completed payment
(3, 25.00, 'Bank Transfer', 'pending', NULL),                   -- Alice: awaiting confirmation for bank transfer
(4, 35.00, 'Wallet', 'failed', '2024-05-28 14:00:00');          -- Bob tried to pay with Wallet, failed (e.g. not enough balance)

-- Insert records into table: book_reviews
INSERT INTO book_reviews (book_id, customer_id, rating, comment) VALUES
(1, 1, 5, 'A masterpiece of dystopian literature.'),    -- Alice reviews 1984
(3, 2, 4, 'Exciting and imaginative!'),                 -- Bob reviews Harry Potter
(2, 1, 3, NULL),                                        -- Alice reviews Mrs Dalloway (rating required, comment optional)
(4, 1, 5, 'Loved this collection!'),                    -- Alice reviews Poetic Collection
(3, 2, 1, 'Received wrong edition, disappointed.');     -- Bob reviews Harry Potter again (for another order)

