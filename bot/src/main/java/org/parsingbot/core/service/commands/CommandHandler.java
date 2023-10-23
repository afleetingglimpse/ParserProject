package org.parsingbot.core.service.commands;

import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.service.Authorisation;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Интерфейс обработки сообщений, являющихся командами
 */
public interface CommandHandler {

    /**
     * Метод реализующий логику выполнения команды в событии
     *
     * @param event обертка над update, user и command
     * @return список методов бота, передаваемых в bot.execute
     */
    List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event);


    /**
     * @return состояние пользователя, необходимое для вызова обработчика команд
     */
    default State getRequiredState() {
        return State.NONE;
    }

    /**
     * @return авторизация пользователя, необходимая для вызова обработчика команд
     */
    default Authorisation getRequiredAuthorisation() {
        return Authorisation.USER;
    }
}