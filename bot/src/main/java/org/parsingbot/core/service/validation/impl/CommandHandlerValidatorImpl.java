package org.parsingbot.core.service.validation.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.service.Authorisation;
import org.parsingbot.core.service.commands.CommandHandler;
import org.parsingbot.core.service.validation.CommandHandlerValidator;
import org.parsingbot.core.util.BotUtils;
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