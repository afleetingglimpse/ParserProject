package org.parsingbot.util;

import lombok.experimental.UtilityClass;
import org.parsingbot.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@UtilityClass
public class BotUtils {

    public static SendMessage createMessageTemplate(Long chatId, String text, ReplyKeyboard replyKeyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(replyKeyboard)
                .build();
    }

    public static InlineKeyboardButton createInlineKeyboardButton(Object text, Object callbackData) {
        return InlineKeyboardButton.builder()
                .text(text.toString())
                .callbackData(callbackData.toString())
                .build();
    }

    public static SendMessage createMessage(Long chatId, String messageText) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(messageText)
                .build();
    }
}