package org.parsingbot.entity;

import lombok.Getter;

@Getter
public class CommandDto {
    private final String prefix;
    private final String fullMessage;

    public static final CommandDto hhCommand = new CommandDto("/hh");

    public CommandDto(String message) {
        fullMessage = message;
        prefix = getPrefix(message);
    }

    private String getPrefix(String message) {
        String prefix = message.split(" ")[0];
        return prefix.charAt(0) == '/' ? prefix : null;
    }
}
