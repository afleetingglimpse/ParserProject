package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Command;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;

import java.util.Map;

@RequiredArgsConstructor
public class CommandHandlerDispatcherImpl implements CommandHandlerDispatcher {

    private final Map<String, CommandHandler> startCommandHandlerMap;
    private final Map<State, CommandHandler> commandHandlerMap;

    @Override
    public CommandHandler getCommandHandler(Event event) {
        Command command = event.getCommand();
        User user = event.getUser();

        if (startCommandHandlerMap.containsKey(command.getPrefix())) {
            return startCommandHandlerMap.get(command.getPrefix());
        }
        return commandHandlerMap.getOrDefault(State.valueOf(user.getState()), null);
    }
}