INSERT INTO USERS (name, surname, age, sex, address, username, password) VALUES
  ('John', 'Doe', 25, 'M', 'New York', 'john_doe', crypt('hashed_password_1', gen_salt('bf', 10))),
  ('Alice', 'Smith', 30, 'F', 'Los Angeles', 'alice_smith', crypt('hashed_password_2', gen_salt('bf', 10))),
  ('Bob', 'Johnson', 22, 'M', 'London', 'bob_johnson', crypt('hashed_password_3', gen_salt('bf', 10))),
  ('Eva', 'Brown', 28, 'F', 'Paris', 'eva_brown', crypt('hashed_password_4', gen_salt('bf', 10))),
  ('Michael', 'Jones', 35, '', 'Tokyo', 'michael_jones', crypt('hashed_password_5', gen_salt('bf', 10))),
  ('Emily', 'Taylor', 29, 'F', 'Berlin', 'emily_taylor', crypt('hashed_password_6', gen_salt('bf', 10))),
  ('James', 'Wilson', 27, 'M', 'Madrid', 'james_wilson', crypt('hashed_password_7', gen_salt('bf', 10))),
  ('Sophia', 'Anderson', 31, 'F', 'Rome', 'sophia_anderson', crypt('hashed_password_8', gen_salt('bf', 10))),
  ('David', 'White', 33, 'M', 'Moscow', 'david_white', crypt('hashed_password_9', gen_salt('bf', 10))),
  ('Emma', 'Brown', 26, 'F', 'Sydney', 'emma_brown', crypt('hashed_password_10', gen_salt('bf', 10))),
  ('Oliver', 'Martinez', 34, 'M', 'Beijing', 'oliver_martinez', crypt('hashed_password_11', gen_salt('bf', 10))),
  ('Isabella', 'Lopez', 32, 'F', 'Toronto', 'isabella_lopez', crypt('hashed_password_12', gen_salt('bf', 10))),
  ('William', 'Garcia', 23, 'M', 'Dubai', 'william_garcia', crypt('hashed_password_13', gen_salt('bf', 10))),
  ('Ava', 'Rodriguez', 24, 'F', 'Singapore', 'ava_rodriguez', crypt('hashed_password_14', gen_salt('bf', 10))),
  ('Benjamin', 'Hernandez', 27, 'M', 'Mumbai', 'benjamin_hernandez', crypt('hashed_password_15', gen_salt('bf', 10))),
  ('Mia', 'Perez', 29, 'F', 'Seoul', 'mia_perez', crypt('hashed_password_16', gen_salt('bf', 10))),
  ('Henry', 'Sanchez', 31, 'M', 'Rio de Janeiro', 'henry_sanchez', crypt('hashed_password_17', gen_salt('bf', 10))),
  ('Charlotte', 'Rivera', 30, 'F', 'Amsterdam', 'charlotte_rivera', crypt('hashed_password_18', gen_salt('bf', 10))),
  ('Alexander', 'Harris', 28, 'M', 'Stockholm', 'alexander_harris', crypt('hashed_password_19', gen_salt('bf', 10))),
  ('Amelia', 'Ramirez', 26, 'F', 'Athens', 'amelia_ramirez', crypt('hashed_password_20', gen_salt('bf', 10)));

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