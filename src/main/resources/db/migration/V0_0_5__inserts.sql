INSERT INTO SUBSCRIPTION (username, friend_username) VALUES
('john_doe', 'bob_johnson');
INSERT INTO SUBSCRIPTION (username, friend_username) VALUES
('john_doe', 'michael_jones');
INSERT INTO SUBSCRIPTION (username, friend_username) VALUES
('john_doe', 'alice_smith');
INSERT INTO SUBSCRIPTION (username, friend_username) VALUES
('john_doe', 'eva_brown');
INSERT INTO SUBSCRIPTION (username, friend_username) VALUES
('bob_johnson', 'michael_jones');


INSERT INTO POSTS (username, body, created) VALUES
('alice_smith', 'Post 1 by User 1', NOW()),
('alice_smith', 'Post 2 by User 1', NOW()),
('alice_smith', 'Post 3 by User 1', NOW()),
('alice_smith', 'Post 4 by User 1', NOW()),
('alice_smith', 'Post 5 by User 1', NOW()),
('alice_smith', 'Post 6 by User 1', NOW()),
('alice_smith', 'Post 7 by User 1', NOW());

-- Записи для пользователя 2
INSERT INTO POSTS (username, body, created) VALUES
('michael_jones', 'Post 1 by User 2', NOW()),
('michael_jones', 'Post 2 by User 2', NOW()),
('michael_jones', 'Post 3 by User 2', NOW()),
('michael_jones', 'Post 4 by User 2', NOW()),
('michael_jones', 'Post 5 by User 2', NOW()),
('michael_jones', 'Post 6 by User 2', NOW()),
('michael_jones', 'Post 7 by User 2', NOW()),
('michael_jones', 'Post 8 by User 2', NOW());

-- Записи для пользователя 3
INSERT INTO POSTS (username, body, created) VALUES
('bob_johnson', 'Post 1 by User 3', NOW()),
('bob_johnson', 'Post 2 by User 3', NOW()),
('bob_johnson', 'Post 3 by User 3', NOW()),
('bob_johnson', 'Post 4 by User 3', NOW()),
('bob_johnson', 'Post 5 by User 3', NOW());


