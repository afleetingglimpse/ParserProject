package org.parsingbot.repository;

import org.parsingbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select vacancy_id from vacancies " +
            "join users_vacancies on vacancies.id = users_vacancies.vacancy_id " +
            "join users on users_vacancies.user_id = users.id " +
            "where users.id = ?1",
            nativeQuery = true)
    List<Integer> getUserVacanciesIds(Integer userId);

    Optional<User> findByUserName(String userName);

    @Query(value = "select * from users where is_subscribed = 'true'", nativeQuery = true)
    List<User> getSubscribedUsers();

    Optional<User> getUserByChatId(long chatId);
}