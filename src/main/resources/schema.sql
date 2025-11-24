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
) engine=InnoDB;