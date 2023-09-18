package org.parsingbot.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Command;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.bot.utils.VacancyPredicates;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandParser;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.util.BotUtils;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Deprecated(since = "18.09.2023", forRemoval = true)
public class HhCommandHandler implements CommandHandler {

    private final VacancyService vacancyService;
    private final UserService userService;
    private final CommandParser commandParser;
    private final Parser parser;

//    private static final State STATE_AFTER_COMPLETION = State.HH_1;

    @Override
    @Transactional
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Command command, Update update) {
        long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        Optional<User> userOptional = userService.getUserByName(userName);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();

        Map<String, String> commandParameters = commandParser.parseCommand(command);
        String vacancyToSearch = commandParameters.get("vacancyName");
        int numberOfVacancies = Integer.parseInt(commandParameters.get("numberOfVacancies"));

        List<Vacancy> userVacancies = vacancyService.getVacanciesByUser(user);

        List<Vacancy> vacancies = parser.parse(
                vacancyToSearch,
                numberOfVacancies,
                VacancyPredicates.uniqueVacancy(userVacancies));

        List<SendMessage> vacanciesToSend = new ArrayList<>();
        vacancies.forEach(vacancy -> {
            user.addVacancy(vacancy);
            vacanciesToSend.add(BotUtils.createMessage(chatId, vacancy.getVacancyLink()));
        });
        vacancyService.save(vacancies);
        userService.save(user);
        return List.copyOf(vacanciesToSend);
    }
}