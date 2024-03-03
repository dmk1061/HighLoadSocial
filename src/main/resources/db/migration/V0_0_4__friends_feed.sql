CREATE TABLE POSTS  (
                       id bigserial primary key,
                       user_id bigint not null,
                       body text not null,
                       created timestamp,
                       CONSTRAINT fk_user
                          FOREIGN KEY(user_id)
                             REFERENCES USERS(id)
);

CREATE TABLE RELATION (
                          id bigserial primary key,
                          user_id bigint not null,
                          friend_id bigint not null,
                          CONSTRAINT fk_user
                            FOREIGN KEY(user_id)
                              REFERENCES USERS(id),
                          CONSTRAINT fk_friend
                            FOREIGN KEY(friend_id)
                              REFERENCES USERS(id),
                           UNIQUE(user_id, friend_id)
);

