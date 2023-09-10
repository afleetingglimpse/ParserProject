package org.parsingbot.service.receiver.checkers.impl;

import org.parsingbot.entity.CommandDto;
import org.parsingbot.entity.CommandEnum;
import org.parsingbot.entity.User;
import org.parsingbot.service.Authorisation;
import org.parsingbot.service.receiver.checkers.CommandChecker;

import java.util.Arrays;
import java.util.Optional;

public class CommandCheckerImpl implements CommandChecker {

    private static final String COMMAND_NOT_FOUND_ERROR = "Command not found";
    private static final String NOT_AUTHORISED_FOR_COMMAND_ERROR = "You are not authorised to use that command";

    @Override
    public String checkCommand(CommandDto commandDto, User user) {
        Optional<CommandEnum> commandOptional = Arrays.stream(CommandEnum.values())
                .filter(commandEnum -> commandEnum.getPrefix().equals(commandDto.getPrefix()))
                .findAny();
        if (commandOptional.isEmpty()) {
            return COMMAND_NOT_FOUND_ERROR;
        }
        Authorisation minimumCommandAuthorisation = commandOptional.get().getMinimumAuthorisation();
        if (Authorisation.compare(Authorisation.valueOf(user.getAuthorisation()), minimumCommandAuthorisation) >= 0) {
            return NOT_AUTHORISED_FOR_COMMAND_ERROR;
        }
        return null;
    }
}