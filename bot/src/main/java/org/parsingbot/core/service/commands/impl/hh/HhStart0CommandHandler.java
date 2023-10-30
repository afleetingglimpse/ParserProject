package org.parsingbot.core.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.core.service.commands.CommandHandler;
import org.parsingbot.core.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class HhStart0CommandHandler implements CommandHandler {

    private static final String TYPE_VACANCY_NAME = "Введите название вакансии для поиска";
    private static final String POPULAR_VACANCIES_TEXT = "Или выберете из списка популярных";
    private static final Map<String, String> POPULAR_VACANCIES = Map.of(
            "Java developer", "Java",
            "Python developer", "Python"
    );

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        List<SendMessage> messagesToUser = new ArrayList<>();
        messagesToUser.add(BotUtils.createMessage(event.getChatId(), TYPE_VACANCY_NAME));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = POPULAR_VACANCIES.keySet().stream()
                .map(key -> BotUtils.createInlineKeyboardButton(key, POPULAR_VACANCIES.get(key)))
                .toList();
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));

        messagesToUser.add(
                BotUtils.createMessageTemplate(event.getChatId(), POPULAR_VACANCIES_TEXT, inlineKeyboardMarkup)
        );

        User user = event.getUser();
        user.setState(State.HH_VACANCY_SELECT_1.toString());
        userService.save(user);
        return List.copyOf(messagesToUser);
    }
}