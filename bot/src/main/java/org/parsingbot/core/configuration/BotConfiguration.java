package org.parsingbot.core.configuration;

import org.parsingbot.core.bot.BotParametersProvider;
import org.parsingbot.core.bot.TelegramBot;
import org.parsingbot.commons.configuration.CommonConfiguration;
import org.parsingbot.core.parser.service.ParserService;
import org.parsingbot.core.parser.service.impl.ParserServiceImpl;
import org.parsingbot.core.service.receiver.CommandHandlerDispatcher;
import org.parsingbot.parser.configuration.ParserConfiguration;
import org.parsingbot.parser.service.Parser;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.commons.service.VacancyService;
import org.parsingbot.core.service.receiver.UpdateReceiver;
import org.parsingbot.core.service.validation.CommandHandlerValidator;
import org.parsingbot.core.service.validation.impl.CommandHandlerValidatorImpl;
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
    public ParserService parserService(Parser parser, VacancyService vacancyService) {
        return new ParserServiceImpl(parser, vacancyService);
    }

    @Bean
    public CommandHandlerValidator commandHandlerValidator() {
        return new CommandHandlerValidatorImpl();
    }

    @Bean
    public UpdateReceiver updateReceiver(UserService userService,
                                         CommandHandlerDispatcher commandHandlerDispatcher,
                                         CommandHandlerValidator commandHandlerValidator) {
        return new UpdateReceiver(
                userService,
                commandHandlerDispatcher,
                commandHandlerValidator);
    }

    @Bean
    public TelegramBot bot(BotParametersProvider botParametersProvider,
                           UpdateReceiver updateReceiver) {
        return new TelegramBot(botParametersProvider,
                updateReceiver);
    }

    @Bean
    public BotInitializer botInitializer(TelegramBot bot) {
        return new BotInitializer(bot);
    }
}
