package org.parsingbot.core.service.receiver;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

/**
 * Интерфейс обработки update от пользователя
 */
public interface UpdateReceiver {

    /**
     * Метод обработки входного update
     *
     * @param update update от пользователя
     * @return список сообщений пользователю
     */
    List<PartialBotApiMethod<? extends Serializable>> handleUpdate(Update update);
}