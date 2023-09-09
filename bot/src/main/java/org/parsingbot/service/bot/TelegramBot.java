package org.parsingbot.service.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.service.Parser;
import org.parsingbot.service.receiver.UpdateReceiver;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Getter
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotParametersProvider parametersProvider;
    private final UpdateReceiver updateReceiver;
    private final Parser parser;

    @Override
    public void onUpdateReceived(Update update) {
        updateReceiver.handleUpdate(this, update);
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