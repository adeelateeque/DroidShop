create table category (id bigint not null auto_increment, createdAt datetime, name varchar(30) not null, status integer, statusCode varchar(255), updatedAt datetime, primary key (id))
create table product (id bigint not null auto_increment, createdAt datetime, name varchar(30) not null, status integer, statusCode varchar(255), updatedAt datetime, primary key (id))
