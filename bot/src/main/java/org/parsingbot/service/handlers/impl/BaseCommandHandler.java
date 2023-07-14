package org.parsingbot.service.handlers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.Parser;
import org.parsingbot.service.bot.impl.TelegramBot;
import org.parsingbot.service.handlers.CommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.utils.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
@RequiredArgsConstructor
public class BaseCommandHandler implements CommandHandler {

    private static final String START_COMMAND_MESSAGE = "Привет, я бот, который умеет парсить HH!";

    private final Map<String, Function<String, Void>> commandsFunctionsMap = Map.of(
            "/hh", this::handleHhCommand,
            "/start", this::handleStartCommand
    );

    private final ResponseHandler responseHandler;

    private TelegramBot bot;
    private String userName;
    private long chatId;

    @Override
    public boolean isCommand(String command) {
        return commandsFunctionsMap.containsKey(command);
    }

    @Override
    public void handleCommand(TelegramBot bot, Update update) {
        chatId = update.getMessage().getChatId();
        userName = update.getMessage().getChat().getUserName();
        String command = update.getMessage().getText();
        String commandBeginning = command.split(" ")[0];

        this.bot = bot;
        if(isCommand(commandBeginning)) {
            commandsFunctionsMap.get(commandBeginning).apply(command);
        }
    }

    private Void handleHhCommand(String command) {
        responseHandler.sendResponse(bot, "wow", chatId);
        List<String> commandParameters = StringUtils.parseHhCommand(command);
        Parser parser = bot.getParser();

        Predicate<Vacancy> unique = vacancy -> !parser.getAllVacancies().contains(vacancy);

        String vacancyToSearch = commandParameters.get(0);
        int numberOfVacancies = Integer.parseInt(commandParameters.get(1));
        List<Vacancy> vacancies = parser.parse(vacancyToSearch, numberOfVacancies, unique);
        vacancies.forEach(vacancy -> responseHandler.sendResponse(bot, vacancy.getVacancyLink(), chatId));
        return null;
    }

    private Void handleStartCommand(String command) {
        responseHandler.sendResponse(bot, START_COMMAND_MESSAGE, chatId);
        return null;
    }

}
