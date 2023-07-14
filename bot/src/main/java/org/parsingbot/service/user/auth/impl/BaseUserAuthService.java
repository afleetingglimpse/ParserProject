package org.parsingbot.service.user.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.service.user.UserService;
import org.parsingbot.service.user.auth.Authorisation;
import org.parsingbot.service.user.auth.UserAuthService;

@RequiredArgsConstructor
@Slf4j
public class BaseUserAuthService implements UserAuthService {

    private final UserService userService;

    @Override
    public boolean isAuthorised(String userName) {
        return userName != null && userName.equals("gwynae");
    }

    @Override
    public void setAuthorised(String userName, Authorisation authorisation) {

    }
}
