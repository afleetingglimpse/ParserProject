package org.parsingbot.commons.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.service.Authorisation;
import org.parsingbot.commons.service.UserAuthService;
import org.parsingbot.commons.service.UserService;

@Slf4j
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserService userService;

    @Override
    public boolean isAuthorised(User user, Authorisation minimumAuthorisation) {
        return Authorisation.compare(Authorisation.valueOf(user.getAuthorisation()), minimumAuthorisation) > 0;
    }

    @Override
    public void setAuthorisedById(User user, Authorisation authorisation) {
        user.setAuthorisation(authorisation.getName());
        userService.save(user);
    }
}