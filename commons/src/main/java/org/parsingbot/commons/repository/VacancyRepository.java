package org.parsingbot.commons.repository;

import org.parsingbot.commons.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
}
