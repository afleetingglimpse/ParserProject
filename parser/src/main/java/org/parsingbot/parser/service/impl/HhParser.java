package org.parsingbot.parser.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.commons.entity.Vacancy;
import org.parsingbot.parser.service.Parser;
import org.parsingbot.parser.service.VacancyBrowser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Slf4j
public class HhParser implements Parser {

    private final static String PARSING_ERROR =
            "Parsing error with params vacancyToSearch = {}, numberOfVacancies = {} occurred";

    private final VacancyBrowser vacancyBrowser;

    @Override
    public List<Vacancy> parse(String vacancyToSearch, int numberOfVacancies, Predicate<Vacancy> filter) {
        try {
            return vacancyBrowser.browse(
                    vacancyToSearch,
                    numberOfVacancies,
                    filter);

        } catch (IOException e) {
            log.warn(PARSING_ERROR, vacancyToSearch, numberOfVacancies);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Vacancy> parse(String vacancyToSearch, int numberOfVacancies) {
        return parse(vacancyToSearch, numberOfVacancies, null);
    }
}
