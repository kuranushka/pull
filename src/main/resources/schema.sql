drop table if exists pull, item, fill_item, fill_item_answer,
    fill_pull, fill_pull_fill_items, pull_items, item_answer,
    usr, user_roles cascade;

create table pull
(
    id          bigserial primary key,
    begin_date  date,
    description varchar(255),
    end_date    date,
    is_active   boolean
);

create table fill_pull
(
    id             bigserial primary key,
    begin_date     date,
    description    varchar(255),
    end_date       date,
    interviewer_id bigint,
    source_pull_id bigint
);


create table item
(
    id       bigserial primary key,
    question varchar(255),
    type     varchar(255)
);

create table fill_item
(
    id       bigserial primary key,
    question varchar(255),
    type     varchar(255)
);

create table fill_item_answer
(
    fill_item_id bigint       not null
        constraint fk_fill_item_answer
            references fill_item,
    answer       boolean,
    answer_key   varchar(255) not null,
    primary key (fill_item_id, answer_key)
);

create table fill_pull_fill_items
(
    fill_pull_id  bigint not null
        constraint fk_fill_pull_fill_items_fill_pull_id
            references fill_pull,
    fill_items_id bigint not null
        constraint uk_fill_pull_fill_items_fill_items_id
            unique
        constraint fk_fill_pull_fill_items_fill_items_id
            references fill_item
);


create table pull_items
(
    pull_id  bigint not null
        constraint fk_pull_items_pull_id
            references pull,
    items_id bigint not null
        constraint uk_pull_items_items_id
            unique
        constraint fk_pull_items_items_id
            references item
);

create table item_answer
(
    item_id    bigint       not null
        constraint fk_item_answer_item_id
            references item,
    answer     boolean,
    answer_key varchar(255) not null,
    primary key (item_id, answer_key)
);

create table usr
(
    id        bigserial primary key,
    is_active boolean,
    password  varchar(255),
    user_name varchar(255)
);

create table user_roles
(
    user_id bigint not null
        constraint fk_user_roles_user_id
            references usr,
    roles   varchar(255)
);