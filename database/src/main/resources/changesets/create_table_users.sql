create table if not exists users (
    id integer PRIMARY KEY,
    user_name varchar,
    chat_id integer,
    authorisation varchar default 'USER',
    is_subscribed boolean default false,
    next_send_date timestamp default now(),
    next_send_date_delay_seconds integer default 3600,
    state varchar default 'NONE',
    vacancy_name text default '',
    number_of_vacancies integer default 0,
    keywords text default ''
);