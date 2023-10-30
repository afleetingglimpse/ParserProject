package org.parsingbot.commons.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.parsingbot.commons.configuration.TestJpaConfiguration;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.entity.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = TestJpaConfiguration.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DisplayName("Тест репозитория  SearchHistoryRepository")
class SearchHistoryRepositoryTest {

    private static final Random RND = new Random();
    private static final String USER_NAME = "innvernes";
    private static final String AUTHORISATION = "authorisation";
    private static final Boolean IS_SUBSCRIBED = true;
    private static final long CHAT_ID = 1;
    private static final LocalDateTime NEXT_SEND_DATE = LocalDateTime.now();
    private static final long NEXT_SEND_DATE_DELAY_SECONDS = 60;
    private static final String STATE = State.NONE.toString();
    private static final String VACANCY_NAME_DB = "vacancyName";
    private static final long NUMBER_OF_VACANCIES_DB = 10;
    private static final String KEYWORDS = "На недельку до второго я уеду в Комарово";

    private static final List<Vacancy> USER_VACANCIES = new ArrayList<>();
    private static final List<SearchHistory> SEARCH_HISTORY = new ArrayList<>();

    private static final User VALID_USER = User.builder()
            .id(1L)
            .userName(USER_NAME)
            .authorisation(AUTHORISATION)
            .isSubscribed(IS_SUBSCRIBED)
            .chatId(CHAT_ID)
            .nextSendDate(NEXT_SEND_DATE)
            .nextSendDateDelaySeconds(NEXT_SEND_DATE_DELAY_SECONDS)
            .state(STATE)
            .userVacancies(USER_VACANCIES)
            .searchHistories(SEARCH_HISTORY)
            .vacancyName(VACANCY_NAME_DB)
            .numberOfVacancies(NUMBER_OF_VACANCIES_DB)
            .keywords(KEYWORDS)
            .build();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Test
    @DisplayName("Тест метода findSearchHistoryByUserId")
    void findSearchHistoryByUserIdTest() {
        String vacancyName = randomFromUuid();
        Long numberOfVacancies = RND.nextLong();
        String keywords = randomFromUuid();
        SearchHistory searchHistory = SearchHistory.builder()
                .vacancyName(vacancyName)
                .numberOfVacancies(numberOfVacancies)
                .keywords(keywords)
                .user(VALID_USER)
                .build();

        VALID_USER.addSearchHistory(searchHistory);
        userRepository.save(VALID_USER);
        searchHistoryRepository.save(searchHistory);

        SearchHistory actual = searchHistoryRepository.findSearchHistoryByUserId(1L);
        assertEquals(searchHistory, actual);
    }

    private String randomFromUuid() {
        return UUID.randomUUID().toString();
    }
}