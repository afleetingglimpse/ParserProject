package org.parsingbot.configuration;

import org.parsingbot.bot.BotParametersProvider;
import org.parsingbot.bot.TelegramBot;
import org.parsingbot.parser.service.ParserService;
import org.parsingbot.parser.service.impl.ParserServiceImpl;
import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.parsingbot.service.receiver.UpdateReceiver;
import org.parsingbot.service.receiver.impl.UpdateReceiverImpl;
import org.parsingbot.service.validation.CommandHandlerValidator;
import org.parsingbot.service.validation.impl.CommandHandlerValidatorImpl;
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
        return new UpdateReceiverImpl(
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
