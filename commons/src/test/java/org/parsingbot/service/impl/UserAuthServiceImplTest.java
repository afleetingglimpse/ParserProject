package org.parsingbot.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.entity.User;
import org.parsingbot.service.Authorisation;
import org.parsingbot.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceImplTest {

    private static final String USER_NAME = "gwynae";
    private static final Authorisation MINIMUM_AUTHORISATION = Authorisation.USER;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserAuthServiceImpl userAuthService;

    @Test
    void isAuthorisedUserIsPresentTest() {
        User user = new User();
        user.setUserName(USER_NAME);
        user.setAuthorisation(MINIMUM_AUTHORISATION.getName());

        when(userService.getUserByName(USER_NAME)).thenReturn(Optional.of(user));
        assertTrue(userAuthService.isAuthorised(USER_NAME, MINIMUM_AUTHORISATION));
    }

    @Test
    void isAuthorisedUserIsNotPresentTest() {
        when(userService.getUserByName(USER_NAME)).thenReturn(Optional.empty());

        // TODO intercept log
        assertFalse(userAuthService.isAuthorised(USER_NAME, MINIMUM_AUTHORISATION));
    }
}