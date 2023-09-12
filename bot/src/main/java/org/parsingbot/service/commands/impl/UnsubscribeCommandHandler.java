package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Command;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@RequiredArgsConstructor
public class UnsubscribeCommandHandler implements CommandHandler {

    private static final String USER_NOT_SUBSCRIBED = "User is not subscribed";
    private static final String SUCCESSFUL_UNSUBSCRIBE = "You have been successfully unsubscribed";

    private final UserService userService;
    private final ResponseHandler responseHandler;

    @Override
    public void handleCommand(TelegramBot bot, Command command, Update update) {
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        Optional<User> userOptional = userService.getUserByName(userName);

        if (userOptional.isEmpty()) {
            return;
        }
        User user = userOptional.get();

        if (!user.getIsSubscribed()) {
            responseHandler.sendResponse(bot, USER_NOT_SUBSCRIBED, chatId);
            return;
        }

        user.setIsSubscribed(false);
        userService.save(user);
        responseHandler.sendResponse(bot, SUCCESSFUL_UNSUBSCRIBE, user.getChatId());
        // TODO set scheduled service parameters
    }
}