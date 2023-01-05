CREATE TABLE shipping
(
    shipment_id serial primary key ,
    tracking_no VARCHAR(50) NOT NULL ,
    date_shipped DATE NOT NULL
);