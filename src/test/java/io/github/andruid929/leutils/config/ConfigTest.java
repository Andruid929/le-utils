package io.github.andruid929.leutils.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

class ConfigTest {

    private final List<String> SAMPLE_CONFIG_LINES = List.of(
            "artifact:le-utils:3.2.0",
            "MAJOR:3",
            "JDK versions:[11, 21, 25]",
            "Letter P:P",
            "Christian:true",
            "MINOR:2.0",
            "PATCH:0",
            "False"
    );

    private final Config TEST_CONFIG = new Config(SAMPLE_CONFIG_LINES);

    /*
     * Reads config lines, collects
     * */
    @Test
    void readConfigLines() {
        assertEquals("le-utils:3.2.0", TEST_CONFIG.getString("artifact"));
        assertEquals(3L, TEST_CONFIG.getLong("MAJOR"));
        assertEquals(0, TEST_CONFIG.getInteger("PATCH"));
        assertEquals('P', TEST_CONFIG.getCharacter("Letter P"));

        assertTrue(TEST_CONFIG.getBoolean("Christian"));

        assertArrayEquals(new String[]{"11", "21", "25"}, TEST_CONFIG.getArray("JDK versions"));
        assertArrayEquals(new int[]{11, 21, 25}, TEST_CONFIG.getIntArray("JDK versions"));

        // The provided file contains only valid lines; the invalid configs list should be empty
        assertEquals(1, TEST_CONFIG.getInvalidConfigs().size());
    }

    @Test
    void gettersThrowOnMissingKeys() {
        assertNull(TEST_CONFIG.getString("missing"));

        assertThrows(NoSuchElementException.class, () -> TEST_CONFIG.getInteger("missing"));
        assertThrows(NoSuchElementException.class, () -> TEST_CONFIG.getLong("missing"));
        assertThrows(NoSuchElementException.class, () -> TEST_CONFIG.getDouble("missing"));
        assertThrows(NoSuchElementException.class, () -> TEST_CONFIG.getFloat("missing"));
        assertThrows(NoSuchElementException.class, () -> TEST_CONFIG.getBoolean("missing"));
        assertThrows(NoSuchElementException.class, () -> TEST_CONFIG.getCharacter("missing"));
        assertThrows(NoSuchElementException.class, () -> TEST_CONFIG.getArray("missing"));
        assertThrows(NoSuchElementException.class, () -> TEST_CONFIG.getIntArray("missing"));
    }

    @Test
    void sizes() {
        Config.add("1", 1);
        Config.add("2", 2);
        Config.add("3", 3);
        Config.add("4", 4);
        Config.add("5", 5);

        assertEquals(5, Config.numberOfConfigs());
        assertEquals(7, TEST_CONFIG.size());
    }

    @Test
    void remove() {
        Config.add("1", 1);
        Config.add("2", 2);
        Config.add("3", 3);
        Config.add("4", 4);
        Config.add("5", 5);

        assertFalse(Config.remove("6"));
        assertTrue(Config.remove("5"));
    }

    @Test
    void characterThrowsOnEmptyValue() {
        Config cfg = new Config(List.of("empty:"));

        assertThrows(IllegalArgumentException.class, () -> cfg.getCharacter("empty"));
    }

    @Test
    void testConfigArrayParsing() {
        Config cfg = new Config(List.of(
                "A:[a, b, c]",
                "B:[ 1 ,  2,3 ]",
                "C:[]",
                "D:[   ]",
                "E:[,]" // results in two empty tokens after trimming brackets -> split on comma -> {"", ""}
        ));

        assertArrayEquals(new String[]{"a", "b", "c"}, cfg.getArray("A"));
        assertArrayEquals(new String[]{"1", "2", "3"}, cfg.getArray("B"));
        assertArrayEquals(new String[]{}, cfg.getArray("C"));
        assertArrayEquals(new String[]{}, cfg.getArray("D"));

        // int array from numeric csv
        Config numeric = new Config(List.of("nums:[10,20,30]"));

        assertArrayEquals(new int[]{10, 20, 30}, numeric.getIntArray("nums"));
    }

    @Test
    void throwsNumberFormatException() {
        Config cfg = new Config(List.of(
                "i:notAnInt",
                "l:9nine",
                "f:x.y",
                "d:..1",
                "ints:[1, 2, x]"
        ));

        assertThrows(NumberFormatException.class, () -> cfg.getInteger("i"));
        assertThrows(NumberFormatException.class, () -> cfg.getLong("l"));
        assertThrows(NumberFormatException.class, () -> cfg.getFloat("f"));
        assertThrows(NumberFormatException.class, () -> cfg.getDouble("d"));
        assertThrows(NumberFormatException.class, () -> cfg.getIntArray("ints"));
    }

    @Test
    void testValidAndInvalidConfigs() {
        Config cfg = new Config(List.of(
                "valid:1",
                " also invalid ",   // no colon
                "still:ok",
                "invalid-again"     // no colon
        ));

        assertEquals("1", cfg.getString("valid"));
        assertEquals("ok", cfg.getString("still"));

        List<String> invalids = cfg.getInvalidConfigs();

        assertEquals(2, invalids.size());

        assertTrue(invalids.contains("also invalid"));
        assertTrue(invalids.contains("invalid-again"));
    }

    @Test
    void testConfigPersistence(@TempDir Path tempDir) throws IOException {
        // Prepare global map
        Config.clear();
        Config.add("z", "26");
        Config.add("a", "1");
        Config.add("m", "13");

        //Create a temporary file
        Path tmp = Files.createTempFile(tempDir, "Persisted", ".cfg");

        Config.persistConfig(tmp, true);

        // Read back with readConfig -> should parse and be accessible
        Config read = Config.readConfig(tmp);

        assertEquals("1", read.getString("a"));
        assertEquals("13", read.getString("m"));
        assertEquals("26", read.getString("z"));

        // Clean up static map
        Config.clear();
    }
}