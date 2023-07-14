package org.parsingbot.service;

import org.parsingbot.entity.Vacancy;

import java.util.List;
import java.util.function.Predicate;

/**
 * Интерфейс взаимодействия парсера и репозитория
 */
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
     * Метод достает вакансии из БД согласно фильтру
     *
     * @param filter функция фильтра
     * @return список вакансий, прошедших фильтрацию
     */
    List<Vacancy> getVacancies(Predicate<Vacancy> filter);

    List<Vacancy> findAllVacancies();
}
