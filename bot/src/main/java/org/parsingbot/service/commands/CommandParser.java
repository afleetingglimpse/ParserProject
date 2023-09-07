package org.parsingbot.service.commands;

import org.parsingbot.entity.CommandDto;

import java.util.Map;

/**
 * Класс, который инжектится в CommandHandler для парсинга входящей команды
 */
public interface CommandParser {

    /**
     * @param command обертка над командой юзера
     * @return мапа параметров, найденных в команде
     */
    Map<String, String> parseCommand(CommandDto command);
}
