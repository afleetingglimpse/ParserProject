package org.parsingbot.service;

import org.parsingbot.entity.Vacancy;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

/**
 * Интерфейс поисковика по вакансиям
 */
public interface VacancyBrowser {

    /**
     * Основная функция поисковика - вызывает поиск по вакансиям и возвращает найденное
     *
     * @param vacancyToSearch   наименование вакансии для поиска
     * @param numberOfVacancies количество вакансий для поиска
     * @param filter            функция фильтрации вакансий
     * @return список найденных вакансий, прошедших фильтрацию
     */
    List<Vacancy> browse(String vacancyToSearch, int numberOfVacancies, Predicate<Vacancy> filter)
            throws IOException;
}
