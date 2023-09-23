package org.parsingbot.entity;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.Update;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Event {
    private Update update;
    private Long chatId;
    private User user;
    private Command command;

    public Event(Update update, Long chatId, User user, String messageText) {
        this.update = update;
        this.chatId = chatId;
        this.user = user;
        this.command = new Command(messageText);
    }
}