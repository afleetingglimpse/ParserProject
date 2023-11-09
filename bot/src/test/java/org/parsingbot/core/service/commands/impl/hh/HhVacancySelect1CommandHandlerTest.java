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
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для класса обработчика команды hh со state = HH_VACANCY_SELECT_1")
class HhVacancySelect1CommandHandlerTest {
    private static final String GREETING_TEXT_2 = "Введите количество вакансий для поиска";
    private static final String GREETING_TEXT_2_NUMBER_OF_VACANCIES_NOT_NULL =
            "Или выберите количество вакансий, которое вы искали в прошлый раз";
    @Mock
    private UserService userService;
    @Mock
    private SearchHistoryService searchHistoryService;
    @InjectMocks
    private HhVacancySelect1CommandHandler sut;

    @Test
    @DisplayName("Тест метода handleCommand, Command.fullMessage not blank, " +
            "searchHistory.numberOfVacancies = SearchHistoryUtils.DEFAULT_NUMBER_OF_VACANCIES")
    void handleCommand_MessageNotBlank_DefaultNumberOfVacanciesTest() {
        Event event = TestHelper.createEvent();
        Long chatId = event.getChatId();
        User user = event.getUser();
        SearchHistory searchHistory = user.getSearchHistories().get(0);
        searchHistory.setNumberOfVacancies(0L);

        SendMessage greetingMessage = SendMessage.builder()
                .chatId(chatId)
                .text(GREETING_TEXT_2)
                .build();
        List<SendMessage> expected = List.of(greetingMessage);

        List<PartialBotApiMethod<? extends Serializable>> actual = sut.handleCommand(event);

        assertEquals(expected, actual);
        assertEquals(State.HH_NUMBER_OF_VACANCIES_SELECT_2.toString(), user.getState());
        assertEquals(event.getCommand().getFullMessage(), searchHistory.getVacancyName());

        verify(searchHistoryService).save(searchHistory);
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Тест метода handleCommand, Command.fullMessage not blank, " +
            "searchHistory.numberOfVacancies = null")
    void handleCommand_MessageNotBlank_NumberOfVacanciesIsBlankTest() {
        Event event = TestHelper.createEvent();
        Long chatId = event.getChatId();
        User user = event.getUser();
        SearchHistory searchHistory = user.getSearchHistories().get(0);

        SendMessage greetingMessage = SendMessage.builder()
                .chatId(chatId)
                .text(GREETING_TEXT_2)
                .build();
        List<SendMessage> expected = List.of(greetingMessage);

        List<PartialBotApiMethod<? extends Serializable>> actual = sut.handleCommand(event);

        assertEquals(expected, actual);
        assertEquals(State.HH_NUMBER_OF_VACANCIES_SELECT_2.toString(), user.getState());
        assertEquals(event.getCommand().getFullMessage(), searchHistory.getVacancyName());

        verify(searchHistoryService).save(searchHistory);
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Тест метода handleCommand, Command.fullMessage is blank, " +
            "searchHistory.numberOfVacancies != SearchHistoryUtils.DEFAULT_NUMBER_OF_VACANCIES")
    void handleCommand_MessageBlank_NumberOfVacanciesNotDefaultTest() {
        Event event = TestHelper.createEvent();
        Long chatId = event.getChatId();
        User user = event.getUser();
        Command blankCommand = new Command("");
        event.setCommand(blankCommand);

        Long numberOfVacancies = TestHelper.RND.nextLong();
        SearchHistory searchHistory = user.getSearchHistories().get(0);
        searchHistory.setNumberOfVacancies(numberOfVacancies);

        SendMessage greetingMessage = SendMessage.builder()
                .chatId(chatId)
                .text(GREETING_TEXT_2)
                .build();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne =
                List.of(InlineKeyboardButton.builder()
                        .text(numberOfVacancies.toString())
                        .callbackData(numberOfVacancies.toString())
                        .build());
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        SendMessage keyboardMessage = SendMessage.builder()
                .chatId(chatId)
                .text(GREETING_TEXT_2_NUMBER_OF_VACANCIES_NOT_NULL)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        List<SendMessage> expected = List.of(greetingMessage, keyboardMessage);

        List<PartialBotApiMethod<? extends Serializable>> actual = sut.handleCommand(event);

        assertEquals(expected, actual);
        assertEquals(State.HH_NUMBER_OF_VACANCIES_SELECT_2.toString(), user.getState());
        assertNull(searchHistory.getVacancyName());

        verify(searchHistoryService).save(searchHistory);
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Тест метода getRequiredState")
    void getRequiredStateTest() {
        assertEquals(State.HH_VACANCY_SELECT_1, sut.getRequiredState());
    }
}