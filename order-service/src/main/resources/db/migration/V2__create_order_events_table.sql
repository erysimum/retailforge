CREATE SEQUENCE order_event_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE order_events (
                              id            BIGINT        NOT NULL DEFAULT nextval('order_event_id_seq') PRIMARY KEY,
                              event_id      VARCHAR(100)  NOT NULL,
                              event_type    VARCHAR(50)   NOT NULL,
                              order_number  VARCHAR(100)  NOT NULL,
                              payload       TEXT          NOT NULL,
                              created_at    TIMESTAMP     NOT NULL,
                              CONSTRAINT uk_order_events_event_id UNIQUE (event_id)
);