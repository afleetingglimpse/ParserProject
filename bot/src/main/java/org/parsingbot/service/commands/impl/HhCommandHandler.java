package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.bot.utils.VacancyPredicates;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.utils.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@RequiredArgsConstructor
public class HhCommandHandler implements CommandHandler {

    private final ResponseHandler responseHandler;
    private final VacancyService vacancyService;
    private final UserService userService;

    private TelegramBot bot;

    @Override
    public void handleCommand(TelegramBot bot, Update update) {
        String command = update.getMessage().getText();
        String commandBeginning = command.split(" ")[0];

        this.bot = bot;

        if (commandBeginning.equals("/hh")) {
            handleHhCommand(command, update);
        }
    }

    private void handleHhCommand(String command, Update update) {

        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        Optional<User> userOptional = userService.getUserByName(userName);

        if (userOptional.isEmpty()) {
            return;
        }

        User user = userOptional.get();

        List<String> commandParameters = StringUtils.parseHhCommand(command);
        Parser parser = bot.getParser();

        List<Vacancy> userVacancies = vacancyService.getVacanciesByUser(user);

        // TODO вынести в параметр запроса?
        Predicate<Vacancy> unique = VacancyPredicates.uniqueVacancy(userVacancies);

        String vacancyToSearch = commandParameters.get(0);
        int numberOfVacancies = Integer.parseInt(commandParameters.get(1));
        List<Vacancy> vacancies = parser.parse(vacancyToSearch, numberOfVacancies, unique);
        vacancies.forEach(vacancy -> {
            user.addVacancy(vacancy);
            responseHandler.sendResponse(bot, vacancy.getVacancyLink(), chatId);
        });
        vacancyService.save(vacancies);
        userService.save(user);
    }
}
