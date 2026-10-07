CREATE SEQUENCE order_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE order_item_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE orders (
                        id               BIGINT        NOT NULL DEFAULT nextval('order_id_seq') PRIMARY KEY,
                        order_number     VARCHAR(100)  NOT NULL,
                        username         VARCHAR(255)  NOT NULL,
                        customer_name    VARCHAR(255)  NOT NULL,
                        customer_email   VARCHAR(255)  NOT NULL,
                        customer_phone   VARCHAR(50)   NOT NULL,
                        delivery_address_line1    VARCHAR(255) NOT NULL,
                        delivery_address_line2    VARCHAR(255),
                        delivery_address_city     VARCHAR(100) NOT NULL,
                        delivery_address_state    VARCHAR(100) NOT NULL,
                        delivery_address_zip_code VARCHAR(20)  NOT NULL,
                        delivery_address_country  VARCHAR(100) NOT NULL,
                        status           VARCHAR(50)   NOT NULL,
                        comments         VARCHAR(500),
                        created_at       TIMESTAMP     NOT NULL,
                        updated_at       TIMESTAMP,
                        CONSTRAINT uk_orders_order_number UNIQUE (order_number)
);

CREATE TABLE order_items (
                             id          BIGINT        NOT NULL DEFAULT nextval('order_item_id_seq') PRIMARY KEY,
                             code        VARCHAR(50)   NOT NULL,
                             name        VARCHAR(255)  NOT NULL,
                             price       NUMERIC(12, 2) NOT NULL,
                             quantity    INTEGER       NOT NULL,
                             order_id    BIGINT        NOT NULL,
                             CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
                             CONSTRAINT ck_order_items_price_non_negative CHECK (price >= 0),
                             CONSTRAINT ck_order_items_quantity_positive CHECK (quantity > 0)
);