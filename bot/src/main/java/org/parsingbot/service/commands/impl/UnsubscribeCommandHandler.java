package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
public class UnsubscribeCommandHandler implements CommandHandler {

    private static final String USER_NOT_SUBSCRIBED = "User is not subscribed";
    private static final String SUCCESSFUL_UNSUBSCRIBE = "You have been successfully unsubscribed";

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        long chatId = event.getChatId();
        User user = event.getUser();

        if (!user.getIsSubscribed()) {
            return List.of(BotUtils.createMessage(chatId, USER_NOT_SUBSCRIBED));
        }

        user.setIsSubscribed(false);
        userService.save(user);
        return List.of(BotUtils.createMessage(chatId, SUCCESSFUL_UNSUBSCRIBE));
    }
}