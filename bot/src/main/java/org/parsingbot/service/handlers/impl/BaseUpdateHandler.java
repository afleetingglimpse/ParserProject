package org.parsingbot.service.handlers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.service.bot.impl.TelegramBot;
import org.parsingbot.service.handlers.CommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.handlers.UpdateHandler;
import org.parsingbot.service.user.auth.UserAuthService;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
public class BaseUpdateHandler implements UpdateHandler {

    private static final String NOT_AUTHORISED_ERROR = "User {} not authorised";
    private static final String INVALID_UPDATE_ERROR = "userName or/and message not valid";
    private static final String NOT_A_COMMAND_MESSAGE = "Your message is not a command. " +
            "Type /help to see the commands list";
    private static final String NOT_AUTHORISED_MESSAGE = "Not authorised. Contact system administrators " +
            "for further information.";

    private final CommandHandler commandHandler;
    private final ResponseHandler responseHandler;
    private final UserAuthService userAuthService;

    @Override
    public void handleUpdate(TelegramBot bot, Update update) {
        long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        String message = update.getMessage().getText();
        if (userName == null || message == null) {
            log.error(INVALID_UPDATE_ERROR);
            return;
        }

        if (userAuthService.isAuthorised(userName)) {
            if (commandHandler.isCommand(message)) {
                commandHandler.handleCommand(message);
            } else {
                responseHandler.sendResponse(bot, NOT_A_COMMAND_MESSAGE, chatId);
            }

        } else {
            log.warn(NOT_AUTHORISED_ERROR, userName);
            responseHandler.sendResponse(bot, NOT_AUTHORISED_MESSAGE, chatId);
        }
    }
}
