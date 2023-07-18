package org.parsingbot.service.handlers;

import org.parsingbot.service.bot.impl.TelegramBot;

/**
 * Интерфейс отправки ответов на запросы пользователя
 */
public interface ResponseHandler {

    /**
     * Отправляет сообщение пользователю в чат
     *
     * @param bot     объект бота, от которого будет отправлено сообщение
     * @param message текст сообщения
     * @param chatId  идентификатор чата
     */
    void sendResponse(TelegramBot bot, String message, long chatId);
}
