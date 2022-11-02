create table IF NOT EXISTS m_employee
(
    id int auto_increment,
    name varchar(50) not null,
    name_kana varchar(50) not null,
    status varchar(50) not null,
    telephone_number varchar(50),
    mail_address varchar(50),
    entering_date date not null,
    primary key (id)
);