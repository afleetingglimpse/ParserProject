package org.parsingbot.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authorisation {
    DEFAULT("DEFAULT"),
    USER("USER"),
    ADMIN("ADMIN"),
    DUNGEON_MASTER("DUNGEON_MASTER");

    private final String name;

    public static String asString(Authorisation authorisation) {
        return authorisation.getName().toLowerCase();
    }
}
