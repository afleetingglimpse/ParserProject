package org.parsingbot.repository;

import org.parsingbot.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    @Query(
            value = "Select * from users where is_subscribed = 'true'",
            nativeQuery = true
    )
    List<User> getSubscribedUsers();
}
