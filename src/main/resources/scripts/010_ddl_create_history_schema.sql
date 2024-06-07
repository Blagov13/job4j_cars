create table history(
    id serial primary key,
    startAt TIMESTAMP,
    endAt TIMESTAMP,
    history_owner_id INT REFERENCES history_owner(id) NOT NULL
);