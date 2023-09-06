package org.parsingbot.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.VacancyBrowser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HhParserTest {

    private static final String VACANCY_TO_SEARCH = "vacancyToSearch";
    private static final int NUMBER_OF_VACANCIES = 1;
    private static final Predicate<Vacancy> FILTER = null;

    @Mock
    private VacancyBrowser vacancyBrowser;

    @InjectMocks
    private HhParser hhParser;

    @Test
    void parseValidTest() throws IOException {
        List<Vacancy> expectedVacancies = List.of(new Vacancy());
        when(vacancyBrowser.browse(VACANCY_TO_SEARCH, NUMBER_OF_VACANCIES, FILTER)).thenReturn(expectedVacancies);

        List<Vacancy> actualVacancies = hhParser.parse(VACANCY_TO_SEARCH, NUMBER_OF_VACANCIES, FILTER);
        assertEquals(expectedVacancies, actualVacancies);
    }

    @Test
    void parseExceptionTest() throws IOException {
        // TODO intercept log
        List<Vacancy> expectedVacancies = Collections.emptyList();
        when(vacancyBrowser.browse(VACANCY_TO_SEARCH, NUMBER_OF_VACANCIES, FILTER)).thenThrow(new IOException());

        List<Vacancy> actualVacancies = hhParser.parse(VACANCY_TO_SEARCH, NUMBER_OF_VACANCIES, FILTER);
        assertEquals(expectedVacancies, actualVacancies);
    }
}