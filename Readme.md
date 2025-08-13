# Reed Catalog
A program for managing bassoon reeds
## Database structure
Tables:

### Partner
- id
- name
- balance

>the partners table needs a __default vendor__ record, which is created on first run.
> This record is used to track the vendor's balance and transactions.

_Necessary properties as environment variables:_
- VENDOR_USERNAME
- VENDOR_INITIAL_BALANCE

### Log
- date-time
- event
- order no.
- money-exchange
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
- partner_id
- total price
- amount to pay
