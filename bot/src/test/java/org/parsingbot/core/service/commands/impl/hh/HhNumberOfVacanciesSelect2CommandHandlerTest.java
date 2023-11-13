package org.parsingbot.core.service.commands.impl.hh;

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
import org.parsingbot.core.service.commands.hh.HhNumberOfVacanciesSelect2CommandHandler;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.parsingbot.commons.utils.TestHelper.RND;
import static org.parsingbot.commons.utils.TestHelper.randomFromUuid;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для класса обработчика команды hh со state = HH_NUMBER_OF_VACANCIES_SELECT_2")
class HhNumberOfVacanciesSelect2CommandHandlerTest {
    private static final String GREETING_TEXT_3 =
            "ПОКА НЕ РАБОТАЕТ!! Введите ключевые слова для поиска (с любым разделителем)";
    private static final String GREETING_TEXT_3_KEYWORDS_NOT_NULL =
            "Или выберите те ключевые слова, с которыми вы искали в прошлый раз";

    @Mock
    private UserService userService;
    @Mock
    private SearchHistoryService searchHistoryService;
    @InjectMocks
    private HhNumberOfVacanciesSelect2CommandHandler sut;

    @Test
    @DisplayName("Тест метода handleCommand, Command.fullMessage is number, " +
            "searchHistory.keywords is blank")
    void handleCommand_MessageNotBlank_KeywordsBlankTest() {
        Event event = TestHelper.createEvent();
        Long chatId = event.getChatId();
        User user = event.getUser();
        SearchHistory searchHistory = user.getSearchHistories().get(0);

        // Метод StringUtils.isNumeric не принимает числа меньше нуля
        Command command = new Command(String.valueOf(RND.nextInt(0, Integer.MAX_VALUE)));
        event.setCommand(command);

        SendMessage greetingMessage = SendMessage.builder()
                .chatId(chatId)
                .text(GREETING_TEXT_3)
                .build();
        List<SendMessage> expected = List.of(greetingMessage);

        List<PartialBotApiMethod<? extends Serializable>> actual = sut.handleCommand(event);

        assertEquals(expected, actual);
        assertEquals(State.HH_KEYWORDS_SELECT_3.toString(), user.getState());
        assertEquals(event.getCommand().getFullMessage(), searchHistory.getNumberOfVacancies().toString());

        verify(searchHistoryService).save(searchHistory);
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Тест метода handleCommand, Command.fullMessage is not number, " +
            "searchHistory.keywords = SearchHistoryUtils.DEFAULT_KEYWORDS")
    void handleCommand_MessageBlank_KeywordsDefaultTest() {
        Event event = TestHelper.createEvent();
        Long chatId = event.getChatId();
        User user = event.getUser();
        Command blankCommand = new Command("");
        event.setCommand(blankCommand);

        String defaultKeywords = "none";
        SearchHistory searchHistory = user.getSearchHistories().get(0);
        searchHistory.setKeywords(defaultKeywords);

        SendMessage greetingMessage = SendMessage.builder()
                .chatId(chatId)
                .text(GREETING_TEXT_3)
                .build();

        List<SendMessage> expected = List.of(greetingMessage);

        List<PartialBotApiMethod<? extends Serializable>> actual = sut.handleCommand(event);

        assertEquals(expected, actual);
        assertEquals(State.HH_KEYWORDS_SELECT_3.toString(), user.getState());
        assertNull(searchHistory.getNumberOfVacancies());

        verify(searchHistoryService).save(searchHistory);
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Тест метода handleCommand, Command.fullMessage is not number, " +
            "searchHistory.keywords != SearchHistoryUtils.DEFAULT_KEYWORDS")
    void handleCommand_MessageBlank_KeywordsNotDefaultTest() {
        Event event = TestHelper.createEvent();
        Long chatId = event.getChatId();
        User user = event.getUser();
        Command blankCommand = new Command("");
        event.setCommand(blankCommand);

        String defaultKeywords = randomFromUuid();
        SearchHistory searchHistory = user.getSearchHistories().get(0);
        searchHistory.setKeywords(defaultKeywords);

        SendMessage greetingMessage = SendMessage.builder()
                .chatId(chatId)
                .text(GREETING_TEXT_3)
                .build();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne =
                List.of(InlineKeyboardButton.builder()
                        .text(defaultKeywords)
                        .callbackData(defaultKeywords)
                        .build());
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        SendMessage keyboardMessage = SendMessage.builder()
                .chatId(chatId)
                .text(GREETING_TEXT_3_KEYWORDS_NOT_NULL)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        List<SendMessage> expected = List.of(greetingMessage, keyboardMessage);

        List<PartialBotApiMethod<? extends Serializable>> actual = sut.handleCommand(event);

        assertEquals(expected, actual);
        assertEquals(State.HH_KEYWORDS_SELECT_3.toString(), user.getState());
        assertNull(searchHistory.getNumberOfVacancies());

        verify(searchHistoryService).save(searchHistory);
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Тест метода getRequiredState")
    void getRequiredStateTest() {
        assertEquals(State.HH_NUMBER_OF_VACANCIES_SELECT_2, sut.getRequiredState());
    }
}