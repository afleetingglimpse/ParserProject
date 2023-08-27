package org.parsingbot.service.commands;


import org.parsingbot.entity.CommandDto;

/**
 * Интерфейс диспетчера обработчиков команд
 */
public interface CommandHandlerDispatcher {

    /**
     * @param command команда
     * @return диспетчер, обрабатывающий входящую команду
     */
    CommandHandler getCommandHandler(CommandDto command);

}
