package org.parsingbot.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.parser.service.ParserService;
import org.parsingbot.service.UserService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HhKeywordsSelect3CommandHandler implements CommandHandler {

    private static final String FINAL_MESSAGE = "Результаты поиска по запросу вакансия = '%s'," +
            "количество вакансий = '%d', ключевые слова = {%s}";

    private final UserService userService;
    private final ParserService parserService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        User user = event.getUser();

        String keywords = event.getCommand().getFullMessage();
        if (StringUtils.isNotBlank(keywords)) {
            user.setKeywords(keywords);
            userService.save(user);
        }

        List<SendMessage> messagesToUser = new ArrayList<>();
        messagesToUser.add(BotUtils.createMessage(
                event.getChatId(),
                String.format(FINAL_MESSAGE, user.getVacancyName(), user.getNumberOfVacancies(), user.getKeywords()))
        );

        List<SendMessage> vacancies = parserService.getVacanciesMessageList(event);
        messagesToUser.addAll(vacancies);

        userService.updateStateByUser(event.getUser(), State.NONE);
        return List.copyOf(messagesToUser);
    }

    @Override
    public State getRequiredState() {
        return State.HH_KEYWORDS_SELECT_3;
    }
}
