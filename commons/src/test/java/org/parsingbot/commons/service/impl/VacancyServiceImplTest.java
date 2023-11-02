package org.parsingbot.commons.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.commons.entity.Vacancy;
import org.parsingbot.commons.repository.VacancyRepository;
import org.parsingbot.commons.service.VacancyService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты на сервис для работы с Vacancy")
class VacancyServiceImplTest {

    @Mock
    private VacancyRepository vacancyRepository;

    @InjectMocks
    private VacancyServiceImpl sut;

    @Test
    @DisplayName("Тест метода save(List<Vacancy>)")
    void saveListTest() {
        List<Vacancy> vacancies = new ArrayList<>();
        sut.save(vacancies);
        verify(vacancyRepository).saveAll(vacancies);
    }

    @Test
    @DisplayName("Тест метода save(Vacancy)")
    void saveTest() {
        Vacancy vacancy = Vacancy.builder().build();
        sut.save(vacancy);
        verify(vacancyRepository).save(vacancy);
    }
}