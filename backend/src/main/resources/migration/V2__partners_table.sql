# V1 -> V2 migration: rename customers to partners, add notes field to orders table

ALTER TABLE customers RENAME TO partners;

ALTER TABLE orders
    RENAME COLUMN customer_id TO partner_id;
ALTER TABLE orders
    ADD notes VARCHAR(255) DEFAULT NULL AFTER amount_to_pay;