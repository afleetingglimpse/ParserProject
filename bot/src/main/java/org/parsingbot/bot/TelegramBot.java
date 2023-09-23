package org.parsingbot.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.service.receiver.UpdateReceiver;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Getter
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotParametersProvider parametersProvider;
    private final UpdateReceiver updateReceiver;

    @Override
    public void onUpdateReceived(Update update) {
        var botApiMethods = updateReceiver.handleUpdate(update);
        processMethods(botApiMethods);
    }

    @Override
    public String getBotUsername() {
        return parametersProvider.getBotName();
    }

    @Override
    public String getBotToken() {
        return parametersProvider.getToken();
    }

    private void processMethods(List<PartialBotApiMethod<? extends Serializable>> botApiMethods) {
        if (botApiMethods == null) {
            return;
        }

        botApiMethods.forEach((method) -> {
            if (method != null) {
                try {
                    execute((SendMessage) method);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}