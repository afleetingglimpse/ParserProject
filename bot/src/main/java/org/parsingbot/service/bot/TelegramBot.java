package org.parsingbot.service.bot.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.service.bot.BotParametersProvider;
import org.parsingbot.service.Parser;
import org.parsingbot.service.handlers.UpdateHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Getter
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotParametersProvider parametersProvider;
    private final UpdateHandler updateHandler;
    private final Parser parser;

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

    public Parser getParser() {return parser;}



}
