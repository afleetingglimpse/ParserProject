package org.parsingbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
import org.parsingbot.repository.UserRepository;
import org.parsingbot.service.Authorisation;
import org.parsingbot.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getSubscribedUsers() {
        return userRepository.findSubscribedUsers();
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
    public Optional<User> getUserByChatId(long chatId) {
        return userRepository.findUserByChatId(chatId);
    }

    @Override
    public User getUserByChatIdCreateIfNotExist(long chatId, String userName) {
        Optional<User> userOptional = this.getUserByChatId(chatId);
        return userOptional.orElseGet(() -> this.save(User.builder().userName(userName).chatId(chatId).build()));
    }

    @Override
    public void updateNextSendDate(User user) {
        long nextSendDateDelaySeconds = user.getNextSendDateDelaySeconds();
        LocalDateTime nextSendDate = LocalDateTime.now();
        user.setNextSendDate(nextSendDate.plusSeconds(nextSendDateDelaySeconds));
        userRepository.save(user);
    }

    @Override
    public void updateStatusByUserId(Integer userId, String status) {
        userRepository.updateStateByUserId(userId, status);
    }

    @Override
    public void updateStatusByUser(User user, String status) {
        userRepository.updateStateByUserId(user.getId(), status);
    }

    @Override
    public void setDefaultStatusByUserId(Integer userId) {
        userRepository.updateStateByUserId(userId, State.NONE.toString());
    }

    @Override
    public String getVacancyNameByUserId(long userId) {
        return userRepository.findVacancyNameByUserId(userId);
    }

    @Override
    public long getNumberOfVacanciesByUserId(long userId) {
        return userRepository.findNumberOfVacanciesByUserId(userId);
    }

    @Override
    public String getKeywordsByUserId(long userId) {
        return userRepository.findKeywordsByUserId(userId);
    }
}