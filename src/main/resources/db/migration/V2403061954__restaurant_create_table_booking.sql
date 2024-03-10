create table if not exists "restaurant_management"."booking"
(
    "id"            uuid                        not null default gen_random_uuid() primary key,
    "deleted"       boolean                     not null default false,
    "version"       bigint                      not null,
    "created_at"    timestamp without time zone null,
    "created_by"    varchar(255)                null,
    "updated_at"    timestamp without time zone null,
    "updated_by"    varchar(255)                null,
    "description"   varchar(500)                null,
    "restaurant_id" uuid                        not null,
    "user_id"       uuid                        not null,
    "booking_date"  timestamp without time zone not null,
    "booking_state" varchar(10)                 not null
);

create index if not exists booking_restaurant_id_idx ON restaurant_management.booking using btree (restaurant_id);
create index if not exists booking_user_id_idx ON restaurant_management.booking using btree (user_id);