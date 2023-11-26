package org.parsingbot.core.service.commands.misc;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.commons.entity.Command;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для класса обработчика команды drop")
class DropStateCommandHandlerTest {

    private static final String STATE_CHANGED = "State changed to %s";

    @Mock
    private UserService userService;

    @InjectMocks
    private DropStateCommandHandler sut;

    @Test
    @DisplayName("Тест метода handleCommand, исключение при попытке определить новый state")
    void handleCommandExceptionTest() {
        Event event = TestHelper.createEvent();
        String command = TestHelper.randomFromUuid();
        event.setCommand(new Command(command));
        Long chatId = event.getChatId();
        User user = event.getUser();

        SendMessage expected = SendMessage.builder()
                .chatId(chatId)
                .text(String.format(STATE_CHANGED, State.NONE))
                .build();

        String expectedWarnLogMessage = String.format("State %s doesn't exist. User.state was set to State.NONE", "");
        String expectedInfoLogMessage = String.format("State for user with chatId %s was set to %s", chatId, State.NONE);

        var logger = TestHelper.getLogger(DropStateCommandHandler.class);
        logger.start();

        assertEquals(List.of(expected), sut.handleCommand(event));

        logger.stop();
        assertEquals(2, logger.list.size());

        assertEquals(Level.WARN, logger.list.get(0).getLevel());
        assertEquals(expectedWarnLogMessage, logger.list.get(0).getFormattedMessage());

        assertEquals(Level.INFO, logger.list.get(1).getLevel());
        assertEquals(expectedInfoLogMessage, logger.list.get(1).getFormattedMessage());

        assertEquals(State.NONE.toString(), user.getState());

        verify(userService).save(event.getUser());
    }

    @Test
    @DisplayName("Тест метода handleCommand, корректно определен новый state")
    void handleCommandTest() {
        Event event = TestHelper.createEvent();
        String newState = State.ANY.toString();
        String newStateCommand = "/drop " + newState;
        event.setCommand(new Command(newStateCommand));
        Long chatId = event.getChatId();

        SendMessage expected = SendMessage.builder()
                .chatId(chatId)
                .text(String.format(STATE_CHANGED, newState))
                .build();

        String expectedInfoLogMessage = String.format("State for user with chatId %s was set to %s", chatId, State.ANY);

        var logger = TestHelper.getLogger(DropStateCommandHandler.class);
        logger.start();

        assertEquals(List.of(expected), sut.handleCommand(event));

        logger.stop();
        assertEquals(1, logger.list.size());

        assertEquals(Level.INFO, logger.list.get(0).getLevel());
        assertEquals(expectedInfoLogMessage, logger.list.get(0).getFormattedMessage());

        verify(userService).save(event.getUser());
    }

    @Test
    @DisplayName("Тест метода getRequiredState")
    void getRequiredStateTest() {
        assertEquals(State.ANY, sut.getRequiredState());
    }

    @Test
    @DisplayName("Тест метода getRequiredAuthorisation")
    void getRequiredAuthorisationTest() {
        assertEquals(Authorisation.ADMIN, sut.getRequiredAuthorisation());
    }
}