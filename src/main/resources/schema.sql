create table if not exists studylist (
id varchar(8) primary key,
date varchar(10),
content varchar(256),
time varchar(30),
username varchar(50)
);

alter table studylist add column if not exists username varchar(50);
alter table studylist alter column time varchar(30);

create table if not exists users (
id varchar(8) primary key,
username varchar(50) unique not null,
password varchar(100) not null
);