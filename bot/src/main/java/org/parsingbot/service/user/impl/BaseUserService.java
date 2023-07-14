package org.parsingbot.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.repository.UserRepository;
import org.parsingbot.service.user.User;
import org.parsingbot.service.user.UserService;

import java.util.Optional;

@RequiredArgsConstructor
public class BaseUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
