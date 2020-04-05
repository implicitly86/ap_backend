
-- точки достатавки --
create sequence sq_deliverypoint minvalue 1 maxvalue 9223372036854775807 increment by 1 start with 1;
create table "deliverypoint" (
  id                  bigint        constraint deliverypoint__id not null,
  name                varchar(256)  constraint deliverypoint__name not null,
  address             varchar(256)  constraint deliverypoint__address not null,
  postcode            varchar(256)  constraint deliverypoint__postcode not null,
  phone               varchar(256),
  email               varchar(256),
  constraint pk_deliverypoint primary key (id)
);
comment on table "deliverypoint" is 'Точка доставки';
comment on column "deliverypoint".id is 'Идентификатор точки доставки';
comment on column "deliverypoint".name is 'Название точки доставки';
comment on column "deliverypoint".address is 'Адрес точки доставки';
comment on column "deliverypoint".postcode is 'Почтовый индекс точки доставки';
comment on column "deliverypoint".phone is 'Телефон точки доставки';
comment on column "deliverypoint".email is 'Почта точки доставки';

-- клиент --
create sequence sq_customer minvalue 1 maxvalue 9223372036854775807 increment by 1 start with 1;
create table "customer" (
  id                  bigint        constraint customer__id not null,
  name                varchar(256)  constraint customer__name not null,
  first_name          varchar(256),
  last_name           varchar(256),
  middle_name         varchar(256),
  address             varchar(256),
  phone               varchar(256),
  email               varchar(256),
  type                numeric(1)    constraint customer__type not null,
  createddt           date          constraint customer__createddt not null,
  createdby_id        bigint        constraint customer__createdby_id not null,
  constraint pk_customer primary key (id),
  constraint fk_customer__createdby foreign key (createdby_id) references "user" (id) on delete cascade
);
comment on table "customer" is 'Клиент';
comment on column "customer".id is 'Идентификатор клиента';
comment on column "customer".name is 'Название клиента';
comment on column "customer".first_name is 'Имя клиента';
comment on column "customer".last_name is 'Фамилия клиента';
comment on column "customer".middle_name is 'Отчество клиента';
comment on column "customer".address is 'Адрес клиента';
comment on column "customer".phone is 'Телефон клиента';
comment on column "customer".email is 'Почта клиента';
comment on column "customer".type is 'Тип клиента: 0 - физическое, 1 - юридическое лицо';
comment on column "customer".createddt is 'Дата создания';
comment on column "customer".createdby_id is 'Идентификатор пользователя, создавшего запись';

-- заказ --
create sequence sq_order minvalue 1 maxvalue 9223372036854775807 increment by 1 start with 1;
create table "order" (
  id                  bigint        constraint order__id not null,
  barcode             varchar(256)  constraint order__barcode not null,
  customer_id         bigint        constraint order__customer_id not null,
  from_point          bigint        constraint order__customer_from not null,
  to_point            bigint        constraint order__customer_to not null,
  createddt           date          constraint order__createddt not null,
  createdby_id        bigint        constraint order__createdby_id not null,
  modifieddt          date          constraint order__modifieddt not null,
  modifiedby_id       bigint        constraint order__modifieddt_id not null,
  constraint pk_order primary key (id),
  constraint fk_order__customer foreign key (customer_id) references "customer" (id) on delete cascade,
  constraint fk_order__from foreign key (from_point) references "deliverypoint" (id) on delete cascade,
  constraint fk_order__to foreign key (to_point) references "deliverypoint" (id) on delete cascade,
  constraint fk_order__createdby foreign key (createdby_id) references "user" (id) on delete cascade,
  constraint fk_order__modifiedby foreign key (modifiedby_id) references "user" (id) on delete cascade
);
comment on table "order" is 'Заказ';
comment on column "order".id is 'Идентификатор заказа';
comment on column "order".barcode is 'Штрих-код заказа';
comment on column "order".customer_id is 'Идентификатор клиента';
comment on column "order".from_point is 'Адрес точки приема заказа';
comment on column "order".to_point is 'Адрес точки доставки заказа';
comment on column "order".createddt is 'Дата создания заказа';
comment on column "order".createdby_id is 'Идентификатор пользователя, создавшего запись';
comment on column "order".modifieddt is 'Дата изменения записи';
comment on column "order".modifiedby_id is 'Идентификатор пользователя, изменившего запись';
