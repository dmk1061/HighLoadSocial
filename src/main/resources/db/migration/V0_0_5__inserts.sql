INSERT INTO SUBSCRIPTION (user_id, friend_id) VALUES
(1, 2);
INSERT INTO SUBSCRIPTION (user_id, friend_id) VALUES
(1, 3);
INSERT INTO SUBSCRIPTION (user_id, friend_id) VALUES
(1, 4);
INSERT INTO SUBSCRIPTION (user_id, friend_id) VALUES
(1, 5);
INSERT INTO SUBSCRIPTION (user_id, friend_id) VALUES
(2, 3);

INSERT INTO POSTS (user_id, body, created) VALUES
(2, 'Post 1 by User 1', NOW()),
(2, 'Post 2 by User 1', NOW()),
(2, 'Post 3 by User 1', NOW()),
(2, 'Post 4 by User 1', NOW()),
(2, 'Post 5 by User 1', NOW()),
(2, 'Post 6 by User 1', NOW()),
(2, 'Post 7 by User 1', NOW());

INSERT INTO POSTS (user_id, body, created) VALUES
(5, 'Post 1 by User 2', NOW()),
(5, 'Post 2 by User 2', NOW()),
(5, 'Post 3 by User 2', NOW()),
(5, 'Post 4 by User 2', NOW()),
(5, 'Post 5 by User 2', NOW()),
(5, 'Post 6 by User 2', NOW()),
(5, 'Post 7 by User 2', NOW()),
(5, 'Post 8 by User 2', NOW());

INSERT INTO POSTS (user_id, body, created) VALUES
(3, 'Post 1 by User 3', NOW()),
(3, 'Post 2 by User 3', NOW()),
(3, 'Post 3 by User 3', NOW()),
(3, 'Post 4 by User 3', NOW()),
(3, 'Post 5 by User 3', NOW());


