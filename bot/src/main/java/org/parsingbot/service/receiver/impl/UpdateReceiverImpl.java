package org.parsingbot.service.receiver.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.parsingbot.service.receiver.UpdateReceiver;
import org.parsingbot.util.BotUtils;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

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
            "User with chatId {} attempted to call command dispatcher for unknown command {}";
    private static final String INVOKED_COMMAND_HANDLER = "User with chatId {} invoked commandHandler: {}";

    private final UserService userService;
    private final CommandHandlerDispatcher commandHandlerDispatcher;

    @Override
    @Transactional
    public List<PartialBotApiMethod<? extends Serializable>> handleUpdate(Update update) {
        Event event = createEvent(update);
        CommandHandler commandHandler = commandHandlerDispatcher.getCommandHandler(event);
        if (commandHandler != null) {
            log.info(INVOKED_COMMAND_HANDLER, event.getChatId(), commandHandler.getClass().getSimpleName());
            return commandHandler.handleCommand(event);
        }
        log.warn(COMMAND_DISPATCHER_NOT_FOUND, event.getChatId(), event.getCommand().getFullMessage());
        return returnInvalidCommandResponse(event);
    }

    private Event createEvent(Update update) {
        long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        String messageText = update.getMessage().getText();
        User user = userService.getUserByChatIdCreateIfNotExist(chatId, userName);

        return new Event(update, user, messageText);
    }

    private List<PartialBotApiMethod<? extends Serializable>> returnInvalidCommandResponse(Event event) {
        return List.of(BotUtils.createMessage(event.getChatId(), NOT_A_COMMAND_ERROR));
    }
}