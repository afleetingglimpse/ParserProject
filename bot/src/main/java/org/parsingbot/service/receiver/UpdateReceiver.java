package org.parsingbot.service.receiver;

import org.parsingbot.service.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс обработки update от пользователя
 */
public interface UpdateReceiver {

    /**
     * Метод обработки входного update
     *
     * @param bot объект бота, получающий update от пользователя
     * @param update update от пользователя
     */
    void handleUpdate(TelegramBot bot, Update update);
}