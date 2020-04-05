
-- пользователь --
create sequence sq_user minvalue 1 maxvalue 9223372036854775807 increment by 1 start with 1;
create table "user" (
  id                 int           constraint user__id not null,
  name               varchar(256)  constraint user__name not null,
  password           varchar(256)  constraint user__password not null,
  constraint pk_user primary key (id)
);
comment on table "user" is 'Пользователь';
comment on column "user".id is 'Идентификатор пользователя';
comment on column "user".name is 'Имя пользователя';
comment on column "user".password is 'пароль пользователя';

-- роль пользователя --
create sequence sq_role minvalue 1 maxvalue 9223372036854775807 increment by 1 start with 1;
create table "role" (
  id                 int           constraint role__id not null,
  name               varchar(256)  constraint role__name not null,
  constraint pk_role primary key (id)
);
comment on table "role" is 'Роль пользователя';
comment on column "role".id is 'Идентификатор роли';
comment on column "role".name is 'Название роли';

-- связь пользователя с ролью --
create sequence sq_user_role minvalue 1 maxvalue 9223372036854775807 increment by 1 start with 1;
create table "user_role" (
  id                 int           constraint user_role__id not null,
  user_id            int           constraint user_role__user_id not null,
  role_id            int           constraint user_role__role_id not null,
  constraint pk_user_role primary key (id),
  constraint fk_user_role__user foreign key (user_id) references "user" (id) on delete cascade,
  constraint fk_user_role__role foreign key (role_id) references "role" (id) on delete cascade
);
comment on table "user_role" is 'Связь пользователя с ролью';
comment on column "user_role".id is 'Идентификатор связи';
comment on column "user_role".user_id is 'Идентификатор пользователя';
comment on column "user_role".role_id is 'Идентификатор роли';
