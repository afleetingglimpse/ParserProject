package org.parsingbot.configuration;

import org.parsingbot.service.receiver.checkers.CommandAuthorisationChecker;
import org.parsingbot.service.receiver.checkers.UpdateChecker;
import org.parsingbot.service.receiver.checkers.impl.CommandAuthorisationCheckerImpl;
import org.parsingbot.service.receiver.checkers.impl.UpdateCheckerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckersConfiguration {

    @Bean
    public UpdateChecker updateChecker() {
        return new UpdateCheckerImpl();
    }

    @Bean
    public CommandAuthorisationChecker commandAuthorisationChecker() {
        return new CommandAuthorisationCheckerImpl();
    }
}