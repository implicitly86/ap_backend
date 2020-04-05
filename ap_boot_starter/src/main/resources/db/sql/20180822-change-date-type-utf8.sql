
-- смена типа колонок, содержащих дату
alter table "customer" alter column createddt type timestamp without time zone;
alter table "order" alter column createddt type timestamp without time zone;
alter table "order" alter column modifieddt type timestamp without time zone;
