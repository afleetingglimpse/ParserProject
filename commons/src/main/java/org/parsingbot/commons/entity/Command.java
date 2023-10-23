package org.parsingbot.commons.entity;

import lombok.Getter;

@Getter
public class Command {
    private final String prefix;
    private final String fullMessage;
    private String messageWithoutPrefix;

    public Command(String message) {
        fullMessage = message;
        prefix = getPrefix(message);
    }

    private String getPrefix(String message) {
        String prefix = message.split(" ")[0];
        messageWithoutPrefix = message.substring(prefix.length());
        return prefix.charAt(0) == '/' ? prefix : null;
    }
}
