package org.parsingbot.service.validation.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
import org.parsingbot.service.Authorisation;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.validation.CommandHandlerValidator;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@RequiredArgsConstructor
public class CommandHandlerValidatorImpl implements CommandHandlerValidator {

    @Override
    public SendMessage getCommandInvocationError(CommandHandler commandHandler, User user) {
        Long chatId = user.getChatId();
        State userState = State.valueOf(user.getState());
        Authorisation userAuthorisation = Authorisation.valueOf(user.getAuthorisation());

        if (Authorisation.compare(userAuthorisation, commandHandler.getRequiredAuthorisation()) < 0) {
            log.warn("User with chatId {} not authorised to call command dispatcher {}", user.getChatId(), commandHandler);
            return BotUtils.userUnauthorisedMessage(chatId);
        }
        if (commandHandler.getRequiredState() == State.ANY) {
            return null;
        }
        if (!commandHandler.getRequiredState().equals(userState)) {
            log.warn("User with chatId {} attempted to call command dispatcher {} (state {}) with state {}",
                    chatId,
                    commandHandler.getClass().getSimpleName(),
                    commandHandler.getRequiredState(),
                    user.getState());
            return BotUtils.userIsNotInRequiredState(chatId);
        }
        return null;
    }
}