package org.parsingbot.configuration;

import org.parsingbot.schedule.ScheduleService;
import org.parsingbot.service.bot.impl.TelegramBot;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource("schedule.properties")
public class ScheduleConfiguration {

    @Bean
    ScheduleService scheduleService(TelegramBot bot,
                                    ResponseHandler responseHandler,
                                    UserService userService) {
        return new ScheduleService(bot, responseHandler, userService);
    }

}
