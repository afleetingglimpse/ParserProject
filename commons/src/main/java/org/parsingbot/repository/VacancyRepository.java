package org.parsingbot.repository;

import org.parsingbot.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
}
