-- -- ==============================================================
-- -- Flyway Migration -
-- -- ==============================================================
-- --
-- -- Creates initial PostgreSQL database tables.
-- --
-- -- ==============================================================
--
-- -- Create tables
--

create table games
(
    id varchar(255) not null primary key,
    game_slug varchar(255),
    game_name varchar(255),
    user_id varchar(255),
    platforms text[],
    game_status varchar(255),
    game_time varchar(300)
);
