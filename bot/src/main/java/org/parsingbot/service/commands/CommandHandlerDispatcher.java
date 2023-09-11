package org.parsingbot.service.commands;


import org.parsingbot.entity.Command;
import org.parsingbot.entity.User;

/**
 * Интерфейс диспетчера обработчиков команд
 */
public interface CommandHandlerDispatcher {

    /**
     * Метод реализующий логику выбора обработчика в зависимости от полученной команды
     *
     * @param command команда
     * @param user объект пользователя
     * @return диспетчер, обрабатывающий входящую команду
     */
    CommandHandler getCommandHandler(Command command, User user);
}