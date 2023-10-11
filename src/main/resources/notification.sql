--liquibase formatted sql

--changeset laserova:1

create table tele_bot(
    id bigserial primary key,
    chat_id bigint not null,
    date_time timestamp not null,
    notification text not null,
    sent boolean default false
);