package org.parsingbot.schedule;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.bot.utils.VacancyPredicates;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ScheduleService {

    private static final Map<String, String> DEFAULT_PARSING_PARAMETERS = Map.of(
            "vacancyToSearch", "java",
            "numberOfVacancies", "5"
    );

    private final TelegramBot bot;
    private final UserService userService;
    private final VacancyService vacancyService;
    private final Parser parser;

    @Transactional
    @Scheduled(fixedDelayString = "${scheduler.checkSubscribedUsersSendDateDelayMS}")
    public void checkSubscribedUsersSendDate() {
        List<User> subscribedUsers = userService.getSubscribedUsers();
        // TODO add custom params
        Map<String, String> parsingParameters = DEFAULT_PARSING_PARAMETERS;
        subscribedUsers.forEach(user -> sendVacanciesToUser(user, parsingParameters));
    }

    private void sendVacanciesToUser(User user, Map<String, String> parsingParameters) {
        LocalDateTime currentDate = LocalDateTime.now();
        if (user.getNextSendDate().isAfter(currentDate)) {
            return;
        }
        userService.updateNextSendDate(user);

        List<Vacancy> vacancies = getVacancies(user, parsingParameters);
        vacancies.forEach(vacancy -> {
            user.addVacancy(vacancy);
            // responseHandler.sendResponse(bot, vacancy.getVacancyLink(), user.getChatId());
        });
        vacancyService.save(vacancies);
        userService.save(user);
    }

    private List<Vacancy> getVacancies(User user, Map<String, String> parsingParameters) {
        List<Vacancy> userVacancies = vacancyService.getVacanciesByUser(user);
        String vacancyToSearch = parsingParameters.get("vacancyToSearch");
        int numberOfVacancies = Integer.parseInt(parsingParameters.get("numberOfVacancies"));
        return parser.parse(
                vacancyToSearch,
                numberOfVacancies,
                VacancyPredicates.uniqueVacancy(userVacancies));
    }
}