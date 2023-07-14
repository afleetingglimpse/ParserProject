package org.parsingbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.repository.VacancyRepository;
import org.parsingbot.service.VacancyFilter;
import org.parsingbot.service.VacancyService;

import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class HhVacancyService implements VacancyService {

    private final VacancyFilter vacancyFilter;
    private final VacancyRepository vacancyRepository;

    @Override
    public void save(List<Vacancy> vacancies) {
        for (Vacancy vacancy : vacancies) {
            vacancyRepository.save(vacancy);
        }
    }

    @Override
    public void save(Vacancy vacancy) {
        vacancyRepository.save(vacancy);
    }

    @Override
    public List<Vacancy> getVacancies(Predicate<Vacancy> filter) {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        return filter != null ? vacancyFilter.getFilteredVacancy(vacancies, filter) : vacancies;
    }
}
