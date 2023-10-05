drop database if exists kade;
create database kade;
use kade;
create table customer(
    id varchar(50) primary key,
    name varchar(50) not null,
    address text not null,
    tel varchar(50) not null
    );

create table item(
    itemCode varchar(50) primary key,
    description varchar(50) not null,
    unitPrize decimal(6,2) not null,
    qtyOnHand int not null
);

create table supplier(
    supId varchar(15) primary key,
    name varchar(50) not null,
    shop varchar(20) not null,
    tel varchar(15) not null
);