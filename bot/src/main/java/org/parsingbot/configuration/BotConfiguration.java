package org.parsingbot.configuration;

import org.parsingbot.repository.UserRepository;
import org.parsingbot.schedule.ScheduleService;
import org.parsingbot.service.Parser;
import org.parsingbot.service.bot.BotParametersProvider;
import org.parsingbot.service.bot.impl.TelegramBot;
import org.parsingbot.service.handlers.CommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.handlers.UpdateHandler;
import org.parsingbot.service.handlers.impl.BaseCommandHandler;
import org.parsingbot.service.handlers.impl.BaseResponseHandler;
import org.parsingbot.service.handlers.impl.BaseUpdateHandler;
import org.parsingbot.service.user.UserService;
import org.parsingbot.service.user.auth.UserAuthService;
import org.parsingbot.service.user.auth.impl.BaseUserAuthService;
import org.parsingbot.service.user.impl.BaseUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableJpaRepositories("org.parsingbot")
@Import({BotParametersProvider.class, ParserConfiguration.class})
public class BotConfiguration {

    @Bean
    CommandHandler commandHandler(ResponseHandler responseHandler) {
        return new BaseCommandHandler(responseHandler);
    }

    @Bean
    ResponseHandler responseHandler() {
        return new BaseResponseHandler();
    }

    @Bean
    UserService userService(UserRepository userRepository) {
        return new BaseUserService(userRepository);
    }

    @Bean
    UserAuthService userAuthService(UserService userService) {
        return new BaseUserAuthService(userService);
    }

    @Bean
    UpdateHandler updateHandler(BotParametersProvider botParametersProvider,
                                CommandHandler commandHandler,
                                ResponseHandler responseHandler,
                                UserAuthService userAuthService) {
        return new BaseUpdateHandler(
                botParametersProvider,
                commandHandler,
                responseHandler,
                userAuthService);
    }

    @Bean
    TelegramBot bot(BotParametersProvider botParametersProvider,
                    UpdateHandler updateHandler,
                    Parser parser) {
        return new TelegramBot(botParametersProvider,
                updateHandler,
                parser);
    }

    @Bean
    BotInitializer botInitializer(TelegramBot bot) {
        return new BotInitializer(bot);
    }
}
