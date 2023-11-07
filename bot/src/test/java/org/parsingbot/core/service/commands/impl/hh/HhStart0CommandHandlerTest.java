package org.parsingbot.core.service.commands.impl.hh;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.service.SearchHistoryService;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.commons.utils.TestHelper;
import org.parsingbot.core.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для класса обработчика команды hh со state = NONE")
class HhStart0CommandHandlerTest {

    private static final String TYPE_VACANCY_NAME = "Введите название вакансии для поиска";
    private static final String POPULAR_VACANCIES_TEXT = "Или выберете из списка популярных";
    private static final Map<String, String> POPULAR_VACANCIES = Map.of(
            "Java developer", "Java",
            "Python developer", "Python"
    );

    @Mock
    private UserService userService;
    @Mock
    private SearchHistoryService searchHistoryService;

    @InjectMocks
    private HhStart0CommandHandler sut;

    @Test
    @DisplayName("Тест метода handleCommand")
    void handleCommandTest() {
        Event event = TestHelper.createEvent();
        User user = event.getUser();
        Long chatId = event.getChatId();

        SendMessage typeVacancyNameMessage = SendMessage.builder()
                .chatId(chatId)
                .text(TYPE_VACANCY_NAME)
                .build();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = POPULAR_VACANCIES.keySet().stream()
                .map(key -> BotUtils.createInlineKeyboardButton(key, POPULAR_VACANCIES.get(key)))
                .toList();
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        SendMessage popularVacanciesTextMessage = SendMessage.builder()
                .chatId(chatId)
                .text(POPULAR_VACANCIES_TEXT)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
        List<SendMessage> expected = List.of(typeVacancyNameMessage, popularVacanciesTextMessage);

        List<PartialBotApiMethod<? extends Serializable>> actual = sut.handleCommand(event);

        assertEquals(expected, actual);

        verify(searchHistoryService).save(user.getSearchHistories().get(0));
        verify(userService).save(user);
    }
}