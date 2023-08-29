package org.parsingbot.service;

import org.parsingbot.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Сервис взаимодействия бота с БД юзеров
 */
public interface UserService {

    /**
     * @param user объект пользователя
     */
    void save(User user);

    /**
     * @param userName имя пользователя
     * @return Optional обертка над объектом пользователя
     */
    Optional<User> getUserByName(String userName);

    /**
     * @param id id пользователя
     * @return Optional обертка над объектом пользователя
     */
    Optional<User> getUserById(int id);

    /**
     * @return список пользователей, у которых is_subscribed = true
     */
    List<User> getSubscribedUsers();

    /**
     * @param id            id пользователя
     * @param authorisation устанавливаемый уровень авторизации пользователя
     */
    void updateAuthorisationById(int id, Authorisation authorisation);
}
