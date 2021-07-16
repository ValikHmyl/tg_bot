CREATE TABLE if not exists subscriber(
    id BIGINT PRIMARY KEY NOT NULL,
    chat_id BIGINT,
    name VARCHAR(50)
);