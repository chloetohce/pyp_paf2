drop database if exists mybnb;
create database mybnb;
use mybnb;

create table acc_occupancy(
acc_id char(10) not null,
vacancy int,
constraint pk_acc_id primary key(acc_id));

create table reservations(
resv_id char(8) not null,
name varchar(128), 
email varchar(128),
acc_id char(10),
arrival_date date,
duration int, 
constraint pk_resv_id primary key (resv_id),
constraint fk_acc_id foreign key(acc_id) references acc_occupancy(acc_id));

grant all privileges on mybnb.* to 'chloe'@'%';
flush privileges;
