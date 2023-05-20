create database jiaming default character set utf8mb4 collate utf8mb4_general_ci;

use jiaming;

create table sys_tenant
(
    id            bigint unsigned not null primary key auto_increment comment '租户id',
    name          varchar(30)     not null comment '租户名',
    user_id       bigint unsigned          default null comment '用户id',
    user_name     varchar(30)     not null comment '联系人',
    mobile        varchar(30)              default null comment '联系方式',
    status        tinyint         not null default 1 comment '租户状态 0:停用 1:正常',
    domain        varchar(256)             default null comment '绑定的域名',
    package_id    bigint          not null comment '租户套餐id',
    expire_time   datetime        not null comment '过期时间',
    account_count int             not null comment '账号配额',
    creator       varchar(64)     not null comment '创建者',
    updater       varchar(64)              default null comment '更新者',
    create_time   datetime        not null default current_timestamp comment '创建时间',
    update_time   datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted       tinyint                  default 0 comment '是否删除 0:未删除 1:删除'
) engine InnoDB comment '租户表';

create table sys_tenant_package
(
    id          bigint unsigned not null primary key auto_increment comment '租户套餐id',
    name        varchar(30)     not null comment '套餐名',
    status      tinyint         not null comment '租户状态 0:停用 1:正常',
    remark      varchar(256)             default null comment '备注',
    menu_ids    varchar(2048)   not null comment '关联的菜单编号',
    creator     varchar(64)     not null comment '创建者',
    updater     varchar(64)     not null comment '更新者',
    create_time datetime        not null default current_timestamp comment '创建时间',
    update_time datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted     tinyint                  default 0 comment '是否删除 0:未删除 1:删除'
) engine InnoDB comment '租户套餐表';

create table sys_user
(
    # fixme 修改为不自增，用分布式id生成
    id          bigint unsigned not null primary key auto_increment comment '系统用户id',
    username    varchar(30)     not null comment '用户名',
    password    varchar(100)    not null comment '密码',
    nickname    varchar(30)     not null comment '用户昵称',
    remark      varchar(500)             default null comment '备注',
    dept_id     bigint                   default null comment '部门id',
    post_ids    varchar(255)             default null comment '岗位id数组，逗号分隔',
    email       varchar(50)              default null comment '用户邮箱',
    mobile      varchar(11)              default null comment '联系方式',
    sex         tinyint         null comment '用户性别 0:女 1:男',
    avatar      varchar(512)             default null comment '用户头像地址',
    status      tinyint         not null default 1 comment '账号状态 0:停用 1:正常',
    login_ip    varchar(50)              default null comment '登录ip',
    login_date  datetime                 default null comment '登录时间',
    creator     varchar(64)              default null comment '创建者',
    updater     varchar(64)              default null comment '更新者',
    create_time datetime        not null default current_timestamp comment '创建时间',
    update_time datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted     tinyint         not null default 0 comment '是否删除 0:未删除 1:删除',
    tenant_id   bigint          not null default 0 comment '租户id，如果为0则为平台用户'
) engine InnoDB comment '系统用户';

create table sys_role
(
    id          bigint       not null primary key auto_increment comment '角色id',
    name        varchar(30)  not null comment '角色名称',
    code        varchar(100) not null comment '角色权限标识',
    status      tinyint      not null comment '角色状态 0:停用 1:启用',
    remark      varchar(500)          default null comment '备注',
    creator     varchar(64)           default null comment '创建者',
    updater     varchar(64)           default null comment '更新者',
    create_time datetime     not null default current_timestamp comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted     tinyint      not null default 0 comment '是否删除 0:未删除 1:删除',
    tenant_id   bigint       not null default 0 comment '租户编号'
) engine InnoDB comment '系统角色';

create table sys_user_role
(
    id          bigint unsigned not null primary key auto_increment comment '用户与角色关联id',
    user_id     bigint          not null comment '用户id',
    role_id     bigint          not null comment '角色id',
    creator     varchar(64)              default null comment '创建者',
    updater     varchar(64)              default null comment '更新者',
    create_time datetime        not null default current_timestamp comment '创建时间',
    update_time datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted     tinyint         not null default 0 comment '是否删除 0:未删除 1:删除',
    tenant_id   bigint unsigned not null default 0 comment '租户id'
) engine InnoDB comment '用户与角色关联表';

