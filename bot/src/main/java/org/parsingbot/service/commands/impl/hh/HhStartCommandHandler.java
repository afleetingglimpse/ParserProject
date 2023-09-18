package org.parsingbot.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.State;
import org.parsingbot.service.UserService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
public class HhStartCommandHandler implements CommandHandler {

    private static final String GREETING_TEXT = "Введите название вакансии для поиска. Или оставьте пустым, чтобы увидеть" +
            "вакансии для Java-разработчиков";

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        userService.updateStateByUser(event.getUser(), State.HH_1);
        return List.of(BotUtils.createMessage(event.getChatId(), GREETING_TEXT));
    }
}