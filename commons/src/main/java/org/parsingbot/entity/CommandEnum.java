package org.parsingbot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.parsingbot.service.Authorisation;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CommandEnum {

    HH_COMMAND("/hh", Authorisation.USER),
    SUBSCRIBE_COMMAND("/subscribe", Authorisation.USER),
    UNSUBSCRIBE_COMMAND("/unsubscribe", Authorisation.USER),
    DROP_COMMAND("/drop", Authorisation.ADMIN),
    HELP_COMMAND("/help", Authorisation.USER);

    private final String prefix;
    private final Authorisation minimumAuthorisation;

    public static boolean contains(Command command) {
        return Arrays.stream(CommandEnum.values()).anyMatch(commandEnum ->
                commandEnum.prefix.equals(command.getPrefix()));
    }

    public static boolean contains(String command) {
        return Arrays.stream(CommandEnum.values()).anyMatch(commandEnum ->
                commandEnum.prefix.equals(command));
    }
}