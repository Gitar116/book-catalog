create table if not exists books
(
    id          number not null,
    title       varchar2(150) not null,
    author      varchar2(150) not null,
    description varchar2(150),
    constraint book_pk primary key (id)
);

insert into books (id, title, author, description)
values (1, 'Crime and Punishment', 'F. Dostoevsky', null);
insert into books (id, title, author, description)
values (2, 'Anna Karenina', 'L. Tolstoy', null);
insert into books (id, title, author, description)
values (3, 'The Brothers Karamazov', 'F. Dostoevsky', null);
insert into books (id, title, author, description)
values (4, 'War and Peace', 'L. Tolstoy', null);
insert into books (id, title, author, description)
values (5, 'Dead Souls', 'N. Gogol', null);