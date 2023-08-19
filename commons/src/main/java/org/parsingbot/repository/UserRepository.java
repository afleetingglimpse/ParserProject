package org.parsingbot.repository;

import org.parsingbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

    @Query(value = "select * from users where is_subscribed = 'true'", nativeQuery = true)
    List<User> getSubscribedUsers();
}