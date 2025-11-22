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

-- CREATED A TEST DB FOR COMMUNITY INFO

drop table if exists organisationinfo;

create table if not exists organisationinfo (
 Id int primary key auto_increment,
 MainText varchar(256),
 Links varchar(256),
 Info varchar(256),
 Promo varchar(256)
)engine=InnoDB;
