package org.parsingbot.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandDtoTest {

    @Test
    void getPrefixOfValidCommandTest() {
        String validCommand = "/valid command";

        CommandDto commandDto = new CommandDto(validCommand);

        String expectedPrefix = "/valid";
        String actualPrefix = commandDto.getPrefix();

        assertEquals(expectedPrefix, actualPrefix);
    }

    @Test
    void getPrefixOfInvalidCommandTest() {
        String invalidCommand = "invalid command";

        CommandDto commandDto = new CommandDto(invalidCommand);

        String actualPrefix = commandDto.getPrefix();
        assertNull(actualPrefix);
    }
}