create table ee_words
(
    id int identity,
    link VARCHAR(200) not null,
    word VARCHAR(50) not null,
    quantity int not null
)
go

create unique index ee_words_id_uindex
    on ee_words (id)
go

alter table ee_words
    add constraint ee_words_pk
        primary key nonclustered (id)
go
