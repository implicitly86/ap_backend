
-- смена типа полей идентификаторов --
alter table "user" alter column id type bigint;
alter table "role" alter column id type bigint;
alter table "user_role" alter column id type bigint;
alter table "user_role" alter column user_id type bigint;
alter table "user_role" alter column role_id type bigint;

-- добавление тестового пользователя (логин / пароль - test / test) --
insert into "user" values (nextval('sq_user'), 'test', '$2a$04$jtxLDiWLMxZnk.XPb91/fOWM6mmAubcM9C1sK2wOttxq.R.lP3ZTu');
insert into "role" values (nextval('sq_role'), 'admin');
insert into "user_role" values (nextval('sq_user_role'), currval('sq_user'), currval('sq_role'));
