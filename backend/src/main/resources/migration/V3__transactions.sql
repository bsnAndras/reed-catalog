# V2 -> V3 migration: add transactions table, migrate data from log table

# Create predefined users:
# EXTERNAL_ACCOUNT partner for money leaving the system or coming into the system
# Vendor partner representing the system admin
INSERT INTO partners (id, balance, name)
    VALUES (-1,0, 'EXTERNAL_ACCOUNT'),
           (0,0, '${vendorUsername}');

CREATE TABLE transactions
(
    timestamp    TIMESTAMP NOT NULL,
    amount       INT       NOT NULL,
    description  VARCHAR(100),
    from_partner BIGINT    NOT NULL,
    to_partner   BIGINT    NOT NULL
);

# Migrate data from log table to transactions table based on money_exchange
    # If money_exchange < 0, from_partner = vendor, to_partner = EXTERNAL_ACCOUNT
    # If money_exchange > 0, from_partner = corresponding partner, to_partner = vendor
INSERT INTO transactions (timestamp, amount, description, from_partner, to_partner)
SELECT log.date_time,
       log.money_exchange,
       log.event,
       IF(log.money_exchange < 0,
          (SELECT id FROM partners WHERE name = '${vendorUsername}'),
          (o.partner_id)
       ) AS from_partner,
       IF(log.money_exchange < 0,
          (SELECT id FROM partners WHERE name = 'EXTERNAL_ACCOUNT'),
          (SELECT id FROM partners WHERE name = '${vendorUsername}')
       ) AS to_partner
FROM log
         LEFT JOIN orders AS o ON o.id = log.order_no
WHERE log.money_exchange IS NOT NULL
  AND log.money_exchange <> 0;
