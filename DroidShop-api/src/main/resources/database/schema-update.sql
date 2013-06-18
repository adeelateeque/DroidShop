create table creditcard (id bigint not null auto_increment, cardHolderName varchar(255), expiryMonth tinyblob, expiryYear tinyblob, number varchar(255) unique, primary key (id))
alter table customer_order add column orderedDate datetime
alter table customer_order add column status varchar(255)
create table customer_order_product (customer_order_id bigint not null, products_id bigint not null, primary key (customer_order_id, products_id), unique (products_id))
create table payment (DTYPE varchar(31) not null, id bigint not null auto_increment, paymentDate datetime, order_id bigint, creditCard_id bigint, primary key (id))
alter table product add column currency varchar(255)
alter table product add column value decimal(19,2)
alter table product add column quantity integer not null
create table product_images (Product_id bigint not null, images varchar(255))
alter table customer_order_product add index FK507D025DB36979A3 (products_id), add constraint FK507D025DB36979A3 foreign key (products_id) references product (id)
alter table customer_order_product add index FK507D025D1C427379 (customer_order_id), add constraint FK507D025D1C427379 foreign key (customer_order_id) references customer_order (id)
alter table payment add index FKD11C3206DF0FC838 (order_id), add constraint FKD11C3206DF0FC838 foreign key (order_id) references customer_order (id)
alter table payment add index FKD11C3206DE9FAC84 (creditCard_id), add constraint FKD11C3206DE9FAC84 foreign key (creditCard_id) references creditcard (id)
alter table product_images add index FKEF9817C874C0CE38 (Product_id), add constraint FKEF9817C874C0CE38 foreign key (Product_id) references product (id)
