package org.parsingbot.commons.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Authorisation {
    UNKNOWN("UNKNOWN"),
    USER("USER"),
    ADMIN("ADMIN"),
    DUNGEON_MASTER("DUNGEON_MASTER");

    private final String name;

    /**
     * Возвращает число меньше 0 если а1 < a2, 0 если равны, больше 0 если a1 > a2
     */
    public static int compare(Authorisation a1, Authorisation a2) {
        if (!Arrays.asList(Authorisation.values()).contains(a1)) {
            a1 = Authorisation.UNKNOWN;
        }
        if (!Arrays.asList(Authorisation.values()).contains(a2)) {
            a2 = Authorisation.UNKNOWN;
        }
        return a1.ordinal() - a2.ordinal();
    }
}