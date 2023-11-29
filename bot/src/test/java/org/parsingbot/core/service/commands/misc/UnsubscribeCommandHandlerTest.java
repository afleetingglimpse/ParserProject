package org.parsingbot.core.service.commands.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.service.Authorisation;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.commons.utils.TestHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для класса обработчика команды unsubscribe")
class UnsubscribeCommandHandlerTest {

    private static final String USER_NOT_SUBSCRIBED = "User is not subscribed";
    private static final String SUCCESSFUL_UNSUBSCRIBE = "You have been successfully unsubscribed";

    @Mock
    private UserService userService;

    @InjectMocks
    private UnsubscribeCommandHandler sut;

    @Test
    @DisplayName("Тест метода handleCommand. Пользователь не подписан")
    void handleCommandUserAlreadySubscribed() {
        Event event = TestHelper.createEvent();
        User user = event.getUser();
        user.setIsSubscribed(false);

        List<SendMessage> expected = List.of(SendMessage.builder()
                .chatId(event.getChatId())
                .text(USER_NOT_SUBSCRIBED)
                .build());

        assertEquals(expected, sut.handleCommand(event));

        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("Тест метода handleCommand. Пользователь успешно отписан")
    void handleCommand() {
        Event event = TestHelper.createEvent();
        User user = event.getUser();
        user.setIsSubscribed(true);

        List<SendMessage> expected = List.of(SendMessage.builder()
                .chatId(event.getChatId())
                .text(SUCCESSFUL_UNSUBSCRIBE)
                .build());

        assertEquals(expected, sut.handleCommand(event));

        verify(userService).save(user);
    }

    @Test
    @DisplayName("Тест метода getRequiredState")
    void getRequiredState() {
        assertEquals(State.NONE, sut.getRequiredState());
    }

    @Test
    @DisplayName("Тест метода getRequiredAuthorisation")
    void getRequiredAuthorisation() {
        assertEquals(Authorisation.USER, sut.getRequiredAuthorisation());
    }
}