package org.parsingbot.commons.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.repository.SearchHistoryRepository;
import org.parsingbot.commons.utils.TestHelper;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты сервиса SearchHistoryService")
class SearchHistoryServiceImplTest {

    private static final Random RND = new Random();

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @InjectMocks
    private SearchHistoryServiceImpl sut;

    @Test
    @DisplayName("Тест метода save")
    void saveTest() {
        String vacancyName = TestHelper.randomFromUuid();
        Long numberOfVacancies = RND.nextLong();
        String keywords = TestHelper.randomFromUuid();
        User user = User.builder().build();
        SearchHistory searchHistory = SearchHistory.builder()
                .vacancyName(vacancyName)
                .numberOfVacancies(numberOfVacancies)
                .keywords(keywords)
                .user(user)
                .build();

        when(searchHistoryRepository.save(searchHistory)).thenReturn(searchHistory);

        SearchHistory actual = sut.save(searchHistory);

        assertEquals(searchHistory, actual);
    }
}