package org.parsingbot.service.user;

import java.util.List;
import java.util.Optional;

/**
 * Сервис взаимодействия бота с БД юзеров
 */
public interface UserService {

    Optional<User> getUserByName(String userName);
    List<User> getSubscribedUsers();
}
