package org.parsingbot.service.commands;

import org.parsingbot.entity.Command;
import org.parsingbot.service.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

/**
 * Интерфейс обработки сообщений, являющихся командами
 */
public interface CommandHandler {

    /**
     * Метод реализующий логику выполнения команды в событии
     *
     * @param command обертка над объектом команды от пользователя
     * @param update     объект Update передаваемый вместе с командой
     */
    List<PartialBotApiMethod<? extends Serializable>> handleCommand(Command command, Update update);
}