package org.parsingbot.service.handlers;

import org.parsingbot.service.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс обработки сообщений, являющихся командами
 */
public interface CommandHandler {

    /** Метод определяет, является ли сообщение командой
     *  @param command команда
     *  @return true/false если сообщение является/не является командой */
    boolean isCommand(String command);

    /** Метод реализующий логику выполнения команды в событии
     *  @param update событие */
    void handleCommand(TelegramBot bot, Update update);
}
