package org.parsingbot.commons.service;

import org.parsingbot.commons.entity.SearchHistory;

/**
 * Сервисный класс для работы с SearchHistory
 */
public interface SearchHistoryService {

    SearchHistory save(SearchHistory searchHistory);
}
