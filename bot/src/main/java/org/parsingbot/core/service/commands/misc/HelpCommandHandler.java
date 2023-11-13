package org.parsingbot.core.service.commands.misc;

import org.parsingbot.commons.entity.CommandEnum;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.State;
import org.parsingbot.core.service.commands.CommandHandler;
import org.parsingbot.core.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

public class HelpCommandHandler implements CommandHandler {

    private static final CommandEnum[] AVAILABLE_COMMANDS = CommandEnum.values();

    private static final String AVAILABLE_COMMANDS_MESSAGE = "Доступные команды:\n\n";

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(AVAILABLE_COMMANDS_MESSAGE);
        for (CommandEnum command : AVAILABLE_COMMANDS) {
            sb.append(command.getPrefix()).append("\n");
        }
        return List.of(BotUtils.createMessage(event.getChatId(), sb.toString()));
    }

    @Override
    public State getRequiredState() {
        return State.ANY;
    }
}