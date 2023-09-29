package org.parsingbot.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum Authorisation {
    UNKNOWN("UNKNOWN"),
    USER("USER"),
    ADMIN("ADMIN"),
    DUNGEON_MASTER("DUNGEON_MASTER");

    private final String name;

    public static String asString(Authorisation authorisation) {
        return authorisation.getName().toLowerCase();
    }

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