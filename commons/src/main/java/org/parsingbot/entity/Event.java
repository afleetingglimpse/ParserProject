package org.parsingbot.entity;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@Builder
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