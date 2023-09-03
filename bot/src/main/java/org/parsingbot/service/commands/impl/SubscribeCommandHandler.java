package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.CommandDto;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@RequiredArgsConstructor
public class SubscribeCommandHandler implements CommandHandler {

    private static final String USER_ALREADY_SUBSRIBED = "User is already subscribed";
    private static final String SUCCESSFUL_SUBSCRIBE = "You have been successfully subscribed";

    private final UserService userService;
    private final ResponseHandler responseHandler;

    @Override
    public void handleCommand(TelegramBot bot, CommandDto commandDto, Update update) {
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        Optional<User> userOptional = userService.getUserByName(userName);

        if (userOptional.isEmpty()) {
            return;
        }
        User user = userOptional.get();

        // TODO check is possible to store boolean in Postgres
        if (user.getIsSubscribed().equals(String.valueOf(true))) {
            responseHandler.sendResponse(bot, USER_ALREADY_SUBSRIBED, chatId);
            return;
        }

        user.setIsSubscribed(String.valueOf(true));
        responseHandler.sendResponse(bot, SUCCESSFUL_SUBSCRIBE, user.getChatId());
        // TODO set scheduled service parameters
    }
}
