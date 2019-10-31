create table kms_a_key
(
    key_id varchar(32) not null,
    key_name varchar(128) null,
    key_alg varchar(15) null,
    length int null,
    version int default 1 null,
    check_value varchar(20) null,
    key_value varchar(200) null,
    create_by varchar(20) null,
    template_id varchar(36) null,
    org_id varchar(36) null ,
    status int null comment '0 create 1 generate 2 active 3 inactive 4 archive ',
    constraint kms_a_key_pk
        primary key (key_id)
);

create table kms_a_key_history
(
    key_history_id varchar(32) not null ,
    key_id varchar(32) not null,
    key_name varchar(128) null,
    key_alg varchar(15) null,
    length int null,
    version int default 1 null,
    check_value varchar(20) null,
    key_value varchar(200) null,
    create_by varchar(20) null,
    template_id varchar(36) null,
    org_id varchar(36) null ,
    status int null comment '0 create 1 generate 2 active 3 inactive 4 archive ',
    constraint kms_a_key_pk
        primary key (key_history_id)
);

