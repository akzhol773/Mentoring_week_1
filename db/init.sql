drop table if exists person;
create table if not exists person
(
    id   serial primary key,
    name varchar(25) not null,
    age int not null
    )
