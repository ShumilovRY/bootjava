DELETE
FROM user_roles;
DELETE
FROM vote;
DELETE
FROM users;
DELETE
FROM restaurant;
DELETE
FROM item;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (title)
VALUES ('Mak'),
       ('BK'),
       ('KFC');

INSERT INTO vote (local_date, local_time, restaurant_id, user_id)
VALUES ('2022-02-05', '06:01', 1, 1),
       ('2022-02-05', '08:00', 2, 2),
       ('2022-02-06', '08:20', 3, 1),
       ('2022-02-06', '10:59', 2, 2),
       (CURRENT_DATE, '08:08', 3, 1);

INSERT INTO item (local_date, price, title, restaurant_id)
VALUES (CURRENT_DATE, 111, 'Mak Burger1', 1),
       (CURRENT_DATE, 112, 'Mak Burger2', 1),
       (CURRENT_DATE, 113, 'Mak Burger3', 1),
       (CURRENT_DATE, 211, 'BK Burger1', 2),
       (CURRENT_DATE, 212, 'BK Burger2', 2),
       (CURRENT_DATE, 213, 'BK Burger3', 2),
       (CURRENT_DATE, 311, 'KFC Burger1', 3),
       (CURRENT_DATE, 312, 'KFC Burger2', 3),
       (CURRENT_DATE, 313, 'KFC Burger3', 3);