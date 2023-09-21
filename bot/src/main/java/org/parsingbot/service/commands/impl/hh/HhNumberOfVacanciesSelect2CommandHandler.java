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
public class HhNumberOfVacanciesSelect2CommandHandler implements CommandHandler {

    private static final String GREETING_TEXT_3 = "Введите ключевые слова для поиска (с любым разделителем)";
    private static final String GREETING_TEXT_3_KEYWORDS_NOT_NULL =
            "Оставьте пустым, чтобы увидеть вакансии с такими ключевыми словами, которые вы искали в прошлый раз (%d)";
    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        User user = event.getUser();

        Long numberOfVacancies = Long.parseLong(event.getCommand().getFullMessage());
        if (numberOfVacancies != null) {
            user.setNumberOfVacancies(numberOfVacancies);
            userService.save(user);
        }

        List<SendMessage> messagesToUser = new ArrayList<>();
        messagesToUser.add(BotUtils.createMessage(event.getChatId(), GREETING_TEXT_3));

        String keywords = userService.getKeywordsByUserId(user.getId());
        if (StringUtils.isNotBlank(keywords)) {
            messagesToUser.add(BotUtils.createMessage(event.getChatId(), GREETING_TEXT_3_KEYWORDS_NOT_NULL));
        }

        userService.updateStateByUser(user, State.HH_KEYWORDS_SELECT_3);
        return List.copyOf(messagesToUser);
    }

    @Override
    public State getRequiredState() {
        return State.HH_NUMBER_OF_VACANCIES_SELECT_2;
    }
}
