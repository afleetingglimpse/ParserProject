package org.parsingbot.service.receiver.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Command;
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
    private static final String COMMAND_DISPATCHER_NOT_FOUND =
            "User {} with chatId {} attempted to call command dispatcher for unknown command {}";
    private static final String PROCESSING_COMMAND = "User {} with chatId {} requested processing command {}";

    private final UserService userService;
    private final UpdateChecker updateChecker;
    private final ResponseHandler responseHandler;
    private final CommandChecker commandChecker;
    private final CommandHandlerDispatcher commandHandlerDispatcher;

    @Override
    public void handleUpdate(TelegramBot bot, Update update) {
        long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        String messageText = update.getMessage().getText();
        User user = userService.getUserByChatIdCreateIfNotExist(chatId, userName);
        Command command = new Command(messageText);

        String updateError = updateChecker.checkUpdate(update);
        if (updateError != null) {
            log.warn(INVALID_UPDATE_LOG, userName, chatId);
            responseHandler.sendResponse(bot, updateError, chatId);
            return;
        }

        String commandError = commandChecker.checkCommand(command, user);
        if (commandError != null) {
            log.warn(NOT_AUTHORISED_FOR_COMMAND_LOG, userName, chatId, command.getPrefix());
            responseHandler.sendResponse(bot, commandError, chatId);
            return;
        }

        CommandHandler commandHandler = commandHandlerDispatcher.getCommandHandler(command, user);
        if (commandHandler == null) {
            log.warn(COMMAND_DISPATCHER_NOT_FOUND, userName, chatId, command.getPrefix());
            responseHandler.sendResponse(bot, NOT_A_COMMAND_ERROR, chatId);
            return;
        }

        log.info(PROCESSING_COMMAND, userName, chatId, command.getPrefix());
        commandHandler.handleCommand(bot, command, update);
    }
}