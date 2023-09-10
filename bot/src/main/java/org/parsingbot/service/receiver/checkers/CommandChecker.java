package org.parsingbot.service.receiver.checkers;

import org.parsingbot.entity.CommandDto;
import org.parsingbot.entity.User;

/**
 * Интерфейс сервиса проверки авторизации пользователя
 */
public interface CommandChecker {

    /**
     * Метод валидации команды от пользователя
     *
     * @param commandDto обертка над сообщением-командой пользователя
     * @param user       объект пользователя
     * @return сообщение об ошибке или null
     */
    String checkCommand(CommandDto commandDto, User user);
}