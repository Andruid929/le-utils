package io.github.andruid929.leutils.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test class for StringFiller.
 * Specifically tests the functionality of the getNumberOfModifications method.
 */
class StringFillerTest {

    @Test
    void testEmptyMapModifications() {
        String original = "Hello, $name$!";

        StringFiller stringFiller = new StringFiller(original, Collections.emptyMap());

        int modifications = stringFiller.getNumberOfModifications();

        assertEquals("Hello, $name$!", stringFiller.get());
        assertEquals(0, modifications, "No modifications should occur when key-value map is empty.");
    }

    @Test
    void testSingleModification() {
        String original = "Hello, $name$!";

        StringFiller stringFiller = StringFiller.onceOff(original, "name", "John");

        int modifications = stringFiller.getNumberOfModifications();

        assertEquals("Hello, John!", stringFiller.get());
        assertEquals(1, modifications, "Should register one modification for a single placeholder replacement.");
    }

    @Test
    void testMultipleModifications() {
        String original = "Hello, $name$. Welcome to $place$.";

        Map<String, String> keyValueMap = Map.of(
                "name", "John",
                "place", "Earth"
        );

        StringFiller stringFiller = new StringFiller(original, keyValueMap);

        int modifications = stringFiller.getNumberOfModifications();

        assertEquals("Hello, John. Welcome to Earth.", stringFiller.get());
        assertEquals(2, modifications, "Should register two modifications for two placeholder replacements.");
    }

    @Test
    void testNonMatchingKeys() {
        String original = "Hello, $name$!";

        Map<String, String> keyValueMap = Map.of("username", "John");

        StringFiller stringFiller = new StringFiller(original, keyValueMap);

        assertEquals("Hello, $name$!", stringFiller.get(), "No modifications should occur if none of the placeholders match keys.");
        assertEquals(0, stringFiller.getNumberOfModifications());
    }

    @Test
    void testReplaceAll() {
        String original = "Hello, $name$! How are you, $name$?";

        Map<String, String> keyValueMap = Map.of("name", "John");
        StringFiller stringFiller = new StringFiller(original, keyValueMap);

        int modifications = stringFiller.getNumberOfModifications();

        assertEquals("Hello, John! How are you, John?", stringFiller.get());
        assertEquals(1, modifications, "Should count only one modification even if the placeholder is replaced in multiple locations.");
    }

    @Test
    void testEmptyString() {
        String original = "";
        Map<String, String> keyValueMap = Map.of("name", "John");
        StringFiller stringFiller = new StringFiller(original, keyValueMap);

        int modifications = stringFiller.getNumberOfModifications();

        assertEquals(0, modifications, "No modifications should occur if the original string is empty.");
    }

    @Test
    void testSingleDollarSign() {
        String original = "Hello, $name";

        String filled = StringFiller.replace(original, "name", "John");

        assertEquals(original, filled);
    }

    @Test
    void testFullPath() {
        String original = "C:\\Users\\$USERNAME$\\Desktop\\$custom_name$\\Eater.$FILE_EXTENSION$";

        Map<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("USERNAME", System.getenv("USERNAME"));
        keyValueMap.put("custom_name", "Le Druid");
        keyValueMap.put("FILE_EXTENSION", "txt");

        StringFiller filler = new StringFiller(original, keyValueMap);

        assertEquals("C:\\Users\\Andrew\\Desktop\\Le Druid\\Eater.txt", filler.get());
        assertEquals(3, filler.getNumberOfModifications());
    }
}
	