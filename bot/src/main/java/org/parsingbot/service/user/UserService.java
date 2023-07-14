package org.parsingbot.service.user;

import java.util.Optional;

/**
 * Сервис взаимодействия бота с БД юзеров
 */
public interface UserService {

    Optional<User> getUserByName(String userName);
}
