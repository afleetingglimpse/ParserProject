package org.parsingbot.commons.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тесты на утильный класс SearchHistoryUtils")
class SearchHistoryUtilsTest {

    private static final String DEFAULT_VACANCY_NAME = "none";
    private static final Long DEFAULT_NUMBER_OF_VACANCIES = 0L;
    private static final String DEFAULT_KEYWORDS = "none";

    private static final Random RND = new Random();

    @Test
    @DisplayName("Тест метода getLastSearchHistoryOrCreateNew, user.searchHistories = null")
    void getLastSearchHistoryOrCreateNewNullTest() {
        User user = User.builder()
                .userName("innvernes")
                .build();

        SearchHistory expected = SearchHistory.builder()
                .vacancyName(DEFAULT_VACANCY_NAME)
                .numberOfVacancies(DEFAULT_NUMBER_OF_VACANCIES)
                .keywords(DEFAULT_KEYWORDS)
                .user(user)
                .build();

        assertEquals(expected, SearchHistoryUtils.getLastSearchHistoryOrCreateNew(user));
        assertNotNull(user.getSearchHistories());
        assertEquals(1, user.getSearchHistories().size());
        assertEquals(expected, user.getSearchHistories().get(0));
    }

    @Test
    @DisplayName("Тест метода getLastSearchHistoryOrCreateNew, user.searchHistories пуст")
    void getLastSearchHistoryOrCreateNewEmptyTest() {
        User user = User.builder()
                .userName("innvernes")
                .searchHistories(new ArrayList<>())
                .build();

        SearchHistory expected = SearchHistory.builder()
                .vacancyName(DEFAULT_VACANCY_NAME)
                .numberOfVacancies(DEFAULT_NUMBER_OF_VACANCIES)
                .keywords(DEFAULT_KEYWORDS)
                .user(user)
                .build();

        assertEquals(expected, SearchHistoryUtils.getLastSearchHistoryOrCreateNew(user));
        assertNotNull(user.getSearchHistories());
        assertEquals(1, user.getSearchHistories().size());
        assertEquals(expected, user.getSearchHistories().get(0));
    }

    @Test
    @DisplayName("Тест метода getLastSearchHistoryOrCreateNew, user.searchHistories содержит SearchHistory")
    void getLastSearchHistoryOrCreateNewNotEmptyTest() {
        User user = User.builder()
                .userName("innvernes")
                .build();

        String vacancyName = randomFromUuid();
        Long numberOfVacancies = RND.nextLong();
        String keywords = randomFromUuid();
        List<SearchHistory> searchHistories = new ArrayList<>();
        searchHistories.add(SearchHistory.builder()
                .vacancyName(vacancyName)
                .numberOfVacancies(numberOfVacancies)
                .keywords(keywords)
                .user(user)
                .build()
        );

        user.setSearchHistories(searchHistories);

        SearchHistory expected = SearchHistory.builder()
                .vacancyName(vacancyName)
                .numberOfVacancies(numberOfVacancies)
                .keywords(keywords)
                .user(user)
                .build();

        assertEquals(expected, SearchHistoryUtils.getLastSearchHistoryOrCreateNew(user));
        assertNotNull(user.getSearchHistories());
        assertEquals(1, user.getSearchHistories().size());
        assertEquals(expected, user.getSearchHistories().get(0));
    }

    private String randomFromUuid() {
        return UUID.randomUUID().toString();
    }
}