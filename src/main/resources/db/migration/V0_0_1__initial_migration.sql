CREATE EXTENSION IF NOT EXISTS pgcrypto;


CREATE TABLE ADDRESS  (
                       id bigserial primary key,
                       city varchar(50) unique

);

CREATE TABLE USERS(
                       id bigserial primary key ,
                       name varchar(30) not null ,
                       surname varchar(30) not null,
                       age int not null,
                       sex varchar(1) not null,
                       address_id bigint not null,
                       login varchar  unique not null,
                       password  varchar not null,
                       CONSTRAINT fk_address
                          FOREIGN KEY(address_id)
                              REFERENCES ADDRESS(id)
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
                          CONSTRAINT fk_user
                            FOREIGN KEY(user_id)
                              REFERENCES USERS(id),
                           UNIQUE(user_id, interest_id)
);