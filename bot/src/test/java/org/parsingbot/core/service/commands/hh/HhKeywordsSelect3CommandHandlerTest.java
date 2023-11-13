package org.parsingbot.core.service.commands.hh;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.commons.entity.*;
import org.parsingbot.commons.service.SearchHistoryService;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.commons.utils.TestHelper;
import org.parsingbot.core.parser.service.ParserService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для класса обработчика команды hh со state = HH_KEYWORDS_SELECT_3")
class HhKeywordsSelect3CommandHandlerTest {
    private static final String FINAL_MESSAGE = "Результаты поиска по запросу вакансия = '%s'," +
            "количество вакансий = '%d', ключевые слова = {%s}";

    @Mock
    private UserService userService;
    @Mock
    private SearchHistoryService searchHistoryService;
    @Mock
    private ParserService parserService;

    @InjectMocks
    private HhKeywordsSelect3CommandHandler sut;

    @Test
    @DisplayName("Тест метода handleCommand, Command.fullMessage is not blank")
    void handleCommand_MessageNotBlankTest() {
        Event event = TestHelper.createEvent();
        User user = event.getUser();
        Long chatId = event.getChatId();
        SearchHistory searchHistory = event.getUser().getSearchHistories().get(0);
        String vacancyName = searchHistory.getVacancyName();
        Long numberOfVacancies = searchHistory.getNumberOfVacancies();
        String keywords = event.getCommand().getFullMessage();

        SendMessage finalMessage = SendMessage.builder()
                .chatId(chatId)
                .text(String.format(FINAL_MESSAGE, vacancyName, numberOfVacancies, keywords))
                .build();

        SendMessage message = SendMessage.builder()
                .chatId(TestHelper.RND.nextLong())
                .text(TestHelper.randomFromUuid())
                .build();
        List<SendMessage> messages = new ArrayList<>();
        messages.add(message);
        when(parserService.getVacanciesMessageList(event)).thenReturn(messages);

        List<SendMessage> expected = new ArrayList<>();
        expected.add(finalMessage);
        expected.add(message);

        List<PartialBotApiMethod<? extends Serializable>> actual = sut.handleCommand(event);

        assertEquals(expected, actual);
        assertEquals(vacancyName, searchHistory.getVacancyName());
        assertEquals(numberOfVacancies, searchHistory.getNumberOfVacancies());
        assertEquals(keywords, searchHistory.getKeywords());
        assertEquals(2, user.getSearchHistories().size());
        assertEquals(State.NONE.toString(), user.getState());

        verify(searchHistoryService, times(3)).save(searchHistory);
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Тест метода handleCommand, Command.fullMessage is blank")
    void handleCommand_MessageIsBlankTest() {
        Event event = TestHelper.createEvent();
        event.setCommand(new Command(""));
        User user = event.getUser();
        Long chatId = event.getChatId();
        SearchHistory searchHistory = event.getUser().getSearchHistories().get(0);
        String vacancyName = searchHistory.getVacancyName();
        Long numberOfVacancies = searchHistory.getNumberOfVacancies();

        SendMessage finalMessage = SendMessage.builder()
                .chatId(chatId)
                .text(String.format(FINAL_MESSAGE, vacancyName, numberOfVacancies, null))
                .build();

        SendMessage message = SendMessage.builder()
                .chatId(TestHelper.RND.nextLong())
                .text(TestHelper.randomFromUuid())
                .build();
        List<SendMessage> messages = new ArrayList<>();
        messages.add(message);
        when(parserService.getVacanciesMessageList(event)).thenReturn(messages);

        List<SendMessage> expected = new ArrayList<>();
        expected.add(finalMessage);
        expected.add(message);

        List<PartialBotApiMethod<? extends Serializable>> actual = sut.handleCommand(event);

        assertEquals(expected, actual);
        assertEquals(vacancyName, searchHistory.getVacancyName());
        assertEquals(numberOfVacancies, searchHistory.getNumberOfVacancies());
        assertNull(searchHistory.getKeywords());
        assertEquals(2, user.getSearchHistories().size());
        assertEquals(State.NONE.toString(), user.getState());

        verify(searchHistoryService, times(3)).save(searchHistory);
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Тест метода getRequiredState")
    void getRequiredState() {
        assertEquals(State.HH_KEYWORDS_SELECT_3, sut.getRequiredState());
    }
}