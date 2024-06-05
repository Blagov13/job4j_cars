create table history_owner(
    id serial primary key,
    owners_id int not null references owners(id),
    car_id int not null references car(id)
);