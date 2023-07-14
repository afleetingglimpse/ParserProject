package org.parsingbot.configuration;

import org.parsingbot.repository.UserRepository;
import org.parsingbot.service.bot.AbstractBot;
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

@Configuration
@EnableJpaRepositories("org.parsingbot")
@Import(BotParametersProvider.class)
public class BotConfiguration {

//    @Bean
//    BotParametersProvider botParametersProvider() {
//        return new BotParametersProvider();
//    }

    @Bean
    CommandHandler commandHandler() {
        return new BaseCommandHandler();
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
    UpdateHandler updateHandler(CommandHandler commandHandler,
                                ResponseHandler responseHandler,
                                UserAuthService userAuthService) {
        return new BaseUpdateHandler(
                commandHandler,
                responseHandler,
                userAuthService);
    }

    @Bean
    TelegramBot bot(BotParametersProvider botParametersProvider, UpdateHandler updateHandler) {
        return new TelegramBot(botParametersProvider, updateHandler);
    }

    @Bean
    BotInitializer botInitializer(TelegramBot bot) {
        return new BotInitializer(bot);
    }
}
