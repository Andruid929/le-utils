package io.github.andruid929.leutils.config;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    private final Path PATH_TO_CONFIG_FILE = Path.of("src", "test", "resources", "Config.tst");

    @Test
    void readConfigParsesValidLinesAndCollectsInvalidOnes() throws IOException {
        Config cfg = Config.readConfig(PATH_TO_CONFIG_FILE);

        assertEquals("le-utils:3.2.0", cfg.getString("artifact"));
        assertEquals(3L, cfg.getLong("MAJOR"));
        assertEquals(0, cfg.getInteger("PATCH"));
        assertEquals('P', cfg.getCharacter("Letter P"));

        assertTrue(cfg.getBoolean("Christian"));

        assertArrayEquals(new String[]{"11", "21", "25"}, cfg.getArray("JDK versions"));
        assertArrayEquals(new int[]{11, 21, 25}, cfg.getIntArray("JDK versions"));

        // The provided file contains only valid lines; the invalid configs list should be empty
        assertTrue(cfg.getInvalidConfigs().isEmpty());
    }

    @Test
    void gettersThrowOnMissingKeys() throws IOException {
        Config cfg = Config.readConfig(PATH_TO_CONFIG_FILE);

        assertNull(cfg.getString("missing"));

        assertThrows(NoSuchElementException.class, () -> cfg.getInteger("missing"));
        assertThrows(NoSuchElementException.class, () -> cfg.getLong("missing"));
        assertThrows(NoSuchElementException.class, () -> cfg.getDouble("missing"));
        assertThrows(NoSuchElementException.class, () -> cfg.getFloat("missing"));
        assertThrows(NoSuchElementException.class, () -> cfg.getBoolean("missing"));
        assertThrows(NoSuchElementException.class, () -> cfg.getCharacter("missing"));
        assertThrows(NoSuchElementException.class, () -> cfg.getArray("missing"));
        assertThrows(NoSuchElementException.class, () -> cfg.getIntArray("missing"));
    }

    @Test
    void characterThrowsOnEmptyValue() {
        Config cfg = new Config(List.of("empty:"));

        assertThrows(IllegalArgumentException.class, () -> cfg.getCharacter("empty"));
    }

    @Test
    void arraysParseBracketedCsvAndHandleWhitespaceAndEmpty() {
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
    void numberParsingThrowsNumberFormatOnMalformed() {
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
    void constructorCollectsInvalidLines() {
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
    void persistConfigSortsKeysAndWritesSnapshot() throws IOException {
        // Prepare global map
        Config.clear();
        Config.add("z", "26");
        Config.add("a", "1");
        Config.add("m", "13");

        Path tmp = Path.of("target", "test-output", "persisted.cfg");
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