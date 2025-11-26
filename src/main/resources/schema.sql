create database if not exists communities;
use communities;

drop table if exists communities;
drop table if exists logins;
drop table if exists roles;
drop table if exists login_roles;

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
<<<<<<< src/main/resources/schema.sql

create table if not exists logins (
  login_name varchar(32)  not null primary key,
  password varchar(512) not null,
  enabled  boolean      not null
) engine=InnoDB;

create table if not exists roles (
  role_id int(11)     not null auto_increment primary key,
  name    varchar(32) not null
) engine=InnoDB;

create table if not exists logins_roles (
  id       bigint      auto_increment primary key,
  login_name varchar(32) not null,
  role_id  int(11)     not null
) engine=InnoDB;

create view if not exists login_authorities as
  select u.login_name as login_name, CONCAT("ROLE_", r.name) as authority
    from logins u
      inner join logins_roles ur on u.login_name = ur.login_name
      inner join roles r on ur.role_id = r.role_id;
