package org.parsingbot.schedule;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.Parser;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.bot.utils.VacancyPredicates;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.user.User;
import org.parsingbot.service.user.UserService;
import org.parsingbot.utils.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class ScheduleService {

    private final TelegramBot bot;
    private final ResponseHandler responseHandler;
    private final UserService userService;

    private static final Map<String, String> parsingParameters = Map.of(
            "vacancyToSearch", "java",
            "numberOfVacancies", "5"
    );

    @Scheduled(fixedDelayString = "${scheduler.fixedDelayMS}")
    public void sendDataEveryMinute() {
        List<User> subscribedUsers = userService.getSubscribedUsers();
        subscribedUsers.forEach(user -> sendData(user, parsingParameters));
    }

    private void sendData(User user,  Map<String, String> parsingParameters) {
        Parser parser = bot.getParser();

        String vacancyToSearch = parsingParameters.get("vacancyToSearch");
        int numberOfVacancies = Integer.parseInt(parsingParameters.get("numberOfVacancies"));

        List<Vacancy> vacancies = parser.parse(vacancyToSearch, numberOfVacancies, VacancyPredicates.unique(parser));

        vacancies.forEach(vacancy -> responseHandler.sendResponse(bot, vacancy.getVacancyLink(), user.getChatId()));
    }
}
