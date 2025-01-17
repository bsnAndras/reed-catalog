# Reed Catalog
A program for managing bassoon reeds
## Database structure
Tables: 
### Log
- date-time
- event
- order no.
- transaction
- actual balance
### Reed
- id
- batch_no.
- condition
- sell price
- order_no.
### Batch
- id
- batch name
- date of purchase
- maker
- quantity
- total buy price
- description
### Order
- id
- date of purchase
- customer_id
- total price
- amount to pay
### Customer
- id
- name
- balance
