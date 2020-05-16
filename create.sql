create sequence hibernate_sequence start with 1 increment by 1;

create table GameResult (
                            id bigint auto_increment not null,
                            created timestamp not null,
                            duration bigint not null,
                            player varchar(255) not null,
                            solved boolean not null,
                            steps integer not null,
                            primary key (id)
)