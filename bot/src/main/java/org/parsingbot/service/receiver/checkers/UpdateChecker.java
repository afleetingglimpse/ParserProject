package org.parsingbot.service.receiver.checkers;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс валидатора входящего update
 */
public interface UpdateChecker {

    /**
     * Метод обработки входящего update
     *
     * @param update объект Update от юзера
     * @return сообщение об ошибке или null если update валидный
     */
    String checkUpdate(Update update);
}