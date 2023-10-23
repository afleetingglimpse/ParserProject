package org.parsingbot.parser.service.impl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.commons.entity.Vacancy;
import org.parsingbot.parser.service.VacancyBrowser;
import org.slf4j.LoggerFactory;

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
    private final static String PARSING_ERROR =
            "Parsing error with params vacancyToSearch = %s, numberOfVacancies = %s occurred";
    @Mock
    private VacancyBrowser vacancyBrowser;

    @InjectMocks
    private HhParser hhParser;

    @Test
    void parseValidTest() throws IOException {
        List<Vacancy> expectedVacancies = List.of(Vacancy.builder().build());
        when(vacancyBrowser.browse(VACANCY_TO_SEARCH, NUMBER_OF_VACANCIES, FILTER)).thenReturn(expectedVacancies);

        List<Vacancy> actualVacancies = hhParser.parse(VACANCY_TO_SEARCH, NUMBER_OF_VACANCIES, FILTER);
        assertEquals(expectedVacancies, actualVacancies);
    }

    @Test
    void parseExceptionTest() throws IOException {
        ListAppender<ILoggingEvent> logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(HhParser.class)).addAppender(logWatcher);

        List<Vacancy> expectedVacancies = Collections.emptyList();
        when(vacancyBrowser.browse(VACANCY_TO_SEARCH, NUMBER_OF_VACANCIES, FILTER)).thenThrow(new IOException());

        List<Vacancy> actualVacancies = hhParser.parse(VACANCY_TO_SEARCH, NUMBER_OF_VACANCIES, FILTER);

        assertEquals(String.format(PARSING_ERROR, VACANCY_TO_SEARCH, NUMBER_OF_VACANCIES),
                logWatcher.list.get(0).getFormattedMessage());
        assertEquals(Level.WARN, logWatcher.list.get(0).getLevel());
        assertEquals(expectedVacancies, actualVacancies);
        logWatcher.stop();
    }
}