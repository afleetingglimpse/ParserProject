package org.parsingbot.core.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.SearchHistory;
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

@RequiredArgsConstructor
public class HhVacancySelect1CommandHandler implements CommandHandler {

    private static final String GREETING_TEXT_2 = "Введите количество вакансий для поиска";
    private static final String GREETING_TEXT_2_NUMBER_OF_VACANCIES_NOT_NULL =
            "Или выберите количество вакансий, которое вы искали в прошлый раз";

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

        SearchHistory searchHistory = userService.getUserSearchHistory(user);
        Long numberOfVacancies = searchHistory.getNumberOfVacancies();
        if (numberOfVacancies != null) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> inlineKeyboardButtonsRowOne =
                    List.of(BotUtils.createInlineKeyboardButton(numberOfVacancies, numberOfVacancies));
            inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));

            messagesToUser.add(
                    BotUtils.createMessageTemplate(event.getChatId(), GREETING_TEXT_2_NUMBER_OF_VACANCIES_NOT_NULL, inlineKeyboardMarkup)
            );
        }
        user.setState(State.HH_NUMBER_OF_VACANCIES_SELECT_2.toString());
        userService.save(user);
        return List.copyOf(messagesToUser);
    }

    @Override
    public State getRequiredState() {
        return State.HH_VACANCY_SELECT_1;
    }
}