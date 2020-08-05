create table users
(
    id                varchar(255)         not null,
    first_name        varchar(255)         not null,
    last_name         varchar(255)         not null,
    registration_time date                 null,
    email             varchar(255)         not null,
    password          varchar(255)         not null,
    is_active         tinyint(1) default 0 null,
    constraint users_id_index
        unique (id)
);

alter table users
    add primary key (id);

create table projects
(
    id           varchar(255)                       not null,
    name         varchar(255)                       not null,
    description  varchar(2500)                      null,
    created_at   datetime default CURRENT_TIMESTAMP not null,
    fk_publisher varchar(255)                       null,
    constraint projects_id_uindex
        unique (id),
    constraint fk_publisher
        foreign key (fk_publisher) references users (id)
            on update cascade on delete cascade
);

create table sprints
(
    id            bigint auto_increment,
    fk_project_id varchar(255)                       not null,
    fk_user_id    varchar(255)                       not null,
    created_at    datetime default CURRENT_TIMESTAMP not null,
    dead_line     datetime                           not null,
    constraint sprints_fk_project_id_uindex
        unique (fk_project_id),
    constraint sprints_fk_user_id_uindex
        unique (fk_user_id),
    constraint sprints_id_uindex
        unique (id),
    constraint fk_sprint_project_id
        foreign key (fk_project_id) references projects (id)
            on update cascade on delete cascade,
    constraint fk_sprint_user_id
        foreign key (fk_user_id) references users (id)
            on update cascade on delete cascade
);

alter table sprints
    add primary key (id);

create table tasks
(
    id                  bigint auto_increment
        primary key,
    name                varchar(255)  not null,
    description         varchar(2500) null,
    fk_creator_user_id  varchar(255)  not null,
    fk_assigned_user_id varchar(255)  not null,
    fk_sprint_id        bigint        not null,
    task_type           varchar(50)   not null,
    constraint fk_asigned_user_id
        foreign key (fk_assigned_user_id) references users (id)
            on update cascade on delete cascade,
    constraint fk_creator_user_id
        foreign key (fk_creator_user_id) references users (id)
            on update cascade on delete cascade,
    constraint fk_sprint_id
        foreign key (fk_sprint_id) references sprints (id)
            on update cascade on delete cascade
);

create table comments
(
    id                 bigint auto_increment,
    fk_user_id         varchar(255)                       not null,
    fk_task_id         bigint                             not null,
    text               varchar(1000)                      not null,
    creation_timestamp datetime default CURRENT_TIMESTAMP not null,
    update_timestamp   datetime                           null on update CURRENT_TIMESTAMP,
    constraint comments_id_uindex
        unique (id),
    constraint fk_task_id
        foreign key (fk_task_id) references tasks (id)
            on update cascade on delete cascade,
    constraint fk_user_id
        foreign key (fk_user_id) references users (id)
            on update cascade on delete cascade
);

alter table comments
    add primary key (id);

create table users_projects
(
    id            bigint auto_increment,
    fk_user_id    varchar(255) not null,
    fk_project_id varchar(255) not null,
    role          varchar(50)  not null,
    constraint users_projects_id_uindex
        unique (id),
    constraint fk_project_id
        foreign key (fk_project_id) references projects (id)
            on update cascade on delete cascade,
    constraint fl_user_id
        foreign key (fk_user_id) references users (id)
            on update cascade on delete cascade
);

alter table users_projects
    add primary key (id);


