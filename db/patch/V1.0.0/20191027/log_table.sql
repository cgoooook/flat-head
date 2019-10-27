create table if not exists kms_operate_archive_log
(
    log_id varchar(20) not null
        primary key,
    operate_type varchar(20) null,
    operate_user varchar(30) null,
    operate_time datetime null,
    operator_result int null,
    operate_content varchar(500) null,
    content_hmac varchar(30) null,
    audit_flag int null
);

create table if not exists kms_operate_log
(
    log_id varchar(20) not null
        primary key,
    operate_type varchar(20) null,
    operate_user varchar(30) null,
    operate_time datetime null,
    operator_result int null,
    operate_content varchar(500) null,
    content_hmac varchar(30) null,
    audit_flag int null
);