package org.parsingbot.utils;

import java.util.List;

public class StringUtils {

    public static boolean checkString(String toCheck) {
        return toCheck != null && !toCheck.isEmpty();
    }

    @Deprecated(forRemoval = true)
    public static List<String> parseHhCommand(String command) {
        if (!checkString(command)) {
            throw new IllegalArgumentException("Invalid command: command should not be null or blank");
        }

        String[] split = command.split(" ");
        if (split.length < 1 || !split[0].equals("/hh")) {
            throw new IllegalArgumentException("Invalid command: command should start with /hh");
        }
        if (split.length < 2 || !checkString(split[1])) {
            throw new IllegalArgumentException("Invalid command: command should contain valid vacancy name");
        }
        if (split.length < 3 || !checkString(split[2])) {
            throw new IllegalArgumentException("Invalid command: command should contain valid number of vacancies");
        }
        return List.of(split[1], split[2]);
    }
}