package org.parsingbot.service.receiver.checkers;

import org.parsingbot.entity.Command;
import org.parsingbot.entity.User;

/**
 * Интерфейс сервиса проверки авторизации пользователя
 */
public interface CommandChecker {

    /**
     * Метод валидации команды от пользователя
     *
     * @param command обертка над сообщением-командой пользователя
     * @param user       объект пользователя
     * @return сообщение об ошибке или null
     */
    String checkCommand(Command command, User user);
}