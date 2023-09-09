package org.parsingbot.service.receiver.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.CommandDto;
import org.parsingbot.service.UserService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.receiver.UpdateReceiver;
import org.parsingbot.service.receiver.checkers.CommandAuthorisationChecker;
import org.parsingbot.service.receiver.checkers.UpdateChecker;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
public class UpdateReceiverImpl implements UpdateReceiver {

    private static final String INVALID_UPDATE_ERROR = "userName or/and message not valid";

    private final UpdateChecker updateChecker;
    private final CommandAuthorisationChecker commandAuthorisationChecker;
    private final ResponseHandler responseHandler;
    private final UserService userService;

    @Override
    public void handleUpdate(TelegramBot bot, Update update) {
        long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        String message = update.getMessage().getText();

        String updateError = updateChecker.checkUpdate(update);
        if (updateError != null) {
            responseHandler.sendResponse(bot, INVALID_UPDATE_ERROR, chatId);
        }

        CommandDto commandDto = new CommandDto(message);
        String commandAuthError = commandAuthorisationChecker.checkCommandAuthorisation(commandDto);
    }
}