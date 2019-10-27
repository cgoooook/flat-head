create table if not exists kms_template
(
    template_id varchar(20) not null
        primary key,
    template_name varchar(30) null,
    is_built_in int default 0 null,
    node int null,
    start_date datetime null,
    end_date datetime null,
    key_usages varchar(200) null,
    extend_usages varchar(500) null
);

alter table kms_template
    add status int default 1 null;
