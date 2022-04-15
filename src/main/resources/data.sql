insert into usr (is_active, password, user_name)
values (true, '$2a$04$efl6wLEqVgaa3sM0E9vCxusFELFF1Q3R/Ts/6ZhHsFs1GriBQF1ne', 'admin');

insert into user_roles (user_id, roles)
values (1, 'ADMIN');

insert into item (question, type)
values ('Сколько часов в сутках', 'SIMPLE_STRING'),
       ('Столица Татарстана?', 'SINGLE_OPTION'),
       ('Выберите зимние виды спорта', 'MULTI_OPTION'),
       ('Сколько часовых поясов В России?', 'SIMPLE_STRING'),
       ('Столица Татарстана?', 'SINGLE_OPTION'),
       ('Выберите летние виды спорта', 'MULTI_OPTION');

insert into item_answer (item_id, answer_key, answer)
values (4, '11 часовых поясов', false),
       (5, 'Казань', true),
       (5, 'Набережные Челны', false),
       (5, 'Уфа', false),
       (6, 'Бадминтон', true),
       (6, 'Воллейбол', true),
       (6, 'Лыжные гонки', false),
       (6, 'Биатлон', false);

insert into pull (begin_date, description, end_date, is_active)
values (current_timestamp, 'Не активный опрос', '31-Dec-2022', false),
       (current_timestamp, 'Активный опрос', '31-Dec-2022', true);

insert into pull_items (pull_id, items_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6);

insert into fill_pull (begin_date, description, end_date, interviewer_id, source_pull_id)
values (current_timestamp, 'Первый заполненный опрос', current_timestamp, 1, 1);
--        (current_timestamp, 'Второй заполненный опрос', current_timestamp, 1, 2);

insert into fill_item (question, type)
values ('Сколько часов в сутках?', 'SIMPLE_STRING'),
       ('Столица Татарстана?', 'SINGLE_OPTION'),
       ('Выберите зимние виды спорта?', 'MULTI_OPTION');
--        ('Сколько в России часовых поясов?', 'SIMPLE_STRING'),
--        ('Столица Индии?', 'SINGLE_OPTION'),
--        ('Выберите летние виды спорта?', 'MULTI_OPTION');

insert into fill_item_answer (fill_item_id, answer, answer_key)
values (1, false, '24 часа'),
       (2, true, 'Казань'),
       (2, false, 'Набережные Челны'),
       (2, false, 'Уфа'),
       (3, false, 'Бадминтон'),
       (3, false, 'Воллейбол'),
       (3, true, 'Лыжные гонки'),
       (3, true, 'Биатлон');

--        (4, false, '11 часовых поясов'),
--        (5, true, 'Дели'),
--        (5, false, 'Кабул'),
--        (5, false, 'Калькутта'),
--        (6, true, 'Велоспорт'),
--        (6, true, 'Баскетбол'),
--        (6, false, 'Фристайл'),
--        (6, false, 'Санный спорт');

insert into fill_pull_fill_items (fill_pull_id, fill_items_id)
values (1, 1),
       (1, 2),
       (1, 3);
--        (2, 4),
--        (2, 5),
--        (2, 6);
