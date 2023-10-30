package org.parsingbot.commons.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.entity.Vacancy;
import org.parsingbot.commons.repository.SearchHistoryRepository;
import org.parsingbot.commons.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для сервиса UserService")
class UserServiceImplTest {

    private static final Random RND = new Random();

    @Mock
    private UserRepository userRepository;
    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @InjectMocks
    private UserServiceImpl sut;

    @Test
    @DisplayName("Тест метода save")
    void save() {
        User user = createUser();

        when(userRepository.save(user)).thenReturn(user);

        User actual = sut.save(user);

        assertEquals(user, actual);

        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(searchHistoryRepository);
    }

    @Test
    @DisplayName("Тест метода getSubscribedUsers")
    void getSubscribedUsers() {
        User userSubscribed = createUser();
        userSubscribed.setIsSubscribed(true);

        List<User> expected = List.of(userSubscribed);
        when(userRepository.findSubscribedUsers()).thenReturn(expected);

        List<User> actual = sut.getSubscribedUsers();

        assertEquals(expected, actual);

        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(searchHistoryRepository);
    }

    @Test
    @DisplayName("Тест метода getUserByChatId")
    void getUserByChatId() {
        User user = createUser();
        Long chatId = user.getChatId();
        Optional<User> expected = Optional.of(user);

        when(userRepository.findUserByChatId(chatId)).thenReturn(expected);

        Optional<User> actual = sut.getUserByChatId(chatId);

        assertEquals(expected, actual);

        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(searchHistoryRepository);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Тест метода getUserByChatIdCreateIfNotExist")
    void getUserByChatIdCreateIfNotExist(boolean isPresent) {
        User user = createUser();
        Long chatId = user.getChatId();
        String newUserName = randomFromUuid();

        Optional<User> expected = Optional.empty();
        User newExpectedUser = null;
        if (isPresent) {
            expected = Optional.of(user);
        } else {
            newExpectedUser = User.builder()
                    .userName(newUserName)
                    .chatId(chatId)
                    .state(State.NONE.toString())
                    .build();
            when(userRepository.save(newExpectedUser)).thenReturn(newExpectedUser);

        }
        when(userRepository.findUserByChatId(chatId)).thenReturn(expected);

        User actual = sut.getUserByChatIdCreateIfNotExist(chatId, newUserName);
        if (isPresent) {
            assertEquals(expected.get(), actual);
        } else {
            assertEquals(newExpectedUser, actual);
        }

        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(searchHistoryRepository);
    }

    @Test
    @DisplayName("Тест метода updateNextSendDate")
    void updateNextSendDate() {
        User user = createUser();

        sut.updateNextSendDate(user);

        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(searchHistoryRepository);
    }

    @Test
    @DisplayName("Тест метода setDefaultStateByUser")
    void setDefaultStateByUser() {
        User user = createUser();
        Long userId = user.getId();

        sut.setDefaultStateByUser(user);

        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(searchHistoryRepository);
    }

    @Test
    @DisplayName("Тест метода getVacancyNameByUserId")
    void getVacancyNameByUserId() {
        Long userId = RND.nextLong();

        String expected = randomFromUuid();
        when(userRepository.findVacancyNameByUserId(userId)).thenReturn(expected);

        String actual = sut.getVacancyNameByUserId(userId);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(searchHistoryRepository);
    }

    @Test
    @DisplayName("Тест метода getNumberOfVacanciesByUserId")
    void getNumberOfVacanciesByUserId() {
        Long userId = RND.nextLong();

        Long expected = RND.nextLong();
        when(userRepository.findNumberOfVacanciesByUserId(userId)).thenReturn(expected);

        Long actual = sut.getNumberOfVacanciesByUserId(userId);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(searchHistoryRepository);
    }

    @Test
    @DisplayName("Тест метода getKeywordsByUserId")
    void getKeywordsByUserId() {
        Long userId = RND.nextLong();

        String expected = randomFromUuid();
        when(userRepository.findKeywordsByUserId(userId)).thenReturn(expected);

        String actual = sut.getKeywordsByUserId(userId);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(searchHistoryRepository);
    }

    @Test
    @DisplayName("Тест метода getUserSearchHistory")
    void getUserSearchHistory() {
        User user = createUser();
        Long id = user.getId();

        SearchHistory expected = SearchHistory.builder().build();
        when(searchHistoryRepository.findSearchHistoryByUserId(id)).thenReturn(expected);

        SearchHistory actual = sut.getUserSearchHistory(user);

        assertEquals(expected, actual);

        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(searchHistoryRepository);
    }

    private String randomFromUuid() {
        return UUID.randomUUID().toString();
    }

    private User createUser() {
        Long id = RND.nextLong();
        String userName = randomFromUuid();
        String authorisation = randomFromUuid();
        Boolean isSubscribed = RND.nextBoolean();
        Long chatId = RND.nextLong();
        LocalDateTime nextSendDate = LocalDateTime.now();
        Long nextSendDateDelaySeconds = RND.nextLong(100); // to not go out of LocalDate bounds
        String state = randomFromUuid();
        String vacancyName = randomFromUuid();
        Long numberOfVacancies = RND.nextLong();
        String keywords = randomFromUuid();
        List<Vacancy> userVacancies = new ArrayList<>();
        List<SearchHistory> searchHistories = new ArrayList<>();
        return User.builder()
                .id(id)
                .userName(userName)
                .authorisation(authorisation)
                .isSubscribed(isSubscribed)
                .chatId(chatId)
                .nextSendDate(nextSendDate)
                .nextSendDateDelaySeconds(nextSendDateDelaySeconds)
                .state(state)
                .vacancyName(vacancyName)
                .numberOfVacancies(numberOfVacancies)
                .keywords(keywords)
                .userVacancies(userVacancies)
                .searchHistories(searchHistories)
                .build();
    }
}