create table sys_menu
(
    id          bigint unsigned not null primary key auto_increment comment '菜单id',
    name        varchar(32)     not null comment '菜单名称',
    parent_id   bigint unsigned not null default 0 comment '父菜单id',
    path        varchar(255)    null comment '路径',
    component   varchar(255)    null comment '1:Layout 不会跳转界面 2:demo/demo 会跳转到该界面',
    redirect    varchar(255)    null comment '当设置noRedirect时，该路由在面包屑导航中不能被点击',
    always_show tinyint         null comment '是否一直现实根路由',
    hidden      tinyint         null comment '当设置未true时，该路由不会出现侧边栏，用于一些编辑界面',
    title       varchar(32)     not null comment '设置路由在侧边栏和面包屑中展示的名字',
    icon        varchar(32)     null comment '设置该路由的图标，支持 svg-class，也支持 el-icon-x',
    no_cache    tinyint         null comment '如果设置为true，则不会被缓存，默认false',
    breadcrumb  tinyint         null comment '如果设置为false，则不会在面包屑中展示（默认true）',
    affix       tinyint         null comment '如果设置为true，则会固定在 tags-view 中（默认false）',
    active_menu varchar(255)    null comment '当路由设置该属性，则会高亮相对应的侧边栏',
    creator     varchar(64)              default null comment '创建者',
    updater     varchar(64)              default null comment '更新者',
    create_time datetime        not null default current_timestamp comment '创建时间',
    update_time datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted     tinyint         not null default 0 comment '是否删除 0:未删除 1:删除'
) engine InnoDB comment '系统菜单';

create table sys_role_menu
(
    id          bigint unsigned not null primary key auto_increment comment '角色菜单关联id',
    role_id     bigint          not null comment '角色id',
    menu_id     bigint          not null comment '菜单id',
    creator     varchar(64)              default null comment '创建者',
    updater     varchar(64)              default null comment '更新者',
    create_time datetime        not null default current_timestamp comment '创建时间',
    update_time datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    tenant_id   bigint          not null default 0 comment '租户id'
) engine InnoDB comment '角色菜单关联表';

create table sys_dept
(
    id             bigint unsigned not null primary key auto_increment comment '部门id',
    name           varchar(30)     not null comment '部门名称',
    parent_id      bigint          not null comment '父部门id',
    leader_user_id bigint                   default null comment '负责人',
    phone          varchar(11)              default null comment '联系电话',
    email          varchar(50)              default null comment '邮箱',
    status         tinyint         not null default 1 comment '部门状态 0:停用 1:正常',
    creator        varchar(64)              default null comment '创建者',
    updater        varchar(64)              default null comment '更新者',
    create_time    datetime        not null default current_timestamp comment '创建时间',
    update_time    datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted        tinyint         not null default 0 comment '是否删除 0:未删除 1:删除',
    tenant_id      bigint          not null default 0 comment '租户id'
) engine InnoDB comment '部门表';

create table sys_post
(
    id          bigint unsigned not null primary key auto_increment comment '岗位id',
    code        varchar(64)     not null comment '岗位标识',
    name        varchar(50)     not null comment '岗位名称',
    status      tinyint         not null default 1 comment '状态 0:停用 1:正常',
    remark      varchar(500)             default null comment '备注',
    creator     varchar(64)              default null comment '创建者',
    updater     varchar(64)              default null comment '更新者',
    create_time datetime        not null default current_timestamp comment '创建时间',
    update_time datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted     tinyint         not null default 0 comment '是否删除 0:未删除 1:删除',
    tenant_id   bigint          not null default 0 comment '租户id'
) engine InnoDB comment '岗位表';

create table sys_user_group
(
    id          bigint unsigned not null primary key auto_increment comment '用户组id',
    name        varchar(255)    not null comment '用户组名',
    remark      varchar(512)             default null comment '描述',
    status      tinyint         not null default 1 comment '状态 0:停用 1:启用',
    creator     varchar(64)              default null comment '创建者',
    updater     varchar(64)              default null comment '更新者',
    create_time datetime        not null default current_timestamp comment '创建时间',
    update_time datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted     tinyint         not null default 0 comment '是否删除 0:未删除 1:删除',
    tenant_id   bigint          not null default 0 comment '租户id'
) engine InnoDB comment '用户组';

create table sys_group_user
(
    id            bigint unsigned not null primary key auto_increment comment '用户与用户组关联id',
    user_group_id bigint          not null comment '用户组id',
    user_id       bigint          not null comment '用户id',
    creator       varchar(64)              default null comment '创建者',
    updater       varchar(64)              default null comment '更新者',
    create_time   datetime        not null default current_timestamp comment '创建时间',
    update_time   datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    tenant_id     bigint          not null default 0 comment '租户id'
) engine InnoDB comment '用户与用户组关联表';

create table sys_group_role
(
    id            bigint unsigned not null primary key auto_increment comment '用户组与角色关联id',
    user_group_id bigint          not null comment '用户组id',
    role_id       bigint          not null comment '角色id',
    creator       varchar(64)              default null comment '创建者',
    updater       varchar(64)              default null comment '更新者',
    create_time   datetime        not null default current_timestamp comment '创建时间',
    update_time   datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    tenant_id     bigint          not null default 0 comment '租户id'
) engine InnoDB comment '用户组与角色关联表';
