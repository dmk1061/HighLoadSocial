CREATE TABLE POSTS  (
                       id bigserial,
                       user_id bigint not null,
                       body text not null,
                       created timestamp
);

CREATE TABLE SUBSCRIPTION (
                          id bigserial primary key,
                          user_id bigint not null,
                          friend_id bigint not null,
                          UNIQUE(user_id, friend_id)
);

