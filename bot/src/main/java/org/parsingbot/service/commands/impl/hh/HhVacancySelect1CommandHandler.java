package org.parsingbot.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HhVacancySelect1CommandHandler implements CommandHandler {

    private static final String GREETING_TEXT_2 = "Введите количество вакансий для поиска";
    private static final String GREETING_TEXT_2_NUMBER_OF_VACANCIES_NOT_NULL =
            "Оставьте пустым, чтобы увидеть такое количество вакансий, которые вы искали в прошлый раз (%d)";

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        User user = event.getUser();

        String vacancyName = event.getCommand().getFullMessage();
        if (StringUtils.isNotBlank(vacancyName)) {
            user.setVacancyName(vacancyName);
            userService.save(user);
        }

        List<SendMessage> messagesToUser = new ArrayList<>();
        messagesToUser.add(BotUtils.createMessage(event.getChatId(), GREETING_TEXT_2));

        long numberOfVacancies = userService.getNumberOfVacanciesByUserId(user.getId());
        if (numberOfVacancies != 0) {
            messagesToUser.add(BotUtils.createMessage(event.getChatId(), GREETING_TEXT_2_NUMBER_OF_VACANCIES_NOT_NULL));
        }

        userService.updateStateByUser(user, State.HH_NUMBER_OF_VACANCIES_SELECT_2);
        return List.copyOf(messagesToUser);
    }

    @Override
    public State getRequiredState() {
        return State.HH_VACANCY_SELECT_1;
    }
}