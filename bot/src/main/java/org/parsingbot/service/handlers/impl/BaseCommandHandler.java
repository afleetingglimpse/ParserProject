package org.parsingbot.service.handlers.impl;

import org.parsingbot.service.handlers.CommandHandler;

public class BaseCommandHandler implements CommandHandler {
    @Override
    public boolean isCommand(String message) {
        return false;
    }

    @Override
    public void handleCommand(String command) {

    }
}
