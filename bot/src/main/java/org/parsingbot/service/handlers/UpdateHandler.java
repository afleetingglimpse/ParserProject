package org.parsingbot.service.handlers;

import org.parsingbot.service.bot.impl.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс обработки событий бота
 */
public interface UpdateHandler {

    /**
     * Метод обработки событий
     *
     * @param bot    бот, в который пришло событие (костыль)
     * @param update событие
     */
    void handleUpdate(TelegramBot bot, Update update);
}
