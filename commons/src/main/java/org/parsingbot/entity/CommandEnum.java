package org.parsingbot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandEnum {

    HH_COMMAND(new CommandDto("/hh")),
    SUBSCRIBE_COMMAND(new CommandDto("/subscribe")),
    UNSUBSCRIBE_COMMAND(new CommandDto("/unsubscribe"));

    private final CommandDto commandDto;
}