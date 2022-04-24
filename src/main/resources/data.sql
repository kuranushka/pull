-- *******SECURITY TABLES*******
insert into usr (is_active, password, user_name)
values (true, '$2a$04$efl6wLEqVgaa3sM0E9vCxusFELFF1Q3R/Ts/6ZhHsFs1GriBQF1ne', 'admin');

insert into user_roles (user_id, roles)
values (1, 'ADMIN');

-- *******PULL TABLES*******
insert into survey(begin_date, description, end_date, is_active)
values ('2022-01-01', 'First active survey', '2022-12-31', true),
       ('2022-01-01', 'Second active survey', '2022-12-31', true),
       ('2022-01-01', 'Third not active survey', '2022-12-31', false);

insert into question (question, type)
values ('How many hours in a day?', 'SIMPLE_STRING_ANSWER'),
       ('Capital of Japan?', 'SINGLE_OPTION_ANSWER'),
       ('Summer sports?', 'MULTI_OPTION_ANSWER'),

       ('How many time zones are there in Russia?', 'SIMPLE_STRING_ANSWER'),
       ('Capital of China?', 'SINGLE_OPTION_ANSWER'),
       ('Winter sports?', 'MULTI_OPTION_ANSWER'),

       ('How many fingers are on the hand?', 'SIMPLE_STRING_ANSWER'),
       ('Capital of Vietnam?', 'SINGLE_OPTION_ANSWER'),
       ('Summer months?', 'MULTI_OPTION_ANSWER');

insert into survey_questions (survey_id, questions_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (3, 8),
       (3, 9);

insert into fill_survey (begin_date, description, end_date, interviewer_id, source_survey_id)
values ('2022-01-01', 'First filled survey', '2022-12-31', 1, 1),
       ('2022-01-01', 'Second filled survey', '2022-12-31', 1, 2),
       ('2022-01-01', 'Third filled survey', '2022-12-31', 1, 3);

insert into answer (question, type)
values ('How many hours in a day?', 'SIMPLE_STRING_ANSWER'),
       ('Capital of Japan?', 'SINGLE_OPTION_ANSWER'),
       ('Summer sports?', 'MULTI_OPTION_ANSWER'),

       ('How many time zones are there in Russia?', 'SIMPLE_STRING_ANSWER'),
       ('Capital of China?', 'SINGLE_OPTION_ANSWER'),
       ('Winter sports?', 'MULTI_OPTION_ANSWER'),

       ('How many fingers are on the hand?', 'SIMPLE_STRING_ANSWER'),
       ('Capital of Vietnam?', 'SINGLE_OPTION_ANSWER'),
       ('Summer months?', 'MULTI_OPTION_ANSWER');

insert into answer_answer_options (answer_id, answer_options)
values (1, '24 hours'),
       (2, 'Tokio'),
       (3, 'Badminton'),
       (3, 'Volleyball'),
       (4, '11 time zones'),
       (5, 'Beijing'),
       (6, 'Ski race'),
       (6, 'Biathlon'),
       (7, '5 fingers'),
       (8, 'Hanoi'),
       (9, 'December');



insert into question_answer_options (question_id, answer_options)
values (2, 'Tokio'),
       (2, 'Paris'),
       (2, 'London'),
       (3, 'Badminton'),
       (3, 'Volleyball'),
       (3, 'Ski race'),
       (3, 'Biathlon'),
       (5, 'Moscow'),
       (5, 'Beijing'),
       (5, 'Seoul'),
       (6, 'Badminton'),
       (6, 'Volleyball'),
       (6, 'Ski race'),
       (6, 'Biathlon'),
       (8, 'Oslo'),
       (8, 'Stockholm'),
       (8, 'Hanoi'),
       (9, 'May'),
       (9, 'December'),
       (9, 'August'),
       (9, 'July');



insert into fill_survey_answers (filled_survey_id, answers_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (3, 8),
       (3, 9);
