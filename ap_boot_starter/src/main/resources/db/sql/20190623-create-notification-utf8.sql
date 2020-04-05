-- пользователь --
create sequence sq_notification minvalue 1 maxvalue 9223372036854775807 increment by 1 start with 1;
create table "notification" (
  id        bigint                      constraint notification__id not null,
  user_id   varchar(256)                constraint notification__user_id not null,
  title     varchar(100)                constraint notification_title not null,
  message   varchar(500)                constraint notification_message not null,
  createddt timestamp without time zone constraint notification__createddt not null,
  constraint pk_notification primary key (id)
);
comment on table "notification" is 'Уведомление';
comment on column "notification".user_id is 'Идентификатор пользователя';
comment on column "notification".title is 'Заголовок уведомления';
comment on column "notification".message is 'Текст уведомления';
comment on column "notification".createddt is 'Дата создания уведомления';