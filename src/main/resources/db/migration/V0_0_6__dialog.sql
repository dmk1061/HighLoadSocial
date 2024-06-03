CREATE TABLE DIALOG_MESSAGE  (
                       id bigserial,
                       from_user_id bigint not null,
                       to_user_id bigint not null,
                       body text not null,
                       created timestamp,
                       CONSTRAINT fk_from_user
                           FOREIGN KEY(from_user_id)
                               REFERENCES USERS(id)
--                        CONSTRAINT fk_to_user
--                            FOREIGN KEY(to_user_id)
--                                REFERENCES USERS(id)
);
INSERT INTO DIALOG_MESSAGE (from_user_id, to_user_id, body, created) VALUES
(1, 2, 'Hey there! How are you?', NOW()),
(2, 1, 'Hi! I''m good, thanks for asking.', NOW() + INTERVAL '1 minute'),
(1, 2, 'That''s great to hear! Anything interesting happen today?', NOW() + INTERVAL '2 minutes'),
(2, 1, 'Not really, just the usual.', NOW() + INTERVAL '3 minutes'),
(1, 2, 'Fair enough. Did you watch the game last night?', NOW() + INTERVAL '4 minutes'),
(2, 1, 'Yeah, it was amazing! Did you catch it?', NOW() + INTERVAL '5 minutes'),
(1, 2, 'No, I missed it. Who won?', NOW() + INTERVAL '6 minutes'),
(2, 1, 'The home team won by a narrow margin.', NOW() + INTERVAL '7 minutes'),
(1, 2, 'Nice! I''ll have to watch the highlights later.', NOW() + INTERVAL '8 minutes'),
(2, 1, 'Definitely worth it!', NOW() + INTERVAL '9 minutes');
INSERT INTO DIALOG_MESSAGE (from_user_id, to_user_id, body, created) VALUES
    (1, 3, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '1 minute'),
    (3, 5, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '2 minutes'),
    (2, 6, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '3 minutes'),
    (4, 8, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '4 minutes'),
    (6, 7, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '5 minutes'),
    (5, 9, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '6 minutes'),
    (10, 12, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '7 minutes'),
    (9, 11, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '8 minutes'),
    (7, 10, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '9 minutes'),
    (12, 14, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '10 minutes'),
    (11, 15, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '11 minutes'),
    (14, 13, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '12 minutes'),
    (15, 4, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '13 minutes'),
    (13, 3, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '14 minutes'),
    (8, 5, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '15 minutes'),
    (7, 1, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '16 minutes'),
    (2, 8, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '17 minutes'),
    (9, 14, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '18 minutes'),
    (11, 7, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '19 minutes'),
     (10, 3, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '20 minutes'),
        (12, 6, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '21 minutes'),
        (13, 2, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '22 minutes'),
        (14, 5, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '23 minutes'),
        (15, 9, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '24 minutes'),
        (3, 11, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '25 minutes'),
        (5, 10, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '26 minutes'),
        (8, 12, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '27 minutes'),
        (4, 13, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '28 minutes'),
        (6, 14, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '29 minutes'),
        (1, 15, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '30 minutes');

    INSERT INTO DIALOG_MESSAGE (from_user_id, to_user_id, body, created) VALUES
        (1, 3, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '1 minute'),
        (3, 5, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '2 minutes'),
        (2, 6, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '3 minutes'),
        (4, 8, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '4 minutes'),
        (6, 7, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '5 minutes'),
        (5, 9, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '6 minutes'),
        (10, 12, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '7 minutes'),
        (9, 11, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '8 minutes'),
        (7, 10, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '9 minutes'),
        (12, 14, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '10 minutes'),
        (11, 15, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '11 minutes'),
        (14, 13, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '12 minutes'),
        (15, 4, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '13 minutes'),
        (13, 3, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '14 minutes'),
        (8, 5, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '15 minutes'),
        (7, 1, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '16 minutes'),
        (2, 8, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '17 minutes'),
        (9, 14, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '18 minutes'),
        (11, 7, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '19 minutes'),
        (10, 3, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '20 minutes'),
        (12, 6, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '21 minutes'),
        (13, 2, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '22 minutes'),
        (14, 5, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '23 minutes'),
        (15, 9, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '24 minutes'),
        (3, 11, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '25 minutes'),
        (5, 10, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '26 minutes'),
        (8, 12, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '27 minutes'),
        (4, 13, 'Привет, как дела?', NOW() - INTERVAL '1 day' + INTERVAL '28 minutes'),
        (6, 14, 'Это интересно.', NOW() - INTERVAL '1 day' + INTERVAL '29 minutes'),
        (1, 15, 'Что ты думаешь об этом?', NOW() - INTERVAL '1 day' + INTERVAL '30 minutes');



