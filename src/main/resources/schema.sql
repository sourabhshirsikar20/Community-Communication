create database if not exists communities;
use communities;

drop table if exists communities;
drop table if exists community_users;
drop table if exists community_roles;
drop table if exists community_security;

create table if not exists communities (
id bigint auto_increment primary key,
address varchar(128),
name varchar(128),
roof_option smallint,
roof_colour varchar(10),
base_option smallint,
base_colour varchar(10),
foreground_option smallint,
foreground_colour varchar(10),
main_text varchar(512),
links_text varchar(256),
info_text varchar(256),
promo_text varchar(256),
approved BIT NOT NULL DEFAULT 0
) engine=InnoDB;

create table if not exists community_users (
  name     varchar(32)  not null primary key,
  password varchar(512) not null,
  enabled  boolean      not null
) engine=InnoDB;

create table if not exists community_roles (
  id    int(11)     not null auto_increment primary key,
  name  varchar(32) not null
) engine=InnoDB;

create table if not exists community_security (
  id    bigint      auto_increment primary key,
  name  varchar(32) not null,
  role  int(11)     not null
) engine=InnoDB;

create view if not exists community_authorities as
select u.name as name, CONCAT("ROLE_", r.name) as authority
from community_users u
         inner join community_roles ur on u.name = ur.name
         inner join roles r on ur.role = r.role;