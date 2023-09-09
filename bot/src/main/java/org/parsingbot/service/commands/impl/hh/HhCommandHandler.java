package org.parsingbot.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.CommandDto;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.bot.utils.VacancyPredicates;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandParser;
import org.parsingbot.service.handlers.ResponseHandler;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class HhCommandHandler implements CommandHandler {

    private final ResponseHandler responseHandler;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final CommandParser commandParser;

    @Override
    @Transactional
    public void handleCommand(TelegramBot bot, CommandDto command, Update update) {
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        Optional<User> userOptional = userService.getUserByName(userName);

        if (userOptional.isEmpty()) {
            return;
        }

        User user = userOptional.get();

        Map<String, String> commandParameters = commandParser.parseCommand(command);
        String vacancyToSearch = commandParameters.get("vacancyName");
        int numberOfVacancies = Integer.parseInt(commandParameters.get("numberOfVacancies"));

        Parser parser = bot.getParser();

        List<Vacancy> userVacancies = vacancyService.getVacanciesByUser(user);

        List<Vacancy> vacancies = parser.parse(
                vacancyToSearch,
                numberOfVacancies,
                VacancyPredicates.uniqueVacancy(userVacancies));

        vacancies.forEach(vacancy -> {
            user.addVacancy(vacancy);
            responseHandler.sendResponse(bot, vacancy.getVacancyLink(), chatId);
        });
        vacancyService.save(vacancies);
        userService.save(user);
    }
}