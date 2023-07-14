package org.parsingbot.service.bot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.configuration.BotParametersProvider;
import org.parsingbot.service.handlers.UpdateHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotParametersProvider parametersProvider;
    private final UpdateHandler updateHandler;

    @Override
    public void onUpdateReceived(Update update) {
        updateHandler.handleUpdate(this, update);
    }

    @Override
    public String getBotUsername() {
        return parametersProvider.getBotName();
    }

    @Override
    public String getBotToken() {
        return parametersProvider.getToken();
    }

}
