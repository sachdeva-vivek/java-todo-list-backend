CREATE TABLE item(
    id VARCHAR(36) NOT NULL primary key,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    date_created timestamptz DEFAULT (now() at time zone 'utc') not null,
    date_modified timestamptz
);