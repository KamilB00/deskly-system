create table if not exists locations
(
    location_id   uuid      not null,
    city          varchar   not null,
    postal_code   varchar   not null,
    street        varchar   not null,
    building_name varchar   not null,
    flat_number   varchar   not null,
    email         varchar   not null,
    name          varchar   not null,
    phone_number  varchar   not null,
    photos        jsonb     not null,
    version       bigserial not null,
    primary key (location_id)
);

create table if not exists resources
(
    resource_id uuid      not null,
    attributes  jsonb     not null,
    name        varchar   not null,
    photos      jsonb     not null,
    type        varchar   not null,
    version     bigserial not null,
    primary key (resource_id)
);

create table if not exists resource_location_assignments
(
    location_id uuid not null,
    resource_id uuid not null,
    primary key (location_id, resource_id)
)
