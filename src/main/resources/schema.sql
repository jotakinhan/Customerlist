CREATE TABLE IF NOT EXISTS customer (
    customer_id int(5) NOT NULL AUTO_INCREMENT,
    first_name varchar(50) DEFAULT NULL,
    last_name varchar(50) DEFAULT NULL,
    PRIMARY KEY(customer_id)
    );