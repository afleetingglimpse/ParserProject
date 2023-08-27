package org.parsingbot.service.commands;

import org.parsingbot.service.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс обработки сообщений, являющихся командами
 */
public interface CommandHandler {

    /**
     * Метод реализующий логику выполнения команды в событии
     *
     * @param update событие
     */
    void handleCommand(TelegramBot bot, Update update);
}
