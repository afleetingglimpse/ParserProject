package org.parsingbot.service.handlers;

import org.parsingbot.service.bot.impl.TelegramBot;

public interface ResponseHandler {
    void sendResponse(TelegramBot bot, String message, long chatId);
}
