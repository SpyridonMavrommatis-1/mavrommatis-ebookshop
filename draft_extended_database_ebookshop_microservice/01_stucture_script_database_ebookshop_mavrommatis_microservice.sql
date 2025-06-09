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