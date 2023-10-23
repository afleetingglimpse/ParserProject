package org.parsingbot.parser.service.impl;

import org.parsingbot.commons.entity.Vacancy;
import org.parsingbot.parser.service.VacancyFilter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HhVacancyFilter implements VacancyFilter {

    @Override
    public List<Vacancy> getFilteredVacancy(List<Vacancy> vacancies, Predicate<Vacancy> filter) {
        return vacancies.stream().filter(filter).collect(Collectors.toList());
    }
}