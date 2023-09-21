package org.parsingbot.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.State;
import org.parsingbot.service.UserService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HhStart0CommandHandler implements CommandHandler {

    private static final String GREETING_TEXT_0 = "Введите название вакансии для поиска";
    private static final String GREETING_TEXT_0_VACANCY_NAME_NOT_NULL =
            "Оставьте пустым, чтобы увидеть вакансии, которые вы искали в прошлый раз (%s)";

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        List<SendMessage> messagesToUser = new ArrayList<>();
        messagesToUser.add(BotUtils.createMessage(event.getChatId(), GREETING_TEXT_0));

        String vacancyName = userService.getVacancyNameByUserId(event.getUser().getId());
        if (StringUtils.isNotBlank(vacancyName)) {
            messagesToUser.add(
                    BotUtils.createMessage(event.getChatId(), String.format(GREETING_TEXT_0_VACANCY_NAME_NOT_NULL, vacancyName))
            );
        }
        userService.updateStateByUser(event.getUser(), State.HH_VACANCY_SELECT_1);
        return List.copyOf(messagesToUser);
    }
}