package org.parsingbot.commons.entity;

import org.junit.jupiter.api.Test;
import org.parsingbot.commons.entity.Command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandTest {

    @Test
    void getPrefixOfValidCommandTest() {
        String validCommand = "/valid command";

        Command command = new Command(validCommand);

        String expectedPrefix = "/valid";
        String actualPrefix = command.getPrefix();

        assertEquals(expectedPrefix, actualPrefix);
    }

    @Test
    void getPrefixOfInvalidCommandTest() {
        String invalidCommand = "invalid command";

        Command command = new Command(invalidCommand);

        String actualPrefix = command.getPrefix();
        assertNull(actualPrefix);
    }
}