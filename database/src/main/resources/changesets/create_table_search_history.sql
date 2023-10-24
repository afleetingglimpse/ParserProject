create table if not exists search_history (
    id integer PRIMARY KEY,
    user_id INTEGER REFERENCES users (id),
    vacancy_name text default 'java',
    number_of_vacancies integer default 5,
    keywords text default ''
);