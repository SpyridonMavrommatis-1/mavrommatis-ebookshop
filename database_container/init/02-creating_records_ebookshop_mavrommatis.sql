-- Insert records into table: author
INSERT INTO author (first_name, last_name, email) VALUES
('George', 'Orwell', 'orwell@example.com'),
('Virginia', 'Woolf', 'woolf@example.com'),
('J.K.', 'Rowling', 'rowling@example.com'),
('Jack', 'Mavros', 'mavros@example.com'),
('Eleni', 'Middleton', 'middleton@example.com');

-- Insert records into table: author_details
INSERT INTO author_details (author_id, biography, birth_date, website) VALUES
(1, 'English novelist and journalist, author of "1984" and "Animal Farm".', '1903-06-25', 'https://en.wikipedia.org/wiki/George_Orwell'),
(2, 'English writer, one of the foremost modernists of the twentieth century.', '1882-01-25', 'https://en.wikipedia.org/wiki/Virginia_Woolf'),
(3, 'British author, best known for the Harry Potter series.', '1965-07-31', 'https://www.jkrowling.com/'),
(4, 'A poet from Ioannina, Greece.', '1988-11-05', NULL),
(5, 'A poet from Ioannina, Greece.', '1988-12-05', NULL);

-- Insert records into table: customer
INSERT INTO customer (username, password, email) VALUES
('alice', 'password1', 'alice@example.com'),
('bob', 'password2', 'bob@example.com');

-- Insert records into table: customer_details
INSERT INTO customer_details (customer_id, first_name, last_name, address, phone) VALUES
(1, 'Alice', 'Smith', '123 Main St, London', '555-1234'),
(2, 'Bob', 'Jones', '456 Elm St, Oxford', '555-5678');

-- Insert records into table: book
INSERT INTO book (title, language, genre, literary_form, isbn, is_collective, author_id) VALUES
('1984', 'English', 'Dystopian', 'Novel', '9780451524935', FALSE, 1),
('Mrs Dalloway', 'English', 'Modernist', 'Novel', '9780156628709', FALSE, 2),
('Harry Potter and the Philosopher''s Stone', 'English', 'Fantasy', 'Novel', '9780747532699', FALSE, 3),
('Poetic Collection', 'English', 'Romance', 'Collection', '9780747531239', TRUE, 4),
('Poetic Collection', 'English', 'Romance', 'Collection', '9780747531239', TRUE, 5);

-- Insert records into table: book_details
INSERT INTO book_details (book_id, publish_date, pages, summary, dimensions, cover_type, weight) VALUES
(1, '1949-06-08', 328, 'A dystopian social science fiction novel.', '20x13x2 cm', 'Paperback', 0.30),
(2, '1925-05-14', 296, 'A novel detailing a day in the life of Clarissa Dalloway.', '21x14x2 cm', 'Hardcover', 0.35),
(3, '1997-06-26', 223, 'The first book in the Harry Potter series.', '19x12x2.5 cm', 'Paperback', 0.32),
(4, '2022-06-26', 130, 'A romantic collection of poems.', '19x12x2.5 cm', 'Paperback', 0.25),
(5, '2022-06-26', 130, 'A romantic collection of poems.', '19x12x2.5 cm', 'Paperback', 0.25);

-- Insert records into table: author_book
INSERT INTO author_book (author_id, book_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 5),
(5, 4);

-- Insert records into table: book_reviews
INSERT INTO book_reviews (book_id, customer_id, rating, comment) VALUES
(1, 1, 5, 'A masterpiece of dystopian literature.'),
(3, 2, 4, 'Exciting and imaginative!'),
(2, 1, 3, NULL),
(4, 1, 5, 'Loved this collection!');
