package org.parsingbot.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.repository.UserRepository;
import org.parsingbot.service.user.UserService;

@RequiredArgsConstructor
public class BaseUserService implements UserService {

    private final UserRepository userRepository;
}
