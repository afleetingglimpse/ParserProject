package org.parsingbot.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Command;
import org.parsingbot.service.commands.CommandHandler;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
public class HhStartCommandHandler implements CommandHandler {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Command command, Update update) {
        return null;
    }
}