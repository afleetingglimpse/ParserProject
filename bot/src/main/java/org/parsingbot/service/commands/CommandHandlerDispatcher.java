package org.parsingbot.service.commands;


import org.parsingbot.entity.CommandDto;

/**
 * Интерфейс диспетчера обработчиков команд
 */
public interface CommandHandlerDispatcher {

    /**
     * Метод реализующий логику выбора обработчика в зависимости от полученной команды
     *
     * @param command команда
     * @return диспетчер, обрабатывающий входящую команду
     */
    CommandHandler getCommandHandler(CommandDto command);
}