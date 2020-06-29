CREATE DATABASE	utnphones;
SHOW ENGINE INNODB STATUS;
use utnphones;
SET GLOBAL time_zone = '-3:00';


create table provinces(
	id int auto_increment not null,
	province_name varchar (50) not null,
    active boolean default 1,
constraint pk_id_province primary key (id)
)ENGINE=InnoDB;

create table cities(
 id int auto_increment not null,
 id_province int not null,
 city_name varchar (50) not null,
 city_short_name varchar (6) not null,
 city_prefix  int(4) unique,
 active boolean default 1,
constraint pk_id_city primary key (id),
constraint fk_id_province foreign key (id_province) references provinces(id)
);

ALTER TABLE cities
add constraint fk_id_province foreign key (id_province) references provinces(id);


ALTER TABLE cities ENGINE=InnODB;

create table users(
	id int auto_increment not null,
	user_name varchar(50) not null,
	user_last_name varchar (50) not null,
	user_dni int not null,
	p_type varchar (30) not null,
	user_alias varchar (30) not null,
	user_password varchar (40) not null,
	active boolean default 1,
	constraint pk_id_person primary key (id)
)ENGINE=InnODB;

create table phone_lines(
	id int auto_increment not null,
	id_client int not null,
	id_city int not null,
	line_number varchar(12) not null,
	line_type varchar(20) not null,
    status varchar(20) not null default "ACTIVE",
	constraint pk_id_line primary key (id),
	constraint fk_id_city foreign key (id_city) references cities(id)
);

ALTER TABLE phone_lines ENGINE=InnODB;

ALTER TABLE phone_lines
add constraint fk_id_city foreign key (id_city) references cities(id);

create table rates(
	id int auto_increment not null,
	id_origin_city int not null,
	id_dest_city int not null,
	price_minute double not null,
	rate_date timestamp not null,
	cost_price double not null,
	constraint pk_id_rate primary key (id),
	constraint fk_id_origin_city foreign key (id_origin_city) references cities(id),
	constraint fk_id_dest_city foreign key (id_dest_city) references cities(id) 
);

ALTER TABLE rates ENGINE=InnODB;
ALTER TABLE rates
add	constraint fk_id_origin_city foreign key (id_origin_city) references cities(id),
add	constraint fk_id_dest_city foreign key (id_dest_city) references cities(id); 

create table bills(
id int auto_increment not null,
 id_line int not null,
 calls_count int,
 total_price double not null,
 cost_price double not null,
 bill_date date not null,
 due_date date not null,
 active boolean default 1,
constraint pk_id_bill primary key(id),
constraint pk_id_line foreign key (id_line) references phone_lines(id)
)ENGINE=InnODB;

create table calls(
	 id int auto_increment not null,
	 id_rate int,
	 id_origin_line int,
	 id_dest_line int,
	 origin_number varchar(12)  not null,
	 dest_number varchar(12)  not null,
	 duration long not null,
	 total_price double,
	 call_date timestamp not null,
	 id_bill int,
	constraint pk_id_calls primary key (id),
	constraint fk_id_rate foreign key (id_rate) references rates (id),
	constraint fk_id_origin_line foreign key(id_origin_line) references phone_lines(id),
	constraint fk_id_dest_line foreign key(id_dest_line) references phone_lines(id),
	constraint fk_id_bill foreign key(id_bill) references bills(id)
)ENGINE=InnODB;
