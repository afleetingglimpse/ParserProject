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
import org.parsingbot.core.parser.service.ParserService;
import org.parsingbot.core.service.commands.CommandHandler;
import org.parsingbot.core.util.BotUtils;
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
    private final SearchHistoryService searchHistoryService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        User user = event.getUser();

        SearchHistory searchHistory = SearchHistoryUtils.getLastSearchHistoryOrCreateNew(user);
        String keywords = event.getCommand().getFullMessage();
        if (StringUtils.isNotBlank(keywords)) {
            searchHistory.setKeywords(keywords);
        }

        List<SendMessage> messagesToUser = new ArrayList<>();
        messagesToUser.add(BotUtils.createMessage(
                event.getChatId(),
                String.format(FINAL_MESSAGE,
                        searchHistory.getVacancyName(),
                        searchHistory.getNumberOfVacancies(),
                        searchHistory.getKeywords()))
        );

        List<SendMessage> vacancies = parserService.getVacanciesMessageList(event);
        messagesToUser.addAll(vacancies);

        searchHistoryService.save(searchHistory);
        SearchHistory newSearchHistory = new SearchHistory(searchHistory);
        user.addSearchHistory(newSearchHistory);
        searchHistoryService.save(searchHistory);
        searchHistoryService.save(newSearchHistory);

        user.setState(State.NONE.toString());
        userService.save(user);
        return List.copyOf(messagesToUser);
    }

    @Override
    public State getRequiredState() {
        return State.HH_KEYWORDS_SELECT_3;
    }
}