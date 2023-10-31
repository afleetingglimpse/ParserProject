package org.parsingbot.commons.utils;

import lombok.experimental.UtilityClass;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class SearchHistoryUtils {

    public static final String DEFAULT_VACANCY_NAME = "none";
    public static final Long DEFAULT_NUMBER_OF_VACANCIES = 0L;
    public static final String DEFAULT_KEYWORDS = "none";

    public SearchHistory getLastSearchHistoryOrCreateNew(User user) {
        List<SearchHistory> searchHistories = user.getSearchHistories();
        if (searchHistories == null) {
            searchHistories = new ArrayList<>();
            user.setSearchHistories(searchHistories);
        }

        if (searchHistories.isEmpty()) {
            SearchHistory newSearchHistory = SearchHistory.builder()
                    .vacancyName(DEFAULT_VACANCY_NAME)
                    .numberOfVacancies(DEFAULT_NUMBER_OF_VACANCIES)
                    .keywords(DEFAULT_KEYWORDS)
                    .user(user)
                    .build();
            user.addSearchHistory(newSearchHistory);
        }
        return searchHistories.get(searchHistories.size() - 1);
    }
}