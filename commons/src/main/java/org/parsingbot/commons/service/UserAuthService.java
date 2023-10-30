package org.parsingbot.commons.service;

import org.parsingbot.commons.entity.User;

/**
 * Сервис авторизации пользователей
 */
public interface UserAuthService {

    /**
     * Проверка авторизации пользователей
     *
     * @param user                 объект пользователя
     * @param minimumAuthorisation необходимая авторизация
     * @return true/false если у пользователя есть/нет прав для просмотра
     */
    boolean isAuthorised(User user, Authorisation minimumAuthorisation);

    /**
     * Установка авторизации пользователя по id
     *
     * @param user          объект пользователя
     * @param authorisation устанавливаемая авторизация
     */
    void setAuthorisedById(User user, Authorisation authorisation);

}
