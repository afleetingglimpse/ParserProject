package org.parsingbot.core.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.repository.SearchHistoryRepository;
import org.parsingbot.commons.service.SearchHistoryService;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.commons.utils.SearchHistoryUtils;
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
public class HhNumberOfVacanciesSelect2CommandHandler implements CommandHandler {

    private static final String GREETING_TEXT_3 =
            "ПОКА НЕ РАБОТАЕТ!! Введите ключевые слова для поиска (с любым разделителем)";
    private static final String GREETING_TEXT_3_KEYWORDS_NOT_NULL =
            "Или выберите те ключевые слова, с которыми вы искали в прошлый раз";

    private final UserService userService;
    private final SearchHistoryService searchHistoryService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        User user = event.getUser();

        SearchHistory searchHistory = SearchHistoryUtils.getLastSearchHistoryOrCreateNew(user);
        if (StringUtils.isNumeric(event.getCommand().getFullMessage())) {
            Long numberOfVacancies = Long.parseLong(event.getCommand().getFullMessage());
            searchHistory.setNumberOfVacancies(numberOfVacancies);
        }

        List<SendMessage> messagesToUser = new ArrayList<>();
        messagesToUser.add(BotUtils.createMessage(event.getChatId(), GREETING_TEXT_3));

        String keywords = searchHistory.getKeywords();
        if (StringUtils.isNotBlank(keywords) && !keywords.equals(SearchHistoryUtils.DEFAULT_KEYWORDS)) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> inlineKeyboardButtonsRowOne =
                    List.of(BotUtils.createInlineKeyboardButton(keywords, keywords));
            inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));

            messagesToUser.add(
                    BotUtils.createMessageTemplate(event.getChatId(), GREETING_TEXT_3_KEYWORDS_NOT_NULL, inlineKeyboardMarkup)
            );
        }

        user.setState(State.HH_KEYWORDS_SELECT_3.toString());
        searchHistoryService.save(searchHistory);
        userService.save(user);
        return List.copyOf(messagesToUser);
    }

    @Override
    public State getRequiredState() {
        return State.HH_NUMBER_OF_VACANCIES_SELECT_2;
    }
}
