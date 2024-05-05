CREATE TABLE DIALOG_MESSAGE  (
                       id bigserial primary key,
                       from_user_id bigint not null,
                       to_user_id bigint not null,
                       body text not null,
                       created timestamp,
                       CONSTRAINT fk_from_user
                           FOREIGN KEY(from_user_id)
                               REFERENCES USERS(id),
                        CONSTRAINT fk_to_user
                            FOREIGN KEY(to_user_id)
                                REFERENCES USERS(id)
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




