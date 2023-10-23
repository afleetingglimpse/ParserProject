package org.parsingbot.parser.configuration;

import org.parsingbot.commons.configuration.CommonConfiguration;
import org.parsingbot.parser.service.Parser;
import org.parsingbot.parser.service.VacancyBrowser;
import org.parsingbot.parser.service.VacancyFilter;
import org.parsingbot.parser.service.impl.HhParser;
import org.parsingbot.parser.service.impl.HhVacancyBrowser;
import org.parsingbot.parser.service.impl.HhVacancyFilter;
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