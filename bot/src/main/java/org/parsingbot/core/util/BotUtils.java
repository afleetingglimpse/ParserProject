package org.parsingbot.core.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@UtilityClass
public class BotUtils {

    private static final String NOT_AUTHORISED_FOR_COMMAND_ERROR = "You are not authorised to use that command";
    private static final String NOT_A_COMMAND_ERROR = "Your message is not a command. Type /help to see the commands list";
    private static final String INVALID_STATE_ERROR = "You can't call that command right now";

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

    public static SendMessage userUnauthorisedMessage(Long chatId) {
        return createMessage(chatId, NOT_AUTHORISED_FOR_COMMAND_ERROR);
    }

    public static SendMessage commandNotFoundError(Long chatId) {
        return createMessage(chatId, NOT_A_COMMAND_ERROR);
    }

    public static SendMessage userIsNotInRequiredState(Long chatId) {
        return createMessage(chatId, INVALID_STATE_ERROR);
    }
}