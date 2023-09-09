package org.parsingbot.service.receiver;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс валидатора входящего update
 */
public interface UpdateValidator {

    /**
     * Метод обработки входящего update
     *
     * @param update объект Update от юзера
     * @return сообщение об ошибке или null если update валидный
     */
    String validate(Update update);
}