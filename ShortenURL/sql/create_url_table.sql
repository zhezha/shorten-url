create table url
(
id int unsigned not null auto_increment,
hash_value varchar(255) not null,
long_url varchar(255) not null,
primary key (id)
)