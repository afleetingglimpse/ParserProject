package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Command;
import org.parsingbot.entity.User;
import org.parsingbot.entity.State;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;

import java.util.Map;

@RequiredArgsConstructor
public class CommandHandlerDispatcherImpl implements CommandHandlerDispatcher {

    private final Map<String, CommandHandler> commandHandlerMap;

    @Override
    public CommandHandler getCommandHandler(Command command, User user) {
        String state = !user.getState().equals(State.NONE.toString()) ? user.getState() : "";
        return commandHandlerMap.getOrDefault(command.getPrefix().concat(state), null);
    }
}