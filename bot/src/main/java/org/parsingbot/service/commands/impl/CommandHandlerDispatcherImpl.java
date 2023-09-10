package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Command;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;

import java.util.Map;

@RequiredArgsConstructor
public class CommandHandlerDispatcherImpl implements CommandHandlerDispatcher {

    private final Map<String, CommandHandler> commandHandlerMap;

    @Override
    public CommandHandler getCommandHandler(Command command) {
        return commandHandlerMap.getOrDefault(command.getPrefix(), null);
    }
}