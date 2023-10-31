package org.parsingbot.commons.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.repository.SearchHistoryRepository;
import org.parsingbot.commons.service.SearchHistoryService;

@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    @Override
    public SearchHistory save(SearchHistory searchHistory) {
        return searchHistoryRepository.save(searchHistory);
    }
}
