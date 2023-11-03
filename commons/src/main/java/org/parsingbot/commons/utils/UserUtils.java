package org.parsingbot.commons.utils;

import lombok.experimental.UtilityClass;
import org.parsingbot.commons.entity.User;

import java.time.LocalDateTime;

@UtilityClass
public class UserUtils {

    /**
     * Обновляет следующую дату отправки шедулерного сообщения.
     * Устанавливает ее в LocalDateTime.now() + user.getNextSendDateDelaySeconds()
     *
     * @param user
     */
    public void updateUserNextSendDate(User user) {
        user.setNextSendDate(LocalDateTime.now().plusSeconds(user.getNextSendDateDelaySeconds()));
    }
}
