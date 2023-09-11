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
     * @return объект сохраненного пользователя
     */
    User save(User user);

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

    /**
     * @param chatId id чата с пользователем
     * @return Optional обертка над объектом пользователя
     */
    Optional<User> getUserByChatId(long chatId);

    /**
     * @param chatId id чата с пользователем
     * @return объект пользователя
     */
    User getUserByChatIdCreateIfNotExist(long chatId, String userName);

    /**
     * Добавляет к user.next_send_date число user.next_send_date_delay_seconds
     *
     * @param user объект пользователя
     */
    // TODO update from current date
    void updateNextSendDate(User user);

    /**
     * @param userId id пользователя
     * @param status новый статус пользователя
     */
    void updateStatusByUserId(Integer userId, String status);

    /**
     * @param userId id пользователя
     */
    void setDefaultStatusByUserId(Integer userId);
}