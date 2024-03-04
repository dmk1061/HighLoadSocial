CREATE TABLE POSTS  (
                       id bigserial primary key,
                       username varchar not null,
                       body text not null,
                       created timestamp
);

CREATE TABLE SUBSCRIPTION (
                          id bigserial primary key,
                          username varchar not null,
                          friend_username varchar not null,
                           UNIQUE(username, friend_username)
);

