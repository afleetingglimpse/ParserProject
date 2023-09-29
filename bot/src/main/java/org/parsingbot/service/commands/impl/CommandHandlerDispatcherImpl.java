package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Command;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
import org.parsingbot.service.Authorisation;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CommandHandlerDispatcherImpl implements CommandHandlerDispatcher {

    private final Map<String, CommandHandler> startCommandHandlerMap;
    private final Map<State, CommandHandler> commandHandlerMap;

    @Override
    public CommandHandler getCommandHandler(Event event) {
        Command command = event.getCommand();
        User user = event.getUser();

        if (startCommandHandlerMap.containsKey(command.getPrefix())) {
            CommandHandler commandHandler = startCommandHandlerMap.get(command.getPrefix());
            if (isUserAbleToInvokeCommandHandler(commandHandler, user)) {
                return commandHandler;
            }
            return null;
        }
        return commandHandlerMap.getOrDefault(State.valueOf(user.getState()), null);
    }

    private boolean isUserAbleToInvokeCommandHandler(CommandHandler commandHandler, User user) {
        State userState = State.valueOf(user.getState());
        Authorisation userAuthorisation = Authorisation.valueOf(user.getAuthorisation());
        if (Authorisation.compare(userAuthorisation, commandHandler.getRequiredAuthorisation()) < 0) {
            log.warn("User with chatId {} not authorised to call command dispatcher {}", user.getChatId(), commandHandler);
            return false;
        }
        if (commandHandler.getRequiredState() == State.ANY) {
            return true;
        }
        if (!commandHandler.getRequiredState().equals(userState)) {
            log.warn("User with chatId {} attempted to call command dispatcher {} (state {}) with state {}",
                    user.getChatId(),
                    commandHandler.getClass().getSimpleName(),
                    commandHandler.getRequiredState(),
                    user.getState());
            return false;
        }
        return true;
    }
}