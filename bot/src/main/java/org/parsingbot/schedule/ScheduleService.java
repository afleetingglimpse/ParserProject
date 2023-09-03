package org.parsingbot.schedule;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.bot.utils.VacancyPredicates;
import org.parsingbot.service.handlers.ResponseHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ScheduleService {

    private static final Map<String, String> DEFAULT_PARSING_PARAMETERS = Map.of(
            "vacancyToSearch", "java",
            "numberOfVacancies", "5"
    );

    private final TelegramBot bot;
    private final ResponseHandler responseHandler;
    private final UserService userService;
    private final VacancyService vacancyService;

    @Scheduled(fixedDelayString = "${scheduler.fixedDelayMS}")
    @Transactional
    public void sendDataEveryMinute() {
        List<User> subscribedUsers = userService.getSubscribedUsers();
        subscribedUsers.forEach(user -> sendData(user, DEFAULT_PARSING_PARAMETERS));
    }

    private void sendData(User user, Map<String, String> parsingParameters) {
        // TODO inject instead of declare
        Parser parser = bot.getParser();

        String vacancyToSearch = parsingParameters.get("vacancyToSearch");
        int numberOfVacancies = Integer.parseInt(parsingParameters.get("numberOfVacancies"));

        List<Vacancy> userVacancies = vacancyService.getVacanciesByUser(user);

        List<Vacancy> vacancies = parser.parse(
                vacancyToSearch,
                numberOfVacancies,
                VacancyPredicates.uniqueVacancy(userVacancies));

        vacancies.forEach(vacancy -> {
            user.addVacancy(vacancy);
            responseHandler.sendResponse(bot, vacancy.getVacancyLink(), user.getChatId());
        });
        vacancyService.save(vacancies);
        userService.save(user);
    }
}