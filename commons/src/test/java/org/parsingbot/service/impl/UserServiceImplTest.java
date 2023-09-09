package org.parsingbot.service.impl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.entity.User;
import org.parsingbot.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @Disabled
    void saveTest() {
//        User user = new User();
//        when(userRepository.save(user)).thenReturn(null);
//        // TODO no idea. Check how to mock DB
    }

    @Test
    void getUserByNameTest() {

    }
}