package org.parsingbot.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.repository.UserRepository;
import org.parsingbot.service.user.User;
import org.parsingbot.service.user.UserService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    @Cacheable("subscribedUsers")
    public List<User> getSubscribedUsers() {
        return userRepository.getSubscribedUsers();
    }
}
