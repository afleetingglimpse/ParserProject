package org.parsingbot.service.user;

import org.parsingbot.service.user.auth.Authorisation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис взаимодействия бота с БД юзеров
 */
public interface UserService {
    // TODO add doc
    Optional<User> getUserByName(String userName);

    Optional<User> getUserById(int id);

    List<User> getSubscribedUsers();

    void updateAuthorisationByUserName(String userName, Authorisation authorisation);

    void updateAuthorisationById(int id, Authorisation authorisation);
}
