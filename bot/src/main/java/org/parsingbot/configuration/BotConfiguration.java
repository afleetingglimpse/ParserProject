package org.parsingbot.configuration;

import org.parsingbot.service.Parser;
import org.parsingbot.service.UserAuthService;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.bot.BotParametersProvider;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.handlers.CommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.handlers.UpdateHandler;
import org.parsingbot.service.handlers.impl.BaseCommandHandler;
import org.parsingbot.service.handlers.impl.BaseResponseHandler;
import org.parsingbot.service.handlers.impl.BaseUpdateHandler;
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
    public CommandHandler commandHandler(ResponseHandler responseHandler,
                                         VacancyService vacancyService,
                                         UserService userService) {
        return new BaseCommandHandler(responseHandler, vacancyService, userService);
    }

    @Bean
    public ResponseHandler responseHandler() {
        return new BaseResponseHandler();
    }

    @Bean
    public UpdateHandler updateHandler(BotParametersProvider botParametersProvider,
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
    public TelegramBot bot(BotParametersProvider botParametersProvider,
                           UpdateHandler updateHandler,
                           Parser parser) {
        return new TelegramBot(botParametersProvider,
                updateHandler,
                parser);
    }

    @Bean
    public BotInitializer botInitializer(TelegramBot bot) {
        return new BotInitializer(bot);
    }
}
