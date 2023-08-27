package org.parsingbot.service;

import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;

import java.util.List;
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

    List<Integer> getUserVacanciesIds(Integer userId);

    void addVacancies(List<Vacancy> vacancies);

    void save(User user);
}
