CREATE TABLE orders
(
    order_id serial primary key ,
    product_name VARCHAR(255) NOT NULL ,
    product_type VARCHAR(255) NOT NULL ,
    date_added DATE NOT NULL,
    status VARCHAR(20) NOT NULL
);