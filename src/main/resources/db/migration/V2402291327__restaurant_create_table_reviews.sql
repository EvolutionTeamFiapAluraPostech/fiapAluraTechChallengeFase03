create table if not exists "restaurant_management"."reviews"
(
    "id"            uuid                        not null default gen_random_uuid() primary key,
    "deleted"       boolean                     not null default false,
    "version"       bigint                      not null,
    "created_at"    timestamp without time zone null,
    "created_by"    varchar(255)                null,
    "updated_at"    timestamp without time zone null,
    "updated_by"    varchar(255)                null,
    "description"   varchar(500)                not null,
    "score"         integer                     not null,
    "restaurant_id" uuid                        not null,
    "user_id"       uuid                        not null
);

create index if not exists review_restaurant_id_idx ON restaurant_management.reviews using btree (restaurant_id);
create index if not exists review_user_id_idx ON restaurant_management.reviews using btree (user_id);