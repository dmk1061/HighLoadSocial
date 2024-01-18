INSERT INTO ADDRESS (city) VALUES
  ('New York'),
  ('Los Angeles'),
  ('London'),
  ('Paris'),
  ('Tokyo');

-- Добавление данных в таблицу USERS
--INSERT INTO USERS (name, surname, age, sex, address_id, login, password) VALUES
--  ('John', 'Doe', 25, 'M', 1, 'john_doe', crypt('hashed_password_1', '${secret-key}')),
--  ('Alice', 'Smith', 30, 'F', 2, 'alice_smith', crypt('hashed_password_2', '${secret-key}')),
--  ('Bob', 'Johnson', 22, 'M', 3, 'bob_johnson', crypt('hashed_password_3', '${secret-key}')),
--  ('Eva', 'Brown', 28, 'F', 4, 'eva_brown', crypt('hashed_password_4', '${secret-key}')),
--  ('Michael', 'Jones', 35, '', 5, 'michael_jones', crypt('hashed_password_5', '${secret-key}'));
INSERT INTO USERS (name, surname, age, sex, address_id, login, password) VALUES
  ('John', 'Doe', 25, 'M', 1, 'john_doe', crypt('hashed_password_1', gen_salt('bf', 10))),
  ('Alice', 'Smith', 30, 'F', 2, 'alice_smith', crypt('hashed_password_2', gen_salt('bf', 10))),
  ('Bob', 'Johnson', 22, 'M', 3, 'bob_johnson', crypt('hashed_password_3', gen_salt('bf', 10))),
  ('Eva', 'Brown', 28, 'F', 4, 'eva_brown', crypt('hashed_password_4', gen_salt('bf', 10))),
  ('Michael', 'Jones', 35, '', 5, 'michael_jones', crypt('hashed_password_5', gen_salt('bf', 10)));
-- Добавление данных в таблицу INTEREST
INSERT INTO INTEREST (interest_name) VALUES
  ('Sports'),
  ('Music'),
  ('Travel'),
  ('Reading'),
  ('Cooking');

-- Добавление данных в таблицу USER_INTEREST
INSERT INTO USER_INTEREST (interest_id, user_id) VALUES
  (1, 1),  -- John Doe interested in Sports
  (2, 2),  -- Alice Smith interested in Music
  (3, 3),  -- Bob Johnson interested in Travel
  (4, 4),  -- Eva Brown interested in Reading
  (5, 5),  -- Michael Jones interested in Cooking

  (1, 2),  -- John Doe interested in Music
  (2, 3),  -- Alice Smith interested in Travel
  (3, 4),  -- Bob Johnson interested in Reading
  (4, 5),  -- Eva Brown interested in Cooking
  (5, 1);  -- Michael Jones interested in Sports