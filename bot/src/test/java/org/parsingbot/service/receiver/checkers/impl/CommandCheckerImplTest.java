package org.parsingbot.service.receiver.checkers.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.parsingbot.entity.Command;
import org.parsingbot.entity.CommandEnum;
import org.parsingbot.entity.User;
import org.parsingbot.service.Authorisation;
import org.parsingbot.service.receiver.checkers.CommandChecker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тест класса валидации команд CommandCheckerImpl")
class CommandCheckerImplTest {

    private static final String COMMAND_NOT_FOUND_ERROR = "Command not found";
    private static final String NOT_AUTHORISED_FOR_COMMAND_ERROR = "You are not authorised to use that command";

    private final CommandChecker commandChecker = new CommandCheckerImpl();

    @Test
    @DisplayName("Тест метода checkCommand для команды, которой нет в CommandEnum")
    void checkCommand_InvalidCommandTest() {
        Command invalidCommand = new Command("invalidCommand");
        assertEquals(COMMAND_NOT_FOUND_ERROR, commandChecker.checkCommand(invalidCommand, null));
    }

    @Test
    @DisplayName("Тест метода checkCommand для неавторизованного пользователя")
    void checkCommand_UserNotAuthorisedTest() {
        Command validCommand = new Command(CommandEnum.HH_COMMAND.getPrefix());
        User user = User.builder().authorisation(Authorisation.UNKNOWN.getName()).build();
        assertEquals(NOT_AUTHORISED_FOR_COMMAND_ERROR, commandChecker.checkCommand(validCommand, user));
    }

    @Test
    @DisplayName("Тест метода checkCommand с валидными параметрами")
    void checkCommandValidTest() {
        Command validCommand = new Command(CommandEnum.HH_COMMAND.getPrefix());
        User user = User.builder().authorisation(Authorisation.ADMIN.getName()).build();
        assertNull(commandChecker.checkCommand(validCommand, user));
    }
}