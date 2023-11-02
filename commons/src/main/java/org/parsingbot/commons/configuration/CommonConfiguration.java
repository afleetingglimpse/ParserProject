package org.parsingbot.commons.configuration;

import org.parsingbot.commons.repository.SearchHistoryRepository;
import org.parsingbot.commons.repository.UserRepository;
import org.parsingbot.commons.repository.VacancyRepository;
import org.parsingbot.commons.service.SearchHistoryService;
import org.parsingbot.commons.service.UserAuthService;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.commons.service.VacancyService;
import org.parsingbot.commons.service.impl.SearchHistoryServiceImpl;
import org.parsingbot.commons.service.impl.UserAuthServiceImpl;
import org.parsingbot.commons.service.impl.UserServiceImpl;
import org.parsingbot.commons.service.impl.VacancyServiceImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("org.parsingbot.commons")
@EnableJpaRepositories("org.parsingbot.commons")
public class CommonConfiguration {

    @Bean
    public VacancyService vacancyService(VacancyRepository vacancyRepository) {
        return new VacancyServiceImpl(vacancyRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public UserAuthService userAuthService(UserService userService) {
        return new UserAuthServiceImpl(userService);
    }

    @Bean
    public SearchHistoryService searchHistoryService(SearchHistoryRepository searchHistoryRepository) {
        return new SearchHistoryServiceImpl(searchHistoryRepository);
    }
}