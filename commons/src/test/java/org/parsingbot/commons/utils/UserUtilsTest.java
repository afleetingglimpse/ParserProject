package org.parsingbot.commons.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.parsingbot.commons.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты на утильный класс UserUtils")
class UserUtilsTest {

    private static final String PATTERN = "yyyy-mm-dd-hh-mm";

    @Test
    @DisplayName("Тест метода updateUserNextSendDate")
    void updateUserNextSendDateTest() {
        String userName = UUID.randomUUID().toString();
        LocalDateTime nextSendDate = LocalDateTime.now();
        Long nextSendDateDelaySeconds = new Random().nextLong(60, 3600);
        User user = User.builder()
                .userName(userName)
                .nextSendDate(nextSendDate)
                .nextSendDateDelaySeconds(nextSendDateDelaySeconds)
                .build();
        UserUtils.updateUserNextSendDate(user);

        String expected = nextSendDate.plusSeconds(nextSendDateDelaySeconds).format(DateTimeFormatter.ofPattern(PATTERN));

        String actual = user.getNextSendDate().format(DateTimeFormatter.ofPattern(PATTERN));

        assertEquals(expected, actual);
    }
}