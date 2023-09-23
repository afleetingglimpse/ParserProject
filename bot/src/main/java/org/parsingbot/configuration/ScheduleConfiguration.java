package org.parsingbot.configuration;

import org.parsingbot.parser.service.ParserService;
import org.parsingbot.schedule.ScheduleService;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.bot.TelegramBot;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource("schedule.properties")
public class ScheduleConfiguration {

    @Bean
    @ConditionalOnProperty(value = "scheduler.enabled")
    ScheduleService scheduleService(TelegramBot bot,
                                    UserService userService,
                                    VacancyService vacancyService,
                                    ParserService parserService) {
        return new ScheduleService(bot,
                userService,
                vacancyService,
                parserService);
    }
}