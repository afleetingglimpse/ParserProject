package org.parsingbot.utils;

import org.parsingbot.entity.Vacancy;

import java.util.function.Predicate;

public class VacancyPredicates {
    public static final Predicate<Vacancy> JUNIOR =
            vacancy -> vacancy.getVacancyName().toLowerCase().contains("junior");

    public static final Predicate<Vacancy> MIDDLE =
            vacancy -> vacancy.getVacancyName().toLowerCase().contains("middle");

    public static final Predicate<Vacancy> SENIOR =
            vacancy -> vacancy.getVacancyName().toLowerCase().contains("senior");
}
