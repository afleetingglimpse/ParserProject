package org.parsingbot.parser.service;

import org.parsingbot.commons.entity.Vacancy;

import java.util.List;
import java.util.function.Predicate;

/**
 * Интерфейс, применяющий фильтр к найденным вакансиям
 */
public interface VacancyFilter {

    /**
     * Метод применяет фильтр списку вакансий и возвращает список вакансий,
     * прошедших фильтрацию
     *
     * @param vacancies список вакансий
     * @param filter    функция фильтра
     * @return список вакансий, прошедших фильтрацию
     */
    List<Vacancy> getFilteredVacancy(List<Vacancy> vacancies, Predicate<Vacancy> filter);
}
