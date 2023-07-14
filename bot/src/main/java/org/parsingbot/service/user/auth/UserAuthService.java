package org.parsingbot.service.user.auth;

/**
 * Сервис авторизации пользователей
 */
public interface UserAuthService {

    /** Проверка авторизации пользователей
     *  @param userName имя пользователя
     *  @return true/false если у пользователя есть/нет прав для просмотра */
    boolean isAuthorised(String userName);

    /** Установка авторизации пользователя
     *  @param userName имя пользователя
     *  @param authorisation устанавливаемая авторизация */
    void setAuthorised(String userName, Authorisation authorisation);
}
