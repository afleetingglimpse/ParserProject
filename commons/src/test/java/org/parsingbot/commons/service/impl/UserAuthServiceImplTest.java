package org.parsingbot.commons.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.service.Authorisation;
import org.parsingbot.commons.service.UserService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceImplTest {
    private static final Authorisation MINIMUM_AUTHORISATION = Authorisation.USER;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserAuthServiceImpl userAuthService;

    @ParameterizedTest
    @EnumSource(value = Authorisation.class, names = {"UNKNOWN", "USER"})
    void isAuthorisedUserIsNotAuthorisedTest(Authorisation userAuthorisation) {
        User user = User.builder()
                .authorisation(userAuthorisation.getName())
                .build();

        assertFalse(userAuthService.isAuthorised(user, MINIMUM_AUTHORISATION));
    }

    @ParameterizedTest
    @EnumSource(value = Authorisation.class, names = {"ADMIN", "DUNGEON_MASTER"})
    void isAuthorisedUserIsAuthorisedTest(Authorisation userAuthorisation) {
        User user = User.builder()
                .authorisation(userAuthorisation.getName())
                .build();

        assertTrue(userAuthService.isAuthorised(user, MINIMUM_AUTHORISATION));

        verifyNoInteractions(userService);
    }

    @Test
    void setAuthorisedByIdTest() {
        User user = User.builder().build();
        Authorisation authorisation = Authorisation.DUNGEON_MASTER;

        userAuthService.setAuthorisedById(user, authorisation);

        assertEquals(authorisation.getName(), user.getAuthorisation());

        verify(userService).save(user);
    }
}