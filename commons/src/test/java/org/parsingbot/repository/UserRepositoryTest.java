package org.parsingbot.repository;

import org.junit.jupiter.api.Test;
import org.parsingbot.configuration.TestJpaConfiguration;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestJpaConfiguration.class)
@ActiveProfiles("test")
class UserRepositoryTest {

    private static final String USER_NAME = "gwynae";
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

    @Test
    void getUserVacanciesIdsTest() {
        vacancyRepository.save(VALID_VACANCY);
        User user = VALID_USER;
        user.addVacancy(VALID_VACANCY);
        userRepository.save(user);
        List<Integer> ids = userRepository.getUserVacanciesIds(user.getId());
        assertEquals(List.of(1), ids);
    }
}