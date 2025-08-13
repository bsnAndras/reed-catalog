ALTER TABLE customers RENAME TO partners;
ALTER TABLE orders
    RENAME COLUMN customer_id TO partner_id;