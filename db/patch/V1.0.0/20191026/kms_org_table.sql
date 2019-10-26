create table kms_org
(
    org_id varchar(16) not null,
    org_name varchar(200) null,
    org_code varchar(200) null,
    parent_id varchar(16) null,
    leaf tinyint default 0 null,
    constraint kms_org_pk
        primary key (org_id)
);

