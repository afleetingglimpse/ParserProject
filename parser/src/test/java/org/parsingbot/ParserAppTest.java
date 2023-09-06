package org.parsingbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ParserAppTest.class)
class ParserAppTest {

    @Test
    void parserContextTest(ApplicationContext context) {
        assertNotNull(context);
    }
}