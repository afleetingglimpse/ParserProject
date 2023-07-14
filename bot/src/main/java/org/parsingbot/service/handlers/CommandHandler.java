package org.parsingbot.service.handlers;

/**
 * Интерфейс обработки сообщений, являющихся командами
 */
public interface CommandHandler {

    /** Метод определяет, является ли сообщение командой
     *  @param message сообщение
     *  @return true/false если сообщение является/не является командой */
    boolean isCommand(String message);

    /** Метод реализующий логику выполнения команды
     *  @param command команда */
    void handleCommand(String command);
}
