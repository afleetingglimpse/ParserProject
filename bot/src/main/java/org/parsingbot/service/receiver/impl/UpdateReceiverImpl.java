package org.parsingbot.service.receiver.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.parsingbot.service.receiver.UpdateReceiver;
import org.parsingbot.service.receiver.checkers.CommandChecker;
import org.parsingbot.service.receiver.checkers.UpdateChecker;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

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
    private final CommandChecker commandChecker;
    private final CommandHandlerDispatcher commandHandlerDispatcher;

    @Override
    @Transactional
    public void handleUpdate(TelegramBot bot, Update update) {
        Event event = createEvent(update);

        // TODO replace all with Event
        var userName = event.getUser().getUserName();
        var chatId = event.getChatId();
        var command = event.getCommand();
        var user = event.getUser();

//        String updateError = updateChecker.checkUpdate(update);
//        if (updateError != null) {
//            log.warn(INVALID_UPDATE_LOG, userName, chatId);
//            responseHandler.sendResponse(bot, updateError, chatId);
//            return;
//        }

//        String commandError = commandChecker.checkCommand(command, user);
//        if (commandError != null) {
//            log.warn(NOT_AUTHORISED_FOR_COMMAND_LOG, userName, chatId, command.getPrefix());
//            responseHandler.sendResponse(bot, commandError, chatId);
//            return;
//        }
//
        CommandHandler commandHandler = commandHandlerDispatcher.getCommandHandler(command, user);
//        if (commandHandler == null) {
//            log.warn(COMMAND_DISPATCHER_NOT_FOUND, userName, chatId, command.getPrefix());
//            responseHandler.sendResponse(bot, NOT_A_COMMAND_ERROR, chatId);
//            return;
//        }

        log.info(PROCESSING_COMMAND, userName, chatId, command.getPrefix());
        List<PartialBotApiMethod<? extends Serializable>> botApiMethods = commandHandler.handleCommand(event);
        botApiMethods.forEach((method) -> {
            try {
                bot.execute((SendMessage) method);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Event createEvent(Update update) {
        long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        String messageText = update.getMessage().getText();
        User user = userService.getUserByChatIdCreateIfNotExist(chatId, userName);

        return new Event(update, user, messageText);
    }
}