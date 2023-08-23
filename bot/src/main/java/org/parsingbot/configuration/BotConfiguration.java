package org.parsingbot.configuration;

import org.parsingbot.repository.UserRepository;
import org.parsingbot.service.Parser;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.bot.BotParametersProvider;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.handlers.CommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.handlers.UpdateHandler;
import org.parsingbot.service.handlers.impl.BaseCommandHandler;
import org.parsingbot.service.handlers.impl.BaseResponseHandler;
import org.parsingbot.service.handlers.impl.BaseUpdateHandler;
import org.parsingbot.service.UserService;
import org.parsingbot.service.UserAuthService;
import org.parsingbot.service.impl.UserAuthServiceImpl;
import org.parsingbot.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Import({BotParametersProvider.class,
        ParserConfiguration.class,
        CommonConfiguration.class})
public class BotConfiguration {

    @Bean
    CommandHandler commandHandler(ResponseHandler responseHandler,
                                  VacancyService vacancyService) {
        return new BaseCommandHandler(responseHandler, vacancyService);
    }

    @Bean
    ResponseHandler responseHandler() {
        return new BaseResponseHandler();
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
