create table if not exists users(
    id int auto_increment not null,
    name varchar(255) not null,
    primary key(id)
    );

create table if not exists files(
    id int auto_increment not null,
    location varchar(255) not null,
    size int not null,
    primary key(id)
    );

create table if not exists  events(
    id int auto_increment not null,
    description varchar(255) not null,
    date date not null,
    user_id int not null,
    file_id int not null,
    constraint users_events foreign key (user_id) references users (id) on delete cascade,
    constraint files_events foreign key (file_id) references files (id) on delete cascade,
    primary key (id)
    );

insert into files(location, size) values ("location 1", 200);
insert into files(location, size) values ("location 2", 400);
insert into files(location, size) values ("location 3", 8000);
insert into files(location, size) values ("location 4", 10000);

insert into users (name) values("Name 1");
insert into users (name) values("Name 2");

insert into events (description, date, user_id, file_id) values ("creation", "2022-06-01", 1, 1);
insert into events (description, date, user_id, file_id) values ("creation", "2022-06-02", 1, 11);
insert into events (description, date, user_id, file_id) values ("creation", "2022-06-03", 1, 21);
insert into events (description, date, user_id, file_id) values ("creation", "2022-06-04", 11, 31);