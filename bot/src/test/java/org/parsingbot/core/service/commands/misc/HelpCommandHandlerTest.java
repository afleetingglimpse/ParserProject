package org.parsingbot.core.service.commands.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.parsingbot.commons.entity.CommandEnum;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.utils.TestHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для класса обработчика команды help")
class HelpCommandHandlerTest {

    private static final String AVAILABLE_COMMANDS_MESSAGE = "Доступные команды:\n\n";

    private final HelpCommandHandler sut = new HelpCommandHandler();

    @Test
    @DisplayName("Тест метода handleCommand")
    void handleCommand() {
        Event event = TestHelper.createEvent();
        CommandEnum[] AVAILABLE_COMMANDS = CommandEnum.values();

        StringBuilder sb = new StringBuilder();
        sb.append(AVAILABLE_COMMANDS_MESSAGE);
        for (CommandEnum command : AVAILABLE_COMMANDS) {
            sb.append(command.getPrefix()).append("\n");
        }

        List<SendMessage> expected = List.of(SendMessage.builder()
                .chatId(event.getChatId())
                .text(sb.toString())
                .build());

        assertEquals(expected, sut.handleCommand(event));
    }

    @Test
    @DisplayName("Тест метода getRequiredState")
    void getRequiredState() {
        assertEquals(State.ANY, sut.getRequiredState());
    }
}