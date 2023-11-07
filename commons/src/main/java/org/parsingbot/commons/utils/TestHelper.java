package org.parsingbot.commons.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import lombok.experimental.UtilityClass;
import org.parsingbot.commons.entity.*;
import org.parsingbot.commons.service.Authorisation;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class TestHelper {

    public static final Random RND = new Random();

    public String randomFromUuid() {
        return UUID.randomUUID().toString();
    }

    public ListAppender<ILoggingEvent> getLogger(Class<?> clazz) {
        ListAppender<ILoggingEvent> logWatcher = new ListAppender<>();
        ((Logger) LoggerFactory.getLogger(clazz)).addAppender(logWatcher);
        return logWatcher;
    }

    public Event createEvent() {
        User user = createUser();
        return Event.builder()
                .user(user)
                .update(new Update())
                .chatId(user.getChatId())
                .command(new Command(String.format("/%s %s", randomFromUuid(), randomFromUuid())))
                .build();
    }

    public User createUser() {
        return User.builder()
                .id(RND.nextLong())
                .userName(randomFromUuid())
                .authorisation(Authorisation.USER.getName())
                .isSubscribed(RND.nextBoolean())
                .chatId(RND.nextLong())
                .nextSendDate(LocalDateTime.now())
                .nextSendDateDelaySeconds(RND.nextLong(1, 100))
                .state(State.ANY.toString())
                .userVacancies(List.of(Vacancy.builder().build()))
                .searchHistories(List.of(SearchHistory.builder().build()))
                .build();
    }
}
