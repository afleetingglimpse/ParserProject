package org.parsingbot.service.receiver.checkers.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.parsingbot.service.receiver.checkers.UpdateChecker;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка класса UpdateCheckerImpl")
class UpdateCheckerImplTest {

    private static final String VALID_USER_NAME = "gwynae";
    private static final String VALID_MESSAGE_TEXT = "Скажи-ка, дядя, ведь не даром..";
    private static final String INVALID_UPDATE_ERROR = "userName or/and message not valid";

    private final UpdateChecker updateChecker = new UpdateCheckerImpl();

    @ParameterizedTest
    @NullSource
    @DisplayName("Тест метода checkUpdate с null параметром")
    void nullUpdateCheckTest(Update update) {
        assertThrows(NullPointerException.class, () -> updateChecker.checkUpdate(update));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Тест метода checkUpdate с null/empty userName")
    void checkUpdate_nullUserNameTest(String userName) {
        Update update = createUpdate(userName, VALID_MESSAGE_TEXT);
        String actual = updateChecker.checkUpdate(update);
        assertEquals(INVALID_UPDATE_ERROR, actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Тест метода checkUpdate с null/empty messageText")
    void checkUpdate_nullMessageTextTest(String messageText) {
        Update update = createUpdate(VALID_USER_NAME, messageText);
        String actual = updateChecker.checkUpdate(update);
        assertEquals(INVALID_UPDATE_ERROR, actual);
    }

    @Test
    @DisplayName("Тест метода checkUpdate с валидным параметром")
    void checkUpdateValidTest() {
        Update update = createUpdate(VALID_USER_NAME, VALID_MESSAGE_TEXT);
        assertNull(updateChecker.checkUpdate(update));
    }

    private Update createUpdate(String userName, String messageText) {
        Chat chat = new Chat();
        chat.setUserName(userName);

        Message message = new Message();
        message.setText(messageText);
        message.setChat(chat);

        Update update = new Update();
        update.setMessage(message);
        return update;
    }
}