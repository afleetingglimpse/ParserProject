package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Command;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SubscribeCommandHandler implements CommandHandler {

    private static final String USER_ALREADY_SUBSCRIBED = "User is already subscribed";
    private static final String SUCCESSFUL_SUBSCRIBE = "You have been successfully subscribed";

    private final UserService userService;
    private final ResponseHandler responseHandler;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        long chatId = event.getChatId();
        User user = event.getUser();

        if (user.getIsSubscribed()) {
            return List.of(BotUtils.createMessage(chatId, USER_ALREADY_SUBSCRIBED));
        }

        user.setIsSubscribed(true);
        userService.save(user);
        return List.of(BotUtils.createMessage(chatId, SUCCESSFUL_SUBSCRIBE));
    }
}