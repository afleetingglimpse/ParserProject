package org.parsingbot.service.handlers;

import org.parsingbot.service.bot.TelegramBot;

/**
 * Интерфейс отправки ответов на запросы пользователя
 */
@Deprecated(since = "18.09.2023", forRemoval = true)
public interface ResponseHandler {

    /**
     * Метод отправляет сообщение пользователю в чат
     *
     * @param bot     объект бота, от которого будет отправлено сообщение
     * @param message текст сообщения
     * @param chatId  идентификатор чата
     */
    void sendResponse(TelegramBot bot, String message, long chatId);
}