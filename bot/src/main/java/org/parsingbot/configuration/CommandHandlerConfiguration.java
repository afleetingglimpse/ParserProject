package org.parsingbot.configuration;

import org.parsingbot.entity.CommandDto;
import org.parsingbot.entity.CommandEnum;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.parsingbot.service.commands.impl.CommandHandlerDispatcherImpl;
import org.parsingbot.service.commands.impl.HhCommandHandler;
import org.parsingbot.service.handlers.ResponseHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandHandlerConfiguration {

    private static final String HH_COMMAND = "/hh";

    @Bean
    public CommandHandler hhCommandHandler(ResponseHandler responseHandler,
                                           VacancyService vacancyService,
                                           UserService userService) {
        return new HhCommandHandler(
                responseHandler,
                vacancyService,
                userService);
    }

    @Bean
    @Qualifier("commandHandlerMap")
    public Map<String, CommandHandler> commandHandlerMap(CommandHandler hhCommandHandler) {
        Map<String, CommandHandler> commandHandlerMap = new HashMap<>();
        commandHandlerMap.put(CommandEnum.HH_COMMAND.getCommandDto().getPrefix(), hhCommandHandler);
        return commandHandlerMap;
    }

    @Bean
    public CommandHandlerDispatcher commandHandlerDispatcher(
            @Qualifier("commandHandlerMap") Map<String, CommandHandler> commandHandlerMap) {
        return new CommandHandlerDispatcherImpl(commandHandlerMap);
    }
}
