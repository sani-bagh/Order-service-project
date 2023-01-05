CREATE TABLE payment
(
    payment_id serial primary key ,
    account_number VARCHAR(50) NOT NULL ,
    amount VARCHAR(50) NOT NULL ,
    date_paid DATE NOT NULL
);