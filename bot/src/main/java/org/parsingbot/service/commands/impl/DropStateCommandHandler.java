package org.parsingbot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DropStateCommandHandler implements CommandHandler {

    private static final String STATE_CHANGED = "State changed to %s";

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        State newState = State.NONE;
        try {
            newState = State.valueOf(event.getCommand().getMessageWithoutPrefix());
        } catch (Exception e) {
            log.warn("State {} doesn't exist. User.state was set to State.NONE", event.getCommand().getMessageWithoutPrefix());
        }
        User user = event.getUser();
        user.setState(newState.toString());
        userService.save(user);
        log.info("State for user with chatId {} was set to {}", event.getChatId(), newState);
        return List.of(BotUtils.createMessage(event.getChatId(), String.format(STATE_CHANGED, newState)));
    }

    @Override
    public State getRequiredState() {
        return State.ANY;
    }
}