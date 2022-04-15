insert into usr (is_active, password, user_name)
values (true, '$2a$04$efl6wLEqVgaa3sM0E9vCxusFELFF1Q3R/Ts/6ZhHsFs1GriBQF1ne', 'admin'),
       (true, '$2a$04$3ZNpCyrwPAWfVyiLftSnsuyZtGR/41ytm60W0G9qNw3yehb5O.h1O', 'user');

insert into user_roles (user_id, roles)
values (1, 'ADMIN'),
       (2, 'INTERVIEWED');

insert into item (question, type)
values ('Сколько часов в сутках', 'SIMPLE_STRING'),
       ('Столица Бельгии?', 'SINGLE_OPTION'),
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
