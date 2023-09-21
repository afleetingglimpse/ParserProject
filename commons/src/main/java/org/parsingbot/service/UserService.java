package org.parsingbot.service;

import org.parsingbot.entity.State;
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
    Optional<User> getUserById(Long id);

    /**
     * @return список пользователей, у которых is_subscribed = true
     */
    List<User> getSubscribedUsers();

    /**
     * @param id            id пользователя
     * @param authorisation устанавливаемый уровень авторизации пользователя
     */
    void updateAuthorisationById(Long id, Authorisation authorisation);

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
     * @param userId id пользователя
     * @param state новый статус пользователя
     */
    void updateStateByUserId(Long userId, State state);

    /**
     * @param user объект пользователя
     * @param state новый статус пользователя
     */
    void updateStateByUser(User user, State state);

    /**
     * @param userId id пользователя
     */
    void setDefaultStateByUserId(Long userId);

    /**
     * @param userId id пользователя
     * @return имя вакансии для поиска
     */
    String getVacancyNameByUserId(Long userId);

    /**
     * @param userId id пользователя
     * @return количество вакансий для поиска
     */
    Long getNumberOfVacanciesByUserId(Long userId);

    /**
     * @param userId id пользователя
     * @return ключевые слова в виде строки для поиска вакансий
     */
    String getKeywordsByUserId(Long userId);
}