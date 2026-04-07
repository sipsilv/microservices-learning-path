create sequence order_event_id_seq start with 1 increment by 50;

create table order_events(
    id bigint default nextval('order_event_id_seq') not null primary key ,
    order_number text not null references orders (order_number),
    event_id text not null unique ,
    payload text not null,
    created_at timestamp,
    updated_at timestamp
);