-- Подключаем необходимые расширения
CREATE EXTENSION IF NOT EXISTS pgcrypto;




CREATE TABLE USERS(
                       id bigserial primary key ,
                       name varchar(30) not null ,
                       surname varchar(30) not null,
                       age int not null,
                       sex varchar(1) not null,
                       address varchar not null,
                       username varchar not null,
                       password  varchar not null
);


CREATE TABLE INTEREST (
                          id bigserial primary key,
                          interest_name varchar unique not null
);

CREATE TABLE USER_INTEREST (
                          id bigserial primary key,
                          interest_id bigint not null,
                          user_id bigint not null,
                          CONSTRAINT fk_interest
                            FOREIGN KEY(interest_id)
                              REFERENCES INTEREST(id),
--                          CONSTRAINT fk_user
--                            FOREIGN KEY(user_id)
--                              REFERENCES USERS(id),
                           UNIQUE(user_id, interest_id)
);