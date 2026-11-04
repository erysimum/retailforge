ALTER TABLE order_events
    ADD COLUMN publication_status VARCHAR(20) NOT NULL DEFAULT 'PENDING';