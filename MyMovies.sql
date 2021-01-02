create database MYMOVIES;
use MYMOVIES;

drop table if exists movies;
drop table if exists sessions;
drop table if exists cinema_admin;

create table cinema_admin
(
    admin_id int(20) primary key auto_increment,
    cinema_name varchar(30) not null ,
    username varchar(20) not null ,
    password varchar(20) not null ,
    city varchar(20) not null ,
    address varchar(20) not null
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table sessions
(
    session_id int(20) primary key auto_increment,
    day varchar(10) not null ,
    time timestamp not null,
    movie_id int(20) not null
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table movies
(
    movie_id int(20) primary key auto_increment,
    title    varchar(20) not null,
    duration int(10)     not null,
    language varchar(20) not null ,
    subtitle varchar(20) not null ,
    director varchar(50) not null ,
    actors varchar(255) not null ,
    min_age int(5) not null ,
    start_date date not null ,
    end_date date not null ,
    admin_id int(20) not null
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

alter table sessions
    add constraint fk_session_movie foreign key sessions (movie_id) references movies (movie_id);

alter table movies add constraint  fk_movie_cinema foreign key movies (admin_id) references cinema_admin (admin_id);
