package org.parsingbot.configuration;

import org.parsingbot.service.receiver.checkers.CommandChecker;
import org.parsingbot.service.receiver.checkers.UpdateChecker;
import org.parsingbot.service.receiver.checkers.impl.CommandCheckerImpl;
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
    public CommandChecker commandAuthorisationChecker() {
        return new CommandCheckerImpl();
    }
}