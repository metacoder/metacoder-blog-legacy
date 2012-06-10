
    alter table User_roles 
        drop 
        foreign key FKEA147569DCD93557

    drop table if exists BlogEntry

    drop table if exists User

    drop table if exists User_roles

    create table BlogEntry (
        id bigint not null auto_increment,
        creationDate timestamp,
        lastUpdateDate timestamp,
        authorName varchar(255),
        content clob,
        title varchar(255),
        primary key (id)
    )

    create table User (
        name varchar(255) not null,
        creationDate timestamp,
        lastUpdateDate timestamp,
        password varchar(128),
        salt binary(16),
        primary key (name)
    )

    create table User_roles (
        User_name varchar(255) not null,
        roles varchar(255)
    )

    alter table User_roles 
        add index FKEA147569DCD93557 (User_name), 
        add constraint FKEA147569DCD93557 
        foreign key (User_name) 
        references User (name)
