CREATE TABLE participates (
   id serial PRIMARY KEY,
   user_id int not null REFERENCES user(id),
   post_id int not null REFERENCES post(id),
   UNIQUE (user_id, post_id)
);