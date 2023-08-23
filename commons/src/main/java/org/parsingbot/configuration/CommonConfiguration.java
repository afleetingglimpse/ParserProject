package org.parsingbot.configuration;

import org.parsingbot.repository.UserRepository;
import org.parsingbot.repository.VacancyRepository;
import org.parsingbot.service.UserAuthService;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.impl.UserAuthServiceImpl;
import org.parsingbot.service.impl.UserServiceImpl;
import org.parsingbot.service.impl.VacancyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.parsingbot")
public class CommonConfiguration {

    @Bean
    public VacancyService vacancyService(VacancyRepository vacancyRepository, UserRepository userRepository) {
        return new VacancyServiceImpl(vacancyRepository, userRepository);
    }

    @Bean
    public UserService userService(VacancyRepository vacancyRepository, UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    UserAuthService userAuthService(UserService userService) {
        return new UserAuthServiceImpl(userService);
    }
}
