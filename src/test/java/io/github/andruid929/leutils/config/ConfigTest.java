package io.github.andruid929.leutils.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
            "False",
            "# This is a comment"
    );

    private final Config TEST_CONFIG = new Config(SAMPLE_CONFIG_LINES);

    @Test
    @DisplayName("Read config lines and parse different data types")
    void readConfigLines() {
        assertEquals("le-utils:3.2.0", TEST_CONFIG.getString("artifact"));
        assertEquals(3L, TEST_CONFIG.getLong("MAJOR"));
        assertEquals(0, TEST_CONFIG.getInteger("PATCH"));
        assertEquals('P', TEST_CONFIG.getCharacter("Letter P"));

        assertTrue(TEST_CONFIG.getBoolean("Christian"));

        assertArrayEquals(new String[]{"11", "21", "25"}, TEST_CONFIG.getArray("JDK versions"));
        assertArrayEquals(new int[]{11, 21, 25}, TEST_CONFIG.getIntArray("JDK versions"));

        assertEquals(1, TEST_CONFIG.getInvalidConfigs().size());
    }

    @Test
    @DisplayName("Getter methods throw or return null for missing keys")
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
    @DisplayName("Get config sizes")
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
    @DisplayName("Remove config entry")
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
    @DisplayName("Character getter throws on empty value")
    void characterThrowsOnEmptyValue() {
        Config cfg = new Config(List.of("empty:"));

        assertThrows(IllegalArgumentException.class, () -> cfg.getCharacter("empty"));
    }

    @Test
    @DisplayName("Parse config array values")
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
    @DisplayName("Throw NumberFormatException for invalid number formats")
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
    @DisplayName("Identify valid and invalid config entries")
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
    @DisplayName("Persist and read config to/from file")
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

    @Test
    @DisplayName("Handle comments and empty lines in config")
    void testCommentsAndEmptyLines() {
        List<String> lines = List.of(
                "# Distro:Fedora", //Comment
                "Version:44", //Valid
                "   # PackageManager:dnf", //Comment after trim
                "      "); //Empty line

        Config config = new Config(lines);

        assertEquals(1, config.size());
        assertTrue(config.getInvalidConfigs().isEmpty());
    }

    /**
     * An app ships defaults and the user's file only holds what they changed, so most keys are missing.
     */
    @Test
    @DisplayName("Defaults fill in keys the user's file leaves out")
    void defaultsForMissingKeys() {
        Config userEdited = new Config(List.of("theme:dark"));

        assertEquals("light", userEdited.getString("font", "light"));
        assertEquals(8080, userEdited.getInteger("port", 8080));
        assertEquals(30_000L, userEdited.getLong("timeoutMillis", 30_000L));
        assertEquals(1.5f, userEdited.getFloat("scale", 1.5f));
        assertEquals(0.75, userEdited.getDouble("ratio", 0.75));
        assertTrue(userEdited.getBoolean("autosave", true));
        assertEquals('#', userEdited.getCharacter("commentPrefix", '#'));
        assertArrayEquals(new String[]{"txt", "md"}, userEdited.getArray("extensions", new String[]{"txt", "md"}));
        assertArrayEquals(new int[]{80, 443}, userEdited.getIntArray("ports", new int[]{80, 443}));
    }

    @Test
    @DisplayName("A value the user set wins over the default")
    void userValueWinsOverDefault() {
        Config userEdited = new Config(List.of(
                "port:9090",
                "autosave:false",
                "extensions:[java, kt]",
                "ports:[8443]"
        ));

        assertEquals(9090, userEdited.getInteger("port", 8080));
        assertFalse(userEdited.getBoolean("autosave", true));
        assertArrayEquals(new String[]{"java", "kt"}, userEdited.getArray("extensions", new String[]{"txt"}));
        assertArrayEquals(new int[]{8443}, userEdited.getIntArray("ports", new int[]{80}));
    }

    /**
     * Only {@code true} counts as true, so a user writing {@code yes} turns the option off, even when its default is
     * on.
     */
    @Test
    @DisplayName("A boolean written as yes is false, not the default")
    void booleanWrittenAsYes() {
        Config userEdited = new Config(List.of("autosave:yes"));

        assertFalse(userEdited.getBoolean("autosave", true));
    }

    /**
     * Falling back to the default here would hide the user's mistake: the app would run on 8080 while the file says
     * otherwise.
     */
    @Test
    @DisplayName("A typo in a value still fails instead of using the default")
    void typoStillFails() {
        Config userEdited = new Config(List.of(
                "port:8O8O",
                "timeoutMillis:30s",
                "ratio:0,75",
                "ports:[80, 4430x]"
        ));

        assertThrows(NumberFormatException.class, () -> userEdited.getInteger("port", 8080));
        assertThrows(NumberFormatException.class, () -> userEdited.getLong("timeoutMillis", 30_000L));
        assertThrows(NumberFormatException.class, () -> userEdited.getDouble("ratio", 0.75));
        assertThrows(NumberFormatException.class, () -> userEdited.getIntArray("ports", new int[]{80}));
    }

    /**
     * A user clearing a value ({@code key:}) has still set the key.
     */
    @Test
    @DisplayName("A key with its value cleared is present, not missing")
    void clearedValueIsPresent() {
        Config userEdited = new Config(List.of(
                "nickname:",
                "commentPrefix:",
                "extensions:[]",
                "ports:"
        ));

        assertEquals("", userEdited.getString("nickname", "guest"));
        assertThrows(IllegalArgumentException.class, () -> userEdited.getCharacter("commentPrefix", '#'));
        assertArrayEquals(new String[]{}, userEdited.getArray("extensions", new String[]{"txt"}));
        assertArrayEquals(new int[]{}, userEdited.getIntArray("ports", new int[]{80}));
    }

    /**
     * Keys aren't normalised, so a user who writes {@code PORT} hasn't set {@code port}.
     */
    @Test
    @DisplayName("A key in a different case doesn't match")
    void keyInDifferentCase() {
        Config userEdited = new Config(List.of("PORT:9090"));

        assertEquals(8080, userEdited.getInteger("port", 8080));
    }

    @Test
    @DisplayName("Global config falls back to the default only for missing keys")
    void globalDefault() {
        Config.clear();

        try {
            Config.add("theme", "dark");
            Config.add("nickname", "");

            assertEquals("dark", Config.get("theme", "light"));
            assertEquals("light", Config.get("font", "light"));
            assertEquals("", Config.get("nickname", "guest"));
        } finally {
            Config.clear();
        }
    }

    /**
     * Before 5.1.0 the String overload let the map throw {@link NullPointerException}, unlike the Number overload.
     */
    @Test
    @DisplayName("Adding a null value is rejected like the other overloads")
    void addNullRejected() {
        Config.clear();

        try {
            assertThrows(IllegalArgumentException.class, () -> Config.add("theme", (String) null));
            assertThrows(IllegalArgumentException.class, () -> Config.add("port", (Number) null));

            assertEquals(0, Config.numberOfConfigs());
        } finally {
            Config.clear();
        }
    }

    /**
     * Entries are added one at a time in the map's order, so a {@code null} part-way through leaves the ones before it
     * in place.
     */
    @Test
    @DisplayName("Adding a map with a null value stops at that entry")
    void addFromMapStopsAtNull() {
        Config.clear();

        Map<String, String> settings = new LinkedHashMap<>();
        settings.put("theme", "dark");
        settings.put("font", null);
        settings.put("port", "9090");

        try {
            assertThrows(IllegalArgumentException.class, () -> Config.addFromMap(settings));

            assertEquals("dark", Config.get("theme"));
            assertNull(Config.get("font"));
            assertNull(Config.get("port"));
        } finally {
            Config.clear();
        }
    }
}
