package org.parsingbot.commons.service;

import org.parsingbot.commons.entity.Vacancy;

import java.util.List;

public interface VacancyService {

    /**
     * Метод сохраняет список вакансий в БД
     *
     * @param vacancies список вакансий
     */
    void save(List<Vacancy> vacancies);

    /**
     * Метод сохраняет вакансию в БД
     *
     * @param vacancy объект вакансии
     */
    void save(Vacancy vacancy);
}
