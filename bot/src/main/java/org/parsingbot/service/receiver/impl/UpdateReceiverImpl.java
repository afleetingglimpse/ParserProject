package org.parsingbot.service.receiver.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.CommandDto;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.receiver.UpdateReceiver;
import org.parsingbot.service.receiver.checkers.CommandChecker;
import org.parsingbot.service.receiver.checkers.UpdateChecker;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
public class UpdateReceiverImpl implements UpdateReceiver {

    // messages for user
    private static final String INVALID_UPDATE_ERROR = "userName or/and message not valid";
    private static final String NOT_AUTHORISED_FOR_COMMAND_ERROR = "You are not authorised to use that command";
    private static final String NOT_A_COMMAND_ERROR = "Your message is not a command. Type /help to see the commands list";

    // log messages
    private static final String INVALID_UPDATE_LOG = "Update object from user {} with chatId {} is not valid";
    private static final String NOT_AUTHORISED_FOR_COMMAND_LOG = "User {} with chatId {} is not authorised to use command {}";

    private final UserService userService;
    private final UpdateChecker updateChecker;
    private final ResponseHandler responseHandler;
    private final CommandChecker commandChecker;
    private final CommandHandlerDispatcher commandHandlerDispatcher;

    @Override
    public void handleUpdate(TelegramBot bot, Update update) {
        long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        String message = update.getMessage().getText();
        User user = userService.getUserByChatIdCreateIfNotExist(chatId, userName);
        CommandDto commandDto = new CommandDto(message);

        String updateError = updateChecker.checkUpdate(update);
        if (updateError != null) {
            log.error(INVALID_UPDATE_LOG, userName, chatId);
            responseHandler.sendResponse(bot, updateError, chatId);
            return;
        }

        String commandAuthError = commandChecker.checkCommand(commandDto, user);
        if (commandAuthError != null) {
            log.error(NOT_AUTHORISED_FOR_COMMAND_LOG, userName, chatId, commandDto.getPrefix());
            responseHandler.sendResponse(bot, commandAuthError, chatId);
            return;
        }

        CommandHandler commandHandler = commandHandlerDispatcher.getCommandHandler(commandDto);
        if (commandHandler == null) {
            responseHandler.sendResponse(bot, NOT_A_COMMAND_ERROR, chatId);
            return;
        }
        commandHandler.handleCommand(bot, commandDto, update);
    }
}