package org.parsingbot.commons.repository;

import org.parsingbot.commons.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {

    @Query(value = "select * from search_history where user_id = ?",
            nativeQuery = true)
    SearchHistory findSearchHistoryByUserId(Long userId);
}