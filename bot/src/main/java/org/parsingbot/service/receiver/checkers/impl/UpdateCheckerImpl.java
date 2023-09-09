package org.parsingbot.service.receiver.checkers.impl;

import lombok.extern.slf4j.Slf4j;
import org.parsingbot.service.receiver.checkers.UpdateChecker;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class UpdateCheckerImpl implements UpdateChecker {

    private static final String INVALID_UPDATE_ERROR = "userName or/and message not valid";

    @Override
    public String validate(Update update) {
        long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        String message = update.getMessage().getText();
        if (userName == null || message == null) {
            log.error(INVALID_UPDATE_ERROR);
            return INVALID_UPDATE_ERROR;
        }
        return null;
    }
}