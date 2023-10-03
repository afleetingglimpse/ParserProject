package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Command;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
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
        User user = event.getUser();
        Command command = event.getCommand();

        if (startCommandHandlerMap.containsKey(command.getPrefix())) {
            return startCommandHandlerMap.get(command.getPrefix());
        }
        return commandHandlerMap.getOrDefault(State.valueOf(user.getState()), null);
    }
}