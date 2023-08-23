package org.parsingbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.repository.UserRepository;
import org.parsingbot.service.Authorisation;
import org.parsingbot.service.UserService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    @Cacheable("subscribedUsers")
    public List<User> getSubscribedUsers() {
        return userRepository.getSubscribedUsers();
    }

    @Override
    public void updateAuthorisationByUserName(String userName, Authorisation authorisation) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAuthorisation(Authorisation.asString(authorisation));
            userRepository.save(user);
        }
    }

    @Override
    public void updateAuthorisationById(int id, Authorisation authorisation) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAuthorisation(Authorisation.asString(authorisation));
            userRepository.save(user);
        }
    }

    @Override
    public List<Integer> getUserVacanciesIds(Integer userId) {
        return userRepository.getUserVacanciesIds(userId);
    }

}
