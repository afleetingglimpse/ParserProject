package org.parsingbot.configuration;

import org.parsingbot.repository.VacancyRepository;
import org.parsingbot.service.Parser;
import org.parsingbot.service.VacancyBrowser;
import org.parsingbot.service.VacancyFilter;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.impl.HhParser;
import org.parsingbot.service.impl.HhVacancyBrowser;
import org.parsingbot.service.impl.HhVacancyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CommonConfiguration.class)
public class ParserConfiguration {

    @Bean
    public VacancyFilter vacancyFilter() {
        return new HhVacancyFilter();
    }

    @Bean
    public VacancyBrowser vacancyBrowser() {
        return new HhVacancyBrowser();
    }

    @Bean
    public Parser parser(VacancyBrowser vacancyBrowser) {
        return new HhParser(vacancyBrowser);
    }
}

