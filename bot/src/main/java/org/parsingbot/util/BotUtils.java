package org.parsingbot.util;

import lombok.experimental.UtilityClass;
import org.parsingbot.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@UtilityClass
public class BotUtils {
    public static SendMessage createMessageTemplate(User user) {
        return createMessageTemplate(String.valueOf(user.getChatId()));
    }

    public static SendMessage createMessageTemplate(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(command)
                .build();
    }

    public static SendMessage createMessage(long chatId, String messageText) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(messageText)
                .build();
    }
}