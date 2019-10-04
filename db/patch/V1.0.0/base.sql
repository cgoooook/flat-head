create table sys_acl_menu
(
    menu_id   varchar(20)       not null
        primary key,
    menu_name varchar(200)      null,
    menu_icon varchar(100)      null,
    parent_id varchar(20)       null,
    menu_url  varchar(200)      null,
    level     int               null,
    weight    int               null,
    disabled  tinyint default 0 null,
    local     varchar(10)       null
);

create table sys_acl_permission
(
    permission_id    int auto_increment
        primary key,
    perm_token       varchar(200) not null,
    perm_description varchar(200) null
);

create table sys_acl_role
(
    role_id          int auto_increment
        primary key,
    role_name        varchar(150) not null,
    role_description varchar(200) null
);

create table sys_acl_role_permission
(
    role_id int not null,
    perm_id int not null,
    constraint sys_acl_role_permission_pk
        unique (role_id, perm_id)
);

create table sys_acl_user
(
    user_id      int auto_increment
        primary key,
    nick_name    varchar(150)  null,
    username     varchar(30)   not null,
    password     varchar(32)   not null,
    pub_key      varchar(500)  null,
    account_type int default 0 null,
    constraint username_UNIQUE
        unique (username)
);

create table sys_acl_user_role
(
    user_id int null,
    role_id int null,
    constraint sys_acl_user_role_pk
        unique (user_id, role_id)
);

