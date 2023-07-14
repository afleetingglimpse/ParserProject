package org.parsingbot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.Parser;
import org.parsingbot.service.VacancyBrowser;
import org.parsingbot.service.VacancyService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Slf4j
public class HhParser implements Parser {

    private final static String PARSING_ERROR =
            "Parsing error with params vacancyToSearch = {}, numberOfVacancies = {} occurred";

    private final VacancyService vacancyService;
    private final VacancyBrowser vacancyBrowser;

    @Override
    public List<Vacancy> parse(String vacancyToSearch, int numberOfVacancies, Predicate<Vacancy> filter) {
        try {
            List<Vacancy> vacanciesFiltered = vacancyBrowser.browse(
                    vacancyToSearch,
                    numberOfVacancies,
                    filter);
            vacancyService.save(vacanciesFiltered);
            return vacanciesFiltered;

        } catch (IOException e) {
            log.warn(PARSING_ERROR, vacancyToSearch, numberOfVacancies);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Vacancy> parse(String vacancyToSearch, int numberOfVacancies) {
        return parse(vacancyToSearch, numberOfVacancies, null);
    }

    @Override
    public List<Vacancy> getVacancies(Predicate<Vacancy> filter) {
        return vacancyService.getVacancies(filter);
    }
}
