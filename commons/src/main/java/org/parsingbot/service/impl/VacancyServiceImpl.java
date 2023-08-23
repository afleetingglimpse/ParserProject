package org.parsingbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.repository.UserRepository;
import org.parsingbot.repository.VacancyRepository;
import org.parsingbot.service.VacancyService;

import java.util.List;

@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;

    @Override
    public void save(List<Vacancy> vacancies) {
        vacancyRepository.saveAll(vacancies);
    }

    @Override
    public void save(Vacancy vacancy) {
        vacancyRepository.save(vacancy);
    }

    @Override
    public List<Vacancy> getVacanciesByIds(List<Integer> idList) {
        return vacancyRepository.findAllById(idList);
    }
}
