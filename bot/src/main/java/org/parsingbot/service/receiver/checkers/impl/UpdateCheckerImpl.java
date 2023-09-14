package org.parsingbot.service.receiver.checkers.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.parsingbot.service.receiver.checkers.UpdateChecker;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class UpdateCheckerImpl implements UpdateChecker {

    private static final String INVALID_UPDATE_ERROR = "userName or/and message not valid";

    @Override
    public String checkUpdate(@NonNull Update update) {
        String userName = update.getMessage().getChat().getUserName();
        String messageText = update.getMessage().getText();
        if (StringUtils.isAnyBlank(userName, messageText)) {
            log.error(INVALID_UPDATE_ERROR);
            return INVALID_UPDATE_ERROR;
        }
        return null;
    }
}