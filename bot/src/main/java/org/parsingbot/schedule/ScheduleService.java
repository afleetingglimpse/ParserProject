package org.parsingbot.schedule;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.parser.service.ParserService;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.bot.TelegramBot;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ScheduleService {

    private static final String VACANCY_NAME_PARAMETER = "vacancyName";
    private static final String NUMBER_OF_VACANCIES_PARAMETER = "numberOfVacancies";
    private static final String KEYWORDS_PARAMETER = "keywords";
    private static final Map<String, String> DEFAULT_PARSING_PARAMETERS = Map.of(
            VACANCY_NAME_PARAMETER, "java",
            NUMBER_OF_VACANCIES_PARAMETER, "5"
    );

    private final TelegramBot bot;
    private final UserService userService;
    private final VacancyService vacancyService;
    private final ParserService parserService;

    @Transactional
    @Scheduled(fixedDelayString = "${scheduler.checkSubscribedUsersSendDateDelayMS}")
    public void checkSubscribedUsersSendDate() {
        List<User> subscribedUsers = userService.getSubscribedUsers();
        subscribedUsers.forEach(user -> {
            Map<String, String> parsingParameters = getUserParsingParameters(user);
            sendVacanciesToUser(user, parsingParameters);
        });
    }

    private void sendVacanciesToUser(User user, Map<String, String> parsingParameters) {
        LocalDateTime currentDate = LocalDateTime.now();
        if (user.getNextSendDate().isAfter(currentDate)) {
            return;
        }
        userService.updateNextSendDate(user);

        List<Vacancy> vacancies = parserService.getVacancies(user, parsingParameters);
        user.addAllVacancies(vacancies);
        vacancyService.save(vacancies);
        userService.save(user);

        List<SendMessage> vacanciesMessages = parserService.getVacanciesMessageList(user, parsingParameters);
        vacanciesMessages.forEach((method) -> {
            try {
                bot.execute(method);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Map<String, String> getUserParsingParameters(User user) {
        String vacancyName = user.getVacancyName();
        String numberOfVacancies = String.valueOf(user.getNumberOfVacancies());
        String keywords = user.getKeywords();

        Map<String, String> parsingParameters = new HashMap<>();
        parsingParameters.put(VACANCY_NAME_PARAMETER, vacancyName);
        parsingParameters.put(NUMBER_OF_VACANCIES_PARAMETER, numberOfVacancies);
        parsingParameters.put(KEYWORDS_PARAMETER, keywords);
        parsingParameters.forEach((k, v) -> {
            if (v == null) {
                parsingParameters.put(k, DEFAULT_PARSING_PARAMETERS.get(k));
            }
        });
        return parsingParameters;
    }
}