package nz.archana.loandocs.service;

import nz.archana.loandocs.exception.MissingPlaceholderException;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PlaceholderFillerTest {

    @Test
    void fillsAllPlaceholders() {
        String result = PlaceholderFiller.fill(
                "Dear {{name}}, your loan of {{amount}} is approved.",
                Map.of("name", "Jane", "amount", "$250,000"));
        assertEquals("Dear Jane, your loan of $250,000 is approved.", result);
    }

    @Test
    void ignoresExtraValues() {
        String result = PlaceholderFiller.fill("Hi {{name}}", Map.of("name", "Jane", "unused", "x"));
        assertEquals("Hi Jane", result);
    }

    @Test
    void throwsWhenValueMissing() {
        MissingPlaceholderException ex = assertThrows(MissingPlaceholderException.class,
                () -> PlaceholderFiller.fill("Hi {{name}}, rate {{rate}}", Map.of("name", "Jane")));
        assertTrue(ex.getMessage().contains("rate"));
    }
}
