package org.parsingbot.configuration;

import org.parsingbot.schedule.ScheduleService;
import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.handlers.ResponseHandler;
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
                                    ResponseHandler responseHandler,
                                    UserService userService,
                                    VacancyService vacancyService,
                                    Parser parser) {
        return new ScheduleService(bot,
                responseHandler,
                userService,
                vacancyService,
                parser);
    }
}