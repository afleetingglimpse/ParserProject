package org.parsingbot.configuration;

import org.parsingbot.schedule.ScheduleService;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.user.UserService;
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
                                    UserService userService) {
        return new ScheduleService(bot, responseHandler, userService);
    }

}
