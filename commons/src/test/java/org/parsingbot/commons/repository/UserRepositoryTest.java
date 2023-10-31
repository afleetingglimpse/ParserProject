package org.parsingbot.commons.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.parsingbot.commons.configuration.TestJpaConfiguration;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.entity.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = TestJpaConfiguration.class)
@DisplayName("Тест репозитория пользователей UserRepository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

    private static final String USER_NAME = "innvernes";
    private static final String AUTHORISATION = "authorisation";
    private static final Boolean IS_SUBSCRIBED = true;
    private static final long CHAT_ID = 1;
    private static final LocalDateTime NEXT_SEND_DATE = LocalDateTime.now();
    private static final long NEXT_SEND_DATE_DELAY_SECONDS = 60;
    private static final String STATE = State.NONE.toString();

    private static final List<Vacancy> USER_VACANCIES = new ArrayList<>();

    private static final User VALID_USER = User.builder()
            .userName(USER_NAME)
            .authorisation(AUTHORISATION)
            .isSubscribed(IS_SUBSCRIBED)
            .chatId(CHAT_ID)
            .nextSendDate(NEXT_SEND_DATE)
            .nextSendDateDelaySeconds(NEXT_SEND_DATE_DELAY_SECONDS)
            .state(STATE)
            .userVacancies(USER_VACANCIES)
            .build();

    private static final String VACANCY_NAME = "vacancyName";
    private static final String VACANCY_DESCRIPTION = "vacancyDescription";
    private static final String VACANCY_LINK = "http://link.com";
    private static final List<User> VACANCY_FOLLOWERS = new ArrayList<>();

    private static final Vacancy VALID_VACANCY = Vacancy.builder()
            .vacancyName(VACANCY_NAME)
            .vacancyDescription(VACANCY_DESCRIPTION)
            .vacancyLink(VACANCY_LINK)
            .vacancyFollowers(VACANCY_FOLLOWERS)
            .build();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @BeforeEach
    void saveVacancy() {
        vacancyRepository.save(VALID_VACANCY);
    }

    @Test
    @DisplayName("Тест метода findUserVacanciesIds")
    void findUserVacanciesIdsTest() {
        User user = VALID_USER;
        user.addVacancy(VALID_VACANCY);
        userRepository.save(user);
        List<Integer> ids = userRepository.findUserVacanciesIds(user.getId());
        assertEquals(List.of(1), ids);
    }

    @Test
    @DisplayName("Тест метода findSubscribedUsers")
    void findSubscribedUsersTest() {
        userRepository.save(VALID_USER);
        List<User> subscribedUsers = userRepository.findSubscribedUsers();
        assertEquals(1, subscribedUsers.size());
        assertEquals(VALID_USER, subscribedUsers.get(0));
    }

    @Test
    @DisplayName("Тест метода findUserByChatIdTest. Пользователь есть в базе")
    void findUserByChatId_UserExistsTest() {
        userRepository.save(VALID_USER);
        Optional<User> userOptional = userRepository.findUserByChatId(VALID_USER.getChatId());
        assertTrue(userOptional.isPresent());
        assertEquals(VALID_USER, userOptional.get());
    }

    @Test
    @DisplayName("Тест метода getUserByChatIdTest. Пользователя нет в базе")
    void findUserByChatId_UserNotExistsTest() {
        userRepository.save(VALID_USER);
        long incorrectChatId = 2;
        Optional<User> userOptional = userRepository.findUserByChatId(incorrectChatId);
        assertFalse(userOptional.isPresent());
        assertEquals(Optional.empty(), userOptional);
    }
}