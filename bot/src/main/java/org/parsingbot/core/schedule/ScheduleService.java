package org.parsingbot.core.schedule;

import lombok.RequiredArgsConstructor;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.entity.Vacancy;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.commons.service.VacancyService;
import org.parsingbot.commons.utils.SearchHistoryUtils;
import org.parsingbot.commons.utils.UserUtils;
import org.parsingbot.core.bot.TelegramBot;
import org.parsingbot.core.parser.service.ParserService;
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
        UserUtils.updateUserNextSendDate(user);

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
        SearchHistory searchHistory = SearchHistoryUtils.getLastSearchHistoryOrCreateNew(user);

        Map<String, String> parsingParameters = new HashMap<>();
        parsingParameters.put(VACANCY_NAME_PARAMETER, searchHistory.getVacancyName());
        parsingParameters.put(NUMBER_OF_VACANCIES_PARAMETER, String.valueOf(searchHistory.getNumberOfVacancies()));
        parsingParameters.put(KEYWORDS_PARAMETER, searchHistory.getKeywords());
        parsingParameters.forEach((k, v) -> {
            if (v == null) {
                parsingParameters.put(k, DEFAULT_PARSING_PARAMETERS.get(k));
            }
        });
        return parsingParameters;
    }
}