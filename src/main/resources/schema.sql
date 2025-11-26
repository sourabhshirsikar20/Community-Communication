drop table if exists communities;

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
);

drop table if exists pages;

create table if not exists pages(
id bigint primary key auto_increment,
page_name varchar(255) not null unique,
secret_hash varchar(255) not null,
name varchar (128),
main_text TEXT,
links_text TEXT,
info_text TEXT,
promo_text TEXT
)engine=InnoDB;