package org.parsingbot.service.commands.impl.hh;

import org.parsingbot.entity.Command;
import org.parsingbot.service.commands.CommandParser;
import org.parsingbot.utils.StringUtils;

import java.util.Map;

public class HhCommandParser implements CommandParser {

    private static final String DEFAULT_VACANCY_NAME = "java";
    private static final String DEFAULT_NUMBER_OF_VACANCIES = String.valueOf(5);
    static Map<String, String> DEFAULT_PARSED_COMMAND = Map.of(
            "vacancyName", DEFAULT_VACANCY_NAME,
            "numberOfVacancies", DEFAULT_NUMBER_OF_VACANCIES
    );

    // TODO fields checks
    @Override
    public Map<String, String> parseCommand(Command command) {
        if (!StringUtils.checkString(command.getFullMessage())) {
            return DEFAULT_PARSED_COMMAND;
        }

        String[] split = command.getFullMessage().split(" ");
        if (split.length < 2 || !StringUtils.checkString(split[1])) {
            return DEFAULT_PARSED_COMMAND;
        }
        String vacancyName = split[1];

        if (split.length < 3 || !StringUtils.checkString(split[2])) {
            return DEFAULT_PARSED_COMMAND;
        }
        String numberOfVacancies = split[2];

        return Map.of(
                "vacancyName", vacancyName,
                "numberOfVacancies", numberOfVacancies
        );
    }
}