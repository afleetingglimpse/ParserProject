package org.parsingbot.configuration;

import org.parsingbot.entity.CommandEnum;
import org.parsingbot.entity.State;
import org.parsingbot.parser.service.ParserService;
import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.parsingbot.service.commands.CommandParser;
import org.parsingbot.service.commands.impl.CommandHandlerDispatcherImpl;
import org.parsingbot.service.commands.impl.SubscribeCommandHandler;
import org.parsingbot.service.commands.impl.UnsubscribeCommandHandler;
import org.parsingbot.service.commands.impl.hh.*;
import org.parsingbot.service.handlers.ResponseHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandHandlerConfiguration {

    @Bean
    public CommandHandler hhStart0CommandHandler(UserService userService) {
        return new HhStart0CommandHandler(userService);
    }

    @Bean
    public CommandHandler hhVacancySelect1CommandHandler(UserService userService) {
        return new HhVacancySelect1CommandHandler(userService);
    }

    @Bean
    public CommandHandler hhNumberOfVacanciesSelect2CommandHandler(UserService userService) {
        return new HhNumberOfVacanciesSelect2CommandHandler(userService);
    }

    @Bean
    public CommandHandler hhKeywordsSelect3CommandHandler(UserService userService,
                                                          ParserService parserService) {
        return new HhKeywordsSelect3CommandHandler(userService, parserService);
    }

    @Bean
    public CommandParser hhCommandParser() {
        return new HhCommandParser();
    }

    @Bean
    public CommandHandler hhCommandHandler(VacancyService vacancyService,
                                           UserService userService,
                                           CommandParser hhCommandParser,
                                           Parser parser) {
        return new HhCommandHandler(
                vacancyService,
                userService,
                hhCommandParser,
                parser);
    }

    @Bean
    public CommandHandler subscribeCommandHandler(UserService userService,
                                                  ResponseHandler responseHandler) {
        return new SubscribeCommandHandler(
                userService,
                responseHandler);
    }

    @Bean
    public CommandHandler unsubscribeCommandHandler(UserService userService,
                                                    ResponseHandler responseHandler) {
        return new UnsubscribeCommandHandler(
                userService,
                responseHandler);
    }

    @Bean
    @Qualifier("startCommandHandlerMap")
    public Map<String, CommandHandler> startCommandHandlerMap(CommandHandler hhStart0CommandHandler,
                                                              CommandHandler subscribeCommandHandler,
                                                              CommandHandler unsubscribeCommandHandler) {
        Map<String, CommandHandler> startCommandHandlerMap = new HashMap<>();
        startCommandHandlerMap.put(CommandEnum.HH_COMMAND.getPrefix(), hhStart0CommandHandler);
        startCommandHandlerMap.put(CommandEnum.SUBSCRIBE_COMMAND.getPrefix(), subscribeCommandHandler);
        startCommandHandlerMap.put(CommandEnum.UNSUBSCRIBE_COMMAND.getPrefix(), unsubscribeCommandHandler);
        return startCommandHandlerMap;
    }

    @Bean
    @Qualifier("commandHandlerMap")
    public Map<State, CommandHandler> commandHandlerMap(CommandHandler hhVacancySelect1CommandHandler,
                                                        CommandHandler hhNumberOfVacanciesSelect2CommandHandler,
                                                        CommandHandler hhKeywordsSelect3CommandHandler) {
        Map<State, CommandHandler> commandHandlerMap = new EnumMap<>(State.class);
        commandHandlerMap.put(State.HH_VACANCY_SELECT_1, hhVacancySelect1CommandHandler);
        commandHandlerMap.put(State.HH_NUMBER_OF_VACANCIES_SELECT_2, hhNumberOfVacanciesSelect2CommandHandler);
        commandHandlerMap.put(State.HH_KEYWORDS_SELECT_3, hhKeywordsSelect3CommandHandler);
        return commandHandlerMap;
    }

    @Bean
    public CommandHandlerDispatcher commandHandlerDispatcher(
            @Qualifier("startCommandHandlerMap") Map<String, CommandHandler> startCommandHandlerMap,
            @Qualifier("commandHandlerMap") Map<State, CommandHandler> commandHandlerMap
    ) {
        return new CommandHandlerDispatcherImpl(startCommandHandlerMap, commandHandlerMap);
    }
}