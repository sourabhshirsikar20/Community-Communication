DROP VIEW IF EXISTS login_authorities;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS login_roles;
DROP TABLE IF EXISTS logins;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS communities;
DROP TABLE IF EXISTS pages;

CREATE TABLE communities (
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
    approved BIT NOT NULL DEFAULT 0,
    secret_hash varchar(255)
)engine=InnoDB;

CREATE TABLE logins (
  login_name varchar(32)  not null primary key,
  password varchar(512) not null,
  enabled  boolean      not null
)engine=InnoDB;

CREATE TABLE roles (
  role_id int(11)     not null auto_increment primary key,
  name    varchar(32) not null
)engine=InnoDB;

CREATE TABLE login_roles (
  id         bigint      auto_increment primary key,
  login_name varchar(32) not null,
  role_id    int(11)     not null
)engine=InnoDB;

CREATE VIEW login_authorities AS
  SELECT u.login_name AS login_name,
         CONCAT('ROLE_', r.name) AS authority
    FROM logins u
         INNER JOIN login_roles ur ON u.login_name = ur.login_name
         INNER JOIN roles r        ON ur.role_id   = r.role_id;

SET FOREIGN_KEY_CHECKS = 1;