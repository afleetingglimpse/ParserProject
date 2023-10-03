package org.parsingbot.service.receiver.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.parsingbot.service.receiver.UpdateReceiver;
import org.parsingbot.service.validation.CommandHandlerValidator;
import org.parsingbot.util.BotUtils;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UpdateReceiverImpl implements UpdateReceiver {

    private final UserService userService;
    private final CommandHandlerDispatcher commandHandlerDispatcher;
    private final CommandHandlerValidator commandHandlerValidator;

    @Override
    @Transactional
    public List<PartialBotApiMethod<? extends Serializable>> handleUpdate(Update update) {
        Event event = createEvent(update);

        CommandHandler commandHandler = commandHandlerDispatcher.getCommandHandler(event);
        if (commandHandler == null) {
            return List.of(BotUtils.commandNotFoundError(event.getChatId()));
        }

        SendMessage invocationErrorMessage = commandHandlerValidator.getCommandInvocationError(commandHandler, event.getUser());
        if (invocationErrorMessage != null) {
            return List.of(invocationErrorMessage);
        }

        log.info("User with chatId {} invoked commandHandler: {}", event.getChatId(), commandHandler.getClass().getSimpleName());
        return commandHandler.handleCommand(event);
    }

    private Event createEvent(Update update) {
        Message message = update.getMessage();
        String text;
        if (message == null) {
            message = update.getCallbackQuery().getMessage();
            text = update.getCallbackQuery().getData();
        } else {
            text = message.getText();
        }

        long chatId = message.getChatId();
        String userName = message.getChat().getUserName();
        String messageText = text;
        User user = userService.getUserByChatIdCreateIfNotExist(chatId, userName);

        return new Event(update, chatId, user, messageText);
    }
}