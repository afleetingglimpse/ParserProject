package org.parsingbot.schedule;

import lombok.RequiredArgsConstructor;
import org.parsingbot.service.bot.impl.TelegramBot;
import org.parsingbot.service.handlers.ResponseHandler;
import org.parsingbot.service.user.User;
import org.parsingbot.service.user.UserService;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@RequiredArgsConstructor
public class ScheduleService {

    private final TelegramBot bot;
    private final ResponseHandler responseHandler;
    private final UserService userService;

    @Scheduled(fixedDelayString = "${fixedDelayMS}")
    public void sendDataEverySec() {
        List<User> subscribedUsers = userService.getSubscribedUsers();
        subscribedUsers.forEach(user -> responseHandler.sendResponse(bot, "Hi", user.getChatId()));
    }
}
