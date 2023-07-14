package org.parsingbot.service.user.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.service.user.User;
import org.parsingbot.service.user.UserService;
import org.parsingbot.service.user.auth.Authorisation;
import org.parsingbot.service.user.auth.UserAuthService;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class BaseUserAuthService implements UserAuthService {

    private static final String USER_NOT_FOUND_ERROR = "User with name {} wasn't found";

    private final UserService userService;

    @Override
    public boolean isAuthorised(String userName) {
        Optional<User> userOptional = userService.getUserByName(userName);
        if (userOptional.isPresent()) {
            return userOptional.get().getAuthorisation().toLowerCase().
                    equals(Authorisation.asString(Authorisation.ADMIN));
        } else {
            log.warn(USER_NOT_FOUND_ERROR, userName);
        }
        return false;
    }

    @Override
    public void setAuthorised(String userName, Authorisation authorisation) {
        Optional<User> userOptional = userService.getUserByName(userName);
        userOptional.ifPresent(user -> user.setAuthorisation(Authorisation.asString(authorisation)));
    }
}
