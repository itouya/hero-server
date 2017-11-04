create table hero (
	id int primary key auto_increment,
	name varchar(50) not null,
	account_image varchar(50) default '/assets/images/default.jpeg' not null
);
