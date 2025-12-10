DROP VIEW IF EXISTS login_authorities;


DROP TABLE IF EXISTS logins_roles;
DROP TABLE IF EXISTS logins;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS promos;
DROP TABLE IF EXISTS communities;
DROP TABLE IF EXISTS pages;
DROP TABLE IF EXISTS reports;

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
    font_options varchar(92) NOT NULL DEFAULT '{"serif":false,"italic":true,"weight":450,"width":85}',
    font_colour varchar(10) NOT NULL DEFAULT '#e9b36cff',
    main_text varchar(512),
    links_text varchar(256),
    info_text varchar(256),
    promo_text varchar(256),
    approved BOOLEAN NOT NULL DEFAULT FALSE,
    secret_hash varchar(255),
    created_date TIMESTAMP NULL DEFAULT NULL -- Added created_date column & Allow NULL, no default
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

CREATE TABLE logins_roles (
  id         bigint      auto_increment primary key,
  login_name varchar(32) not null,
  role_id    int(11)     not null
)engine=InnoDB;

CREATE VIEW login_authorities AS
  SELECT u.login_name AS login_name,
         CONCAT('ROLE_', r.name) AS authority
    FROM logins u
         INNER JOIN logins_roles ur ON u.login_name = ur.login_name
         INNER JOIN roles r        ON ur.role_id   = r.role_id;

-- Street data

create table if not exists streets (
  id          bigint auto_increment primary key,
  name        varchar(64),
  address     varchar(126) UNIQUE, -- (block address size - 2)  as doesnt need to cover the last potential address of blocks
                                   -- NULL address is taken to mean the main (root) street
  description varchar(512)
) engine=InnoDB;

--Reports table
CREATE TABLE IF NOT EXISTS reports (
    id bigint auto_increment primary key,
    blockID int,
    block_name varchar(128),
    reason varchar(512),
    report_date timestamp default current_timestamp
) engine=InnoDB;

-- Promo Table
CREATE TABLE IF NOT EXISTS promos (
    id bigint auto_increment primary key,
    block_id bigint not null,
    promo_position int not null,
    CONSTRAINT unique_position UNIQUE (promo_position),
    FOREIGN KEY (block_id) REFERENCES communities(id) ON DELETE CASCADE
) engine=InnoDB;
SET FOREIGN_KEY_CHECKS = 1;
