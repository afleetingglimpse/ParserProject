package org.parsingbot.configuration;

import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.bot.BotParametersProvider;
import org.parsingbot.service.bot.TelegramBot;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.handlers.impl.BaseResponseHandler;
import org.parsingbot.service.receiver.UpdateReceiver;
import org.parsingbot.service.receiver.checkers.CommandAuthorisationChecker;
import org.parsingbot.service.receiver.checkers.UpdateChecker;
import org.parsingbot.service.receiver.impl.UpdateReceiverImpl;
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
    public ResponseHandler responseHandler() {
        return new BaseResponseHandler();
    }

    @Bean
    public UpdateReceiver updateReceiver(UserService userService,
                                         UpdateChecker updateChecker,
                                         ResponseHandler responseHandler,
                                         CommandAuthorisationChecker commandAuthorisationChecker,
                                         CommandHandlerDispatcher commandHandlerDispatcher) {
        return new UpdateReceiverImpl(
                userService,
                updateChecker,
                responseHandler,
                commandAuthorisationChecker,
                commandHandlerDispatcher);
    }

    @Bean
    public TelegramBot bot(BotParametersProvider botParametersProvider,
                           UpdateReceiver updateReceiver,
                           Parser parser) {
        return new TelegramBot(botParametersProvider,
                updateReceiver,
                parser);
    }

    @Bean
    public BotInitializer botInitializer(TelegramBot bot) {
        return new BotInitializer(bot);
    }
}
