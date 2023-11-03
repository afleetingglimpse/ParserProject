package org.parsingbot.commons.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import lombok.experimental.UtilityClass;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@UtilityClass
public class TestHelper {

    public String randomFromUuid() {
        return UUID.randomUUID().toString();
    }

    public ListAppender<ILoggingEvent> getLogger(Class<?> clazz) {
        ListAppender<ILoggingEvent> logWatcher = new ListAppender<>();
        ((Logger) LoggerFactory.getLogger(clazz)).addAppender(logWatcher);
        return logWatcher;
    }
}
