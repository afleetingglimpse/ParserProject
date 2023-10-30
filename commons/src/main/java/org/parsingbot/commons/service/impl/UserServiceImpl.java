package org.parsingbot.commons.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.repository.SearchHistoryRepository;
import org.parsingbot.commons.repository.UserRepository;
import org.parsingbot.commons.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getSubscribedUsers() {
        return userRepository.findSubscribedUsers();
    }

    @Override
    public Optional<User> getUserByChatId(Long chatId) {
        return userRepository.findUserByChatId(chatId);
    }

    @Override
    public User getUserByChatIdCreateIfNotExist(Long chatId, String userName) {
        Optional<User> userOptional = getUserByChatId(chatId);
        return userOptional.orElseGet(() -> save(
                User.builder()
                        .userName(userName)
                        .chatId(chatId)
                        .state(State.NONE.toString())
                        .build())
        );
    }

    @Override
    public void updateNextSendDate(User user) {
        Long nextSendDateDelaySeconds = user.getNextSendDateDelaySeconds();
        LocalDateTime nextSendDate = LocalDateTime.now();
        user.setNextSendDate(nextSendDate.plusSeconds(nextSendDateDelaySeconds));
        userRepository.save(user);
    }

    @Override
    public void setDefaultStateByUser(User user) {
        user.setState(State.NONE.toString());
        save(user);
    }

    @Override
    public SearchHistory getUserSearchHistory(User user) {
        return searchHistoryRepository.findSearchHistoryByUserId(user.getId());
    }
}