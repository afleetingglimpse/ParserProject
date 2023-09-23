package org.parsingbot.service.receiver.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parsingbot.entity.Command;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;
import org.parsingbot.service.UserService;
import org.parsingbot.bot.TelegramBot;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@DisplayName("Тест класса получателя сообщений от пользователя UpdateReceiverImpl")
@ExtendWith(MockitoExtension.class)
class UpdateReceiverImplTest {

    private final static TelegramBot BOT = null;
    private static final long CHAT_ID = 1;
    private static final String USER_NAME = "gwynae";
    private static final String MESSAGE_TEXT = "Мороз и солнце, день чудесный..";
    private static final Command COMMAND = new Command(MESSAGE_TEXT);
    private static final Update UPDATE = createUpdate();
    private static final User USER = User.builder().userName(USER_NAME).chatId(CHAT_ID).build();
    private static final Event EVENT = Event.builder().update(UPDATE).user(USER).command(COMMAND).build();

    private static final String INVALID_UPDATE_LOG = "Update object from user %s with chatId %s is not valid";
    private static final String NOT_AUTHORISED_FOR_COMMAND_LOG = "User %s with chatId %s is not authorised to use command %s";
    private static final String NOT_A_COMMAND_ERROR = "Your message is not a command. Type /help to see the commands list";
    private static final String COMMAND_DISPATCHER_NOT_FOUND =
            "User %s with chatId %s attempted to call command dispatcher for unknown command %s";
    private static final String PROCESSING_COMMAND = "User %s with chatId %s requested processing command %s";

    private ListAppender<ILoggingEvent> logWatcher;

    @Mock
    private UserService userService;
    @Mock
    private CommandHandlerDispatcher commandHandlerDispatcher;
    @InjectMocks
    private UpdateReceiverImpl updateReceiver;

    private static Update createUpdate() {
        Chat chat = new Chat();
        chat.setUserName(USER_NAME);
        chat.setId(CHAT_ID);

        Message message = new Message();
        message.setChat(chat);
        message.setText(MESSAGE_TEXT);

        Update update = new Update();
        update.setMessage(message);

        return update;
    }

//    @BeforeEach
//    void setup() {
//        logWatcher = new ListAppender<>();
//        ((Logger) LoggerFactory.getLogger(UpdateReceiverImpl.class)).addAppender(logWatcher);
//        logWatcher.start();
//        when(userService.getUserByChatIdCreateIfNotExist(CHAT_ID, USER_NAME)).thenReturn(USER);
//    }

//    @Test
//    @DisplayName("Тест метода handleUpdate с невалидным Update")
//    void handleUpdate_UpdateErrorTest() {
//        String expectedErrorMessage = "expectedErrorMessage";
//        when(updateChecker.checkUpdate(UPDATE)).thenReturn(expectedErrorMessage);
//
//        updateReceiver.handleUpdate(BOT, UPDATE);
//        assertEquals(String.format(INVALID_UPDATE_LOG, USER_NAME, CHAT_ID), logWatcher.list.get(0).getFormattedMessage());
//        assertEquals(Level.WARN, logWatcher.list.get(0).getLevel());
//        verify(responseHandler).sendResponse(BOT, expectedErrorMessage, CHAT_ID);
//        verifyNoInteractions(commandChecker);
//        verifyNoInteractions(commandHandlerDispatcher);
//        logWatcher.stop();
//    }
//
////    @Test
////    @DisplayName("Тест метода handleUpdate с невалидной Command")
////    void handleUpdate_CommandErrorTest() {
////        String expectedErrorMessage = "expectedErrorMessage";
////        when(updateChecker.checkUpdate(UPDATE)).thenReturn(null);
//////        when(commandChecker.checkCommand(any(), eq(USER))).thenReturn(expectedErrorMessage);
////
////        updateReceiver.handleUpdate(BOT, UPDATE);
////
////        assertEquals(String.format(NOT_AUTHORISED_FOR_COMMAND_LOG, USER_NAME, CHAT_ID, COMMAND.getPrefix()),
////                logWatcher.list.get(0).getFormattedMessage());
////        assertEquals(Level.WARN, logWatcher.list.get(0).getLevel());
////        verify(updateChecker).checkUpdate(UPDATE);
//////        verify(commandChecker).checkCommand(any(), eq(USER));
////        verify(responseHandler).sendResponse(BOT, expectedErrorMessage, CHAT_ID);
////        verifyNoInteractions(commandHandlerDispatcher);
////        logWatcher.stop();
////    }
//
//    @Test
//    @DisplayName("Тест метода handleUpdate если не найден подходящий CommandDispatcher")
//    void handleUpdate_NoCommandDispatcherFoundTest() {
//        when(updateChecker.checkUpdate(UPDATE)).thenReturn(null);
////        when(commandChecker.checkCommand(any(), eq(USER))).thenReturn(null);
//        when(commandHandlerDispatcher.getCommandHandler(any(), eq(USER))).thenReturn(null);
//
//        updateReceiver.handleUpdate(BOT, UPDATE);
//
//        assertEquals(String.format(COMMAND_DISPATCHER_NOT_FOUND, USER_NAME, CHAT_ID, COMMAND.getPrefix()),
//                logWatcher.list.get(0).getFormattedMessage());
//        assertEquals(Level.WARN, logWatcher.list.get(0).getLevel());
//        verify(updateChecker).checkUpdate(UPDATE);
////        verify(commandChecker).checkCommand(any(), eq(USER));
//        verify(commandHandlerDispatcher).getCommandHandler(any(), eq(USER));
//        verify(responseHandler).sendResponse(BOT, NOT_A_COMMAND_ERROR, CHAT_ID);
//        logWatcher.stop();
//    }
//
//    @Test
//    @DisplayName("Тест метода handleUpdate с валидными параметрами")
//    void handleUpdateValidTest() {
//        when(updateChecker.checkUpdate(UPDATE)).thenReturn(null);
////        when(commandChecker.checkCommand(any(), eq(USER))).thenReturn(null);
//
//        CommandHandler commandHandler = mock(CommandHandler.class);
//        when(commandHandlerDispatcher.getCommandHandler(any(), eq(USER))).thenReturn(commandHandler);
//
//        updateReceiver.handleUpdate(BOT, UPDATE);
//
//        assertEquals(String.format(PROCESSING_COMMAND, USER_NAME, CHAT_ID, COMMAND.getPrefix()),
//                logWatcher.list.get(0).getFormattedMessage());
//        assertEquals(Level.INFO, logWatcher.list.get(0).getLevel());
//        verify(updateChecker).checkUpdate(UPDATE);
////        verify(commandChecker).checkCommand(any(), eq(USER));
//        verify(commandHandlerDispatcher).getCommandHandler(any(), eq(USER));
//        verify(commandHandler).handleCommand(any());
//        verifyNoInteractions(responseHandler);
//        logWatcher.stop();
//    }
}