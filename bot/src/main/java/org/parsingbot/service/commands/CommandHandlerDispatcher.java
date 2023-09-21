package org.parsingbot.service.commands;


import org.parsingbot.entity.Command;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;

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