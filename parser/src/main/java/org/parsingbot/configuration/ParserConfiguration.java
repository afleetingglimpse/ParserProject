package org.parsingbot.configuration;

import org.parsingbot.repository.VacancyRepository;
import org.parsingbot.service.Parser;
import org.parsingbot.service.VacancyBrowser;
import org.parsingbot.service.VacancyFilter;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.impl.HhParser;
import org.parsingbot.service.impl.HhVacancyBrowser;
import org.parsingbot.service.impl.HhVacancyFilter;
import org.parsingbot.service.impl.HhVacancyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfiguration {

    @Bean
    VacancyFilter vacancyFilter() {
        return new HhVacancyFilter();
    }

    @Bean
    VacancyService vacancyService(VacancyFilter vacancyFilter, VacancyRepository vacancyRepository) {
        return new HhVacancyService(vacancyFilter, vacancyRepository);
    }

    @Bean
    VacancyBrowser vacancyBrowser() {
        return new HhVacancyBrowser();
    }

    @Bean
    Parser parser(VacancyService vacancyService, VacancyBrowser vacancyBrowser) {
        return new HhParser(vacancyService, vacancyBrowser);
    }

}

