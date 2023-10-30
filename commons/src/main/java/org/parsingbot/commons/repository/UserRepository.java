package org.parsingbot.commons.repository;

import org.parsingbot.commons.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью User
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @param userId id пользователя
     * @return id вакансий, ассоциированных с пользователем
     */
    @Query(value = "select vacancy_id from vacancies " +
            "join users_vacancies on vacancies.id = users_vacancies.vacancy_id " +
            "join users on users_vacancies.user_id = users.id " +
            "where users.id = ?1",
            nativeQuery = true)
    List<Integer> findUserVacanciesIds(Long userId);

    /**
     * @param chatId id чата с пользователем
     * @return Optional обертка над объектом пользователя
     */
    Optional<User> findUserByChatId(Long chatId);

    /**
     * @return список пользователей, у которых is_subscribed = true
     */
    @Query(value = "select * from users where is_subscribed = true", nativeQuery = true)
    List<User> findSubscribedUsers();

    /**
     * @param userId id пользователя
     * @param state новое состояние пользователя
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update users set state = ?2 where id = ?1", nativeQuery = true)
    void updateStateByUserId(Long userId, String state);

    /**
     * @param userId id пользователя
     * @return имя вакансии для поиска
     */
    @Query(value = "select vacancy_name from users where users.id = ?1", nativeQuery = true)
    String findVacancyNameByUserId(Long userId);

    /**
     * @param userId id пользователя
     * @return количество вакансий для поиска
     */
    @Query(value = "select number_of_vacancies from users where users.id = ?1", nativeQuery = true)
    Long findNumberOfVacanciesByUserId(Long userId);

    /**
     * @param userId id пользователя
     * @return ключевые слова в виде строки для поиска вакансий
     */
    @Query(value = "select keywords from users where users.id = ?1", nativeQuery = true)
    String findKeywordsByUserId(Long userId);
}