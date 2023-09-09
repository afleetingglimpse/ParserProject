package org.parsingbot.service.receiver.checkers;

import org.parsingbot.entity.CommandDto;
import org.parsingbot.entity.User;

/**
 * Интерфейс сервиса проверки авторизации пользователя
 */
public interface CommandAuthorisationChecker {

    /**
     * Метод проверки авторизации пользователя для команды
     *
     * @param commandDto обертка над сообщением-командой пользователя
     * @param user объект пользователя
     * @return сообщение об ошибке или null
     */
    String checkCommandAuthorisation(CommandDto commandDto, User user);
}