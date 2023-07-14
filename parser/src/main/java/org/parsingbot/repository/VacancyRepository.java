package org.parsingbot.repository;

import org.parsingbot.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс взаимодействия с БД
 */
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
}
