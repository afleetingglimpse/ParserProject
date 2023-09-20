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
    private User user;
    private Command command;

    public Event(Update update, User user, String messageText) {
        this.update = update;
        this.user = user;
        this.command = new Command(messageText);
    }

    public long getChatId() {
        return update.getMessage().getChatId();
    }
}