package org.parsingbot.commons.service;

import org.parsingbot.commons.entity.User;
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

    /**
     * @param vacanciesIdsList список id вакансий
     * @return список вакансий с соответствующими id
     */
    List<Vacancy> getVacanciesByIds(List<Integer> vacanciesIdsList);

    /**
     * @param userId id пользователя
     * @return список вакансий, на которые подписан пользователь
     */
    List<Vacancy> getVacanciesByUserId(Long userId);

    /**
     * @param user объект пользователя
     * @return список вакансий, на которые подписан пользователь
     */
    List<Vacancy> getVacanciesByUser(User user);
}
