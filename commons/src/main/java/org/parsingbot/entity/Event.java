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

    public long getChatId() {
        return update.getMessage().getChatId();
    }
}