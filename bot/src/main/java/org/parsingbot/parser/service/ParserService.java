package org.parsingbot.parser.service;

import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Map;

/**
 * Сервис для взаимодействия с парсером в удобном для бота формате
 */
public interface ParserService {

    /**
     * @param event обертка над update, user и command
     * @return список вакансий
     */
    List<Vacancy> getVacancies(Event event);

    /**
     * @param user              объект пользователя
     * @param parsingParameters параметры поиска
     * @return список вакансий
     */
    List<Vacancy> getVacancies(User user, Map<String, String> parsingParameters);

    /**
     * @param event обертка над update, user и command
     * @return список сообщений для пользователя (каждое сообщение содержит по одной вакансии)
     */
    List<SendMessage> getVacanciesMessageList(Event event);

    /**
     * @param user              объект пользователя
     * @param parsingParameters параметры поиска
     * @return список сообщений для пользователя (каждое сообщение содержит по одной вакансии)
     */
    List<SendMessage> getVacanciesMessageList(User user, Map<String, String> parsingParameters);

    /**
     * @param event обертка над update, user и command
     * @return сообщений для пользователя (все вакансии в одном сообщении)
     */
    SendMessage getVacanciesSingleMessage(Event event);
}