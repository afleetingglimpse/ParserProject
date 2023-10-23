package org.parsingbot.core.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.parsingbot.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;

class StringUtilsTest {

    @ParameterizedTest
    @NullAndEmptySource
    void checkStringTest(String command) {
        assertFalse(StringUtils.checkString(command));
    }
}