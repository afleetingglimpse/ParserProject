package org.parsingbot.service;

import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;

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

    List<Vacancy> getVacanciesByIds(List<Integer> idList);

    List<Vacancy> getVacanciesByUserId(Integer userId);

    List<Vacancy> getVacanciesByUser(User user);

}
