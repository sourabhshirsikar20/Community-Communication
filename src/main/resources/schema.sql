drop table if exists communities;

create table if not exists communities (
id bigint auto_increment primary key,
address varchar(128)
) engine=InnoDB;