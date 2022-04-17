insert into usr (is_active, password, user_name)
values (true, '$2a$04$efl6wLEqVgaa3sM0E9vCxusFELFF1Q3R/Ts/6ZhHsFs1GriBQF1ne', 'admin');

insert into user_roles (user_id, roles)
values (1, 'ADMIN');

insert into item (question, type)
values ('How many hours in a day?', 'SIMPLE_STRING'),
       ('Capital of Tatarstan?', 'SINGLE_OPTION'),
       ('Select winter sports?', 'MULTI_OPTION'),
       ('How many time zones are there in Russia?', 'SIMPLE_STRING'),
       ('Capital of Tatarstan?', 'SINGLE_OPTION'),
       ('Choose summer sports', 'MULTI_OPTION');

insert into item_answer (item_id, answer_key, answer)
values (4, '11 time zones', false),
       (5, 'Kazan', true),
       (5, 'Naberezhnye Chelny', false),
       (5, 'Ufa', false),
       (6, 'Badminton', true),
       (6, 'Volleyball', true),
       (6, 'Ski race', false),
       (6, 'Biathlon', false);

insert into pull (begin_date, description, end_date, is_active)
values (current_timestamp, 'Not Active Poll', '31-Dec-2022', false),
       (current_timestamp, 'Active Poll', '31-Dec-2022', true);

insert into pull_items (pull_id, items_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6);

insert into fill_pull (begin_date, description, end_date, interviewer_id, source_pull_id)
values (current_timestamp, 'First filled pull', current_timestamp, 1, 1);

insert into fill_item (question, type)
values ('How many hours in a day?', 'SIMPLE_STRING'),
       ('Capital of Tatarstan?', 'SINGLE_OPTION'),
       ('Choose winter sports?', 'MULTI_OPTION');

insert into fill_item_answer (fill_item_id, answer, answer_key)
values (1, false, '24 hours'),
       (2, true, 'Kazan'),
       (2, false, 'Naberezhnye Chelny'),
       (2, false, 'Ufa'),
       (3, false, 'Badminton'),
       (3, false, 'Volleyball'),
       (3, true, 'Ski race'),
       (3, true, 'Biathlon');

insert into fill_pull_fill_items (fill_pull_id, fill_items_id)
values (1, 1),
       (1, 2),
       (1, 3);
