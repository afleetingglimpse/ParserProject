package org.parsingbot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandEnum {

    HH_COMMAND(new CommandDto("/hh"));

    private final CommandDto commandDto;
}
