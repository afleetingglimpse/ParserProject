package org.parsingbot.commons.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.commons.entity.Vacancy;
import org.parsingbot.commons.repository.VacancyRepository;
import org.parsingbot.commons.service.VacancyService;

import java.util.List;

@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    @Override
    public void save(List<Vacancy> vacancies) {
        vacancyRepository.saveAll(vacancies);
    }

    @Override
    public void save(Vacancy vacancy) {
        vacancyRepository.save(vacancy);
    }
}
