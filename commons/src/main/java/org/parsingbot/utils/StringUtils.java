package org.parsingbot.utils;

import java.util.List;

public class StringUtils {

    // pattern: /hh java 10
    public static List<String> parseHhCommand(String command) {
        String[] split = command.split(" ");
        if (!checkString(command)) {
            throw new IllegalArgumentException("Invalid command: command should not be null or blank");
        }
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

    public static boolean checkString(String toCheck) {
        return toCheck != null && !toCheck.isEmpty();
    }

}
