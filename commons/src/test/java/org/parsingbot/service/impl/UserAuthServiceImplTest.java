package org.parsingbot.service.impl;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.entity.User;
import org.parsingbot.service.Authorisation;
import org.parsingbot.service.UserAuthService;
import org.parsingbot.service.UserService;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceImplTest {

    private static final String USER_NAME = "gwynae";
    private static final Authorisation MINIMUM_AUTHORISATION = Authorisation.USER;
    private static final String USER_NOT_FOUND_ERROR = String.format("User with name %s wasn't found", USER_NAME);

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
        ListAppender<ILoggingEvent> logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(UserAuthServiceImpl.class)).addAppender(logWatcher);

        when(userService.getUserByName(USER_NAME)).thenReturn(Optional.empty());
        assertFalse(userAuthService.isAuthorised(USER_NAME, MINIMUM_AUTHORISATION));
        assertThat(logWatcher.list.get(0).getFormattedMessage()).contains(USER_NOT_FOUND_ERROR);
    }
}