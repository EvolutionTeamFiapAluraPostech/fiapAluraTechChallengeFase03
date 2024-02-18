create schema "restaurant_management";

create table if not exists "restaurant_management"."restaurants"
(
    "id"              uuid                        not null default gen_random_uuid() primary key,
    "deleted"         boolean                     not null default false,
    "version"         bigint                      not null,
    "created_at"      timestamp without time zone null,
    "created_by"      varchar(255)                null,
    "updated_at"      timestamp without time zone null,
    "updated_by"      varchar(255)                null,
    "name"            varchar(100)                not null,
    "cnpj"            varchar(14)                 not null,
    "type_of_cuisine" varchar(50)                 not null,
    "latitude"        numeric(10, 6)              not null,
    "longitude"       numeric(10, 6)              not null,
    "street"          varchar(255)                not null,
    "number"          varchar(100)                not null,
    "neighborhood"    varchar(100)                not null,
    "city"            varchar(100)                not null,
    "state"           varchar(2)                  not null,
    "postal_code"     varchar(8)                  not null,
    "open_at"         varchar(5)                  not null,
    "close_at"        varchar(5)                  not null,
    "people_capacity" int                         not null
);

create index if not exists restaurant_cnpj_idx ON restaurant_management.restaurants using btree (cnpj);
create index if not exists restaurant_type_of_cuisine_idx ON restaurant_management.restaurants using btree (type_of_cuisine);