create table kms_key_collection
(
    collection_id varchar(36) not null,
    collection_name varchar(120) null,
    org_id varchar(36) null,
    constraint kms_key_collection_pk
        primary key (collection_id)
);

create table kms_collection_key
(
    key_id varchar(32) not null,
    connection_id varchar(32) not null,
    constraint kms_connection_key_pk
        primary key (key_id, connection_id)
);



