package org.parsingbot.service.commands;

import org.parsingbot.entity.CommandDto;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс обработки сообщений, являющихся командами
 */
public interface CommandHandler {

    /**
     * Метод реализующий логику выполнения команды в событии
     *
     * @param commandDto обертка над объектом команды от пользователя
     * @param update     объект Update передаваемый вместе с командой
     */
    void handleCommand(CommandDto commandDto, Update update);
}
