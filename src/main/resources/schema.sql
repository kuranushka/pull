drop schema public cascade;
create schema public;

create table answer
(
    id       bigserial primary key,
    question varchar(255),
    type     varchar(255)
);

create table answer_answer_options
(
    answer_id      bigint not null
        constraint fk_answer_answer_options
            references answer,
    answer_options varchar(255)
);


create table fill_survey
(
    id               bigserial primary key,
    begin_date       date,
    description      varchar(255),
    end_date         date,
    interviewer_id   bigint,
    source_survey_id bigint
);


create table fill_survey_answers
(
    filled_survey_id bigint not null
        constraint fk_fill_survey_answers_filled_survey_id
            references fill_survey,
    answers_id       bigint not null
        constraint fk_fill_survey_answers_answers_id
            references answer
);



create table question
(
    id       bigserial primary key,
    question varchar(255),
    type     varchar(255)
);



create table question_answer_options
(
    question_id    bigint not null
        constraint fk_question_answer_options
            references question,
    answer_options varchar(255)
);



create table survey
(
    id          bigserial primary key,
    begin_date  date,
    description varchar(255),
    end_date    date,
    is_active   boolean
);



create table survey_questions
(
    survey_id    bigint not null
        constraint fk_survey_questions_survey_id
            references survey,
    questions_id bigint not null
        constraint fk_survey_questions_questions_id
            references question
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
        constraint fk_user_roles
            references usr,
    roles   varchar(255)
);


