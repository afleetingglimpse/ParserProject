package org.parsingbot.commons.service;

/**
 * Сервис авторизации пользователей
 */
public interface UserAuthService {

    /**
     * Проверка авторизации пользователей
     *
     * @param userName      имя пользователя
     * @param minimumAuthorisation необходимая авторизация
     * @return true/false если у пользователя есть/нет прав для просмотра
     */
    boolean isAuthorised(String userName, Authorisation minimumAuthorisation);

    /**
     * Установка авторизации пользователя по id
     *
     * @param id            id пользователя
     * @param authorisation устанавливаемая авторизация
     */
    void setAuthorisedById(Long id, Authorisation authorisation);

}
