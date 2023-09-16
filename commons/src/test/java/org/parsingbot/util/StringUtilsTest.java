package org.parsingbot.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertFalse;

class StringUtilsTest {

    @ParameterizedTest
    @NullAndEmptySource
    void checkStringTest(String command) {
        assertFalse(StringUtils.checkString(command));
    }
}