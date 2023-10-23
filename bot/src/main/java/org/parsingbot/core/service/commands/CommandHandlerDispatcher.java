package org.parsingbot.core.service.commands;


import org.parsingbot.commons.entity.Event;

/**
 * Интерфейс диспетчера обработчиков команд
 */
public interface CommandHandlerDispatcher {

    /**
     * Метод реализующий логику выбора обработчика в зависимости от полученной команды
     *
     * @param event обертка над update, user и command
     * @return диспетчер, обрабатывающий входящую команду
     */
    CommandHandler getCommandHandler(Event event);
}