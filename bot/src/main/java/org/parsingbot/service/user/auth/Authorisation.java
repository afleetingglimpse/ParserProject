package org.parsingbot.service.user.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authorisation {
    DEFAULT("DEFAULT"),
    ADMIN("ADMIN");

    private final String name;

    public static String asString(Authorisation authorisation) {
        return authorisation.getName();
    }
}
