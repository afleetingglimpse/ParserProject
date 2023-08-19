package org.parsingbot.service.handlers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.handlers.ResponseHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
public class BaseResponseHandler implements ResponseHandler {

    private final static String MESSAGE_SENDING_ERROR = "Failed to send message";

    @Override
    public void sendResponse(TelegramBot bot, String message, long chatId) {
        SendMessage msg = new SendMessage(String.valueOf(chatId), message);
        try {
            bot.execute(msg);
        } catch (TelegramApiException e) {
            log.error(MESSAGE_SENDING_ERROR);
        }
    }
}
