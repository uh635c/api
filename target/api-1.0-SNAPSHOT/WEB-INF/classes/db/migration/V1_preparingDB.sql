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