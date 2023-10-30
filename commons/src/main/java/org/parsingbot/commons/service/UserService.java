package org.parsingbot.commons.service;

import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.User;

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
     * @return список пользователей, у которых is_subscribed = true
     */
    List<User> getSubscribedUsers();

    /**
     * @param chatId id чата с пользователем
     * @return Optional обертка над объектом пользователя
     */
    Optional<User> getUserByChatId(Long chatId);

    /**
     * @param chatId id чата с пользователем
     * @return объект пользователя
     */
    User getUserByChatIdCreateIfNotExist(Long chatId, String userName);

    /**
     * Добавляет к user.next_send_date число user.next_send_date_delay_seconds
     *
     * @param user объект пользователя
     */
    void updateNextSendDate(User user);

    /**
     * @param user объект пользователя
     */
    void setDefaultStateByUser(User user);
}