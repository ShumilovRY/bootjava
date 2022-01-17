INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', '{noop}password'),
       ('admin@mail.ru', 'Admin_first', 'Admin_Last', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);
INSERT INTO LUNCHES (DATE)
VALUES ('2021-05-16'),
       ('2021-05-16'),
       ('2021-05-16');

INSERT INTO RESTAURANTS (NAME, LUNCH_ID) VALUES ('Mak', 1);
INSERT INTO RESTAURANTS (NAME, LUNCH_ID) VALUES ('BK', 2);
INSERT INTO RESTAURANTS (NAME, LUNCH_ID) VALUES ('KFC', 3);

INSERT INTO ITEMS(DISH_NAME, PRICE, LUNCH_ID)
VALUES ('Prime Filet Mignon', 44, 1),
       ('Chateaubriand Au Poivre', 125, 1),
       ('Whole Branzino', 70, 1),
       ('Tuna Romesco', 36, 1),
       ('Arancini Lobster', 18, 1),
       ('Ice-Cold Shellfish', 149, 2),
       ('Half Maine Lobster', 49, 2),
       ('Classic Caesar', 16, 2),
       ('Scottish Salmon', 38, 2),
       ('Miso-Glased Sea Bass', 46, 2),
       ('Contadina', 15, 3),
       ('Fettuccine Al Gusto', 17, 3),
       ('Pollo Caprese', 20, 3),
       ('Lodi', 17, 3),
       ('Cavolo E Ricota', 17, 3);