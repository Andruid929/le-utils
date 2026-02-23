package io.github.andruid929.leutils.config;

import io.github.andruid929.leutils.strings.StringUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * A utility class for managing configurations as key-value pairs. Configurations are represented
 * as {@code String} keys and {@code String} values and can be retrieved as various primitive types
 * and arrays when the stored values are parseable into those types.
 *
 * <p>Two usage modes are supported:
 * <ul>
 *   <li>Global, mutable storage via static methods: {@link #add(String, String)}, {@link #remove(String)},
 *       {@link #clear()}, and {@link #persistConfig(Path, boolean)}.</li>
 *   <li>Immutable, instance-level view built from a list of {@code "key:value"} lines via the constructor,
 *       which powers all instance getters such as {@link #getString(String)} and {@link #getInteger(String)}.</li>
 * </ul>
 *
 * <p>Instance objects of {@link Config} are immutable and thread-safe for reads. The global storage
 * uses a {@link ConcurrentHashMap} for thread-safety of individual operations; snapshotting is used
 * when persisting to ensure deterministic output.</p>
 *
 * <p>Parsing behavior and errors:
 * <ul>
 *   <li>Missing keys in getters that parse values throw {@link NoSuchElementException} with the key name.</li>
 *   <li>Malformed numeric values throw {@link NumberFormatException} as per the underlying parser.</li>
 *   <li>{@link #getCharacter(String)} throws {@link IllegalArgumentException} if the value is empty.</li>
 *   <li>{@link #getArray(String)} trims whitespace, accepts bracketed CSV forms like {@code [a, b, c]},
 *       and returns an empty array for blank or {@code []} values.</li>
 *   <li>Lines without a colon in the constructor input are collected in {@link #getInvalidConfigs()}.</li>
 * </ul>
 *
 * <p>Persistence format:
 * each entry is written as {@code key:value} per line, with keys sorted lexicographically for stable diffs.</p>
 *
 * @author Andrew
 * @since 3.2.0
 */

public final class Config {

    /**
     * Global, mutable configuration map used by static methods.
     * Thread-safe for individual operations.
     */

    static final Map<String, String> configs = new ConcurrentHashMap<>();

    /**
     * Immutable key-value map backing instance getters.
     */

    private final Map<String, String> keyValueConfigs;

    /**
     * Immutable list of lines from the constructor input that were not valid {@code key:value} pairs.
     */

    private final List<String> invalidConfigs;

    /**
     * Builds an immutable configuration snapshot from a list of lines. Each line is expected to be in the form
     * {@code key:value}. Lines without a colon are treated as invalid and are exposed via {@link #getInvalidConfigs()}.
     * Whitespace around the entire line is trimmed prior to parsing; keys and values are stored as-is after the split.
     *
     * @param configsList non-null list of raw lines, typically read from a configuration file
     */

    @Contract(pure = true)
    public Config(@NotNull List<String> configsList) {
        Map<String, String> configMap = new HashMap<>();

        java.util.List<String> invalids = new ArrayList<>();

        for (String config : configsList) {
            String[] keyAndValue = config.trim().split(":", 2);

            if (keyAndValue.length != 2) {
                invalids.add(config.trim());

                continue;
            }

            configMap.put(keyAndValue[0], keyAndValue[1]);
        }

        keyValueConfigs = Collections.unmodifiableMap(configMap);

        invalidConfigs = Collections.unmodifiableList(invalids);
    }

    /**
     * Returns an immutable list of input lines that were not valid {@code key:value} pairs.
     * Lines are trimmed as they appeared to the parser.
     *
     * @return unmodifiable list of invalid config lines; possibly empty
     */

    public List<String> getInvalidConfigs() {
        return invalidConfigs;
    }

    /**
     * Retrieves the raw string value for a key, or {@code null} if absent.
     *
     * @param key configuration key
     * @return the stored string, or {@code null} if not found
     */

    public String getString(String key) {
        return keyValueConfigs.get(key);
    }

    /**
     * Parses the value for {@code key} as an {@code int}.
     *
     * @param key configuration key
     * @return parsed int value
     * @throws NoSuchElementException if the key is missing
     * @throws NumberFormatException  if the value is not a valid integer
     */

    public int getInteger(String key) {
        String value = getString(key);

        if (value == null) {
            throw new NoSuchElementException("Missing config for key: " + key);
        }

        return Integer.parseInt(value);
    }

    /**
     * Parses the value for {@code key} as a {@code long}.
     *
     * @param key configuration key
     * @return parsed long value
     * @throws NoSuchElementException if the key is missing
     * @throws NumberFormatException  if the value is not a valid long
     */

    public long getLong(String key) {
        String value = getString(key);

        if (value == null) {
            throw new NoSuchElementException("Missing config for key: " + key);
        }

        return Long.parseLong(value);
    }

    /**
     * Parses the value for {@code key} as a {@code boolean} using {@link Boolean#parseBoolean(String)}.
     * Returns {@code true} only if the value equals (ignoring case) {@code "true"}.
     *
     * @param key configuration key
     * @return parsed boolean value
     * @throws NoSuchElementException if the key is missing
     */

    public boolean getBoolean(String key) {
        String value = getString(key);
        if (value == null) {
            throw new NoSuchElementException("Missing config for key: " + key);
        }
        return Boolean.parseBoolean(value);
    }

    /**
     * Parses the value for {@code key} as a {@code float}.
     *
     * @param key configuration key
     * @return parsed float value
     * @throws NoSuchElementException if the key is missing
     * @throws NumberFormatException  if the value is not a valid float
     */

    public float getFloat(String key) {
        String value = getString(key);
        if (value == null) {
            throw new NoSuchElementException("Missing config for key: " + key);
        }
        return Float.parseFloat(value);
    }

    /**
     * Parses the value for {@code key} as a {@code double}.
     *
     * @param key configuration key
     * @return parsed double value
     * @throws NoSuchElementException if the key is missing
     * @throws NumberFormatException  if the value is not a valid double
     */

    public double getDouble(String key) {
        String value = getString(key);

        if (value == null) {
            throw new NoSuchElementException("Missing config for key: " + key);
        }

        return Double.parseDouble(value);
    }

    /**
     * Returns the first character of the string value for {@code key}.
     *
     * @param key configuration key
     * @return first character of the stored string
     * @throws NoSuchElementException   if the key is missing
     * @throws IllegalArgumentException if the stored string is empty
     */

    public char getCharacter(String key) {
        String s = getString(key);

        if (s == null) {
            throw new NoSuchElementException("Missing config for key: " + key);
        }

        if (s.isEmpty()) {
            throw new IllegalArgumentException("Empty value for key: " + key);
        }

        return s.charAt(0);
    }

    /**
     * Parses the value for {@code key} as a string array. Expected formats include a bracketed,
     * comma-separated list such as {@code [a, b, c]} or {@code [a,b,c]}. All whitespace is removed
     * before parsing. A blank or {@code []} value yields an empty array.
     *
     * @param key configuration key
     * @return array of string elements; never {@code null}
     * @throws NoSuchElementException if the key is missing
     */

    public String @NotNull [] getArray(String key) {
        String raw = getString(key);

        if (raw == null) {
            throw new NoSuchElementException("Missing config for key: " + key);
        }

        String value = raw.replaceAll("\\s+", "");

        if (value.isBlank()) {
            return new String[]{};
        }

        String csvString = StringUtil.trimCharacters(value, '[', ']');

        if (csvString.isBlank()) {
            return new String[]{};
        }

        return csvString.split(",");
    }

    /**
     * Parses the value for {@code key} as an array of {@code int}. The value is first interpreted
     * via {@link #getArray(String)} and each element is parsed with {@link Integer#parseInt(String)}.
     *
     * @param key configuration key
     * @return array of ints; never {@code null}
     * @throws NoSuchElementException if the key is missing
     * @throws NumberFormatException  if any element is not a valid integer
     */

    public int @NotNull [] getIntArray(String key) {
        String[] strings = getArray(key);

        int arrayLength = strings.length;

        if (arrayLength == 0) {
            return new int[]{};
        }

        int[] ints = new int[arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            int parsedInt = Integer.parseInt(strings[i]);

            ints[i] = parsedInt;
        }

        return ints;
    }

    /**
     * Adds or replaces a global configuration entry with a string value.
     *
     * @param key   non-null configuration key
     * @param value value to store; may be {@code null}
     */

    public static void add(@NotNull String key, String value) {
        configs.put(key, value);
    }

    /**
     * Adds or replaces a global configuration entry with a numeric value.
     *
     * @param key   non-null configuration key
     * @param value non-null numeric value
     * @throws IllegalArgumentException if {@code value} is {@code null}
     */

    public static void add(@NotNull String key, Number value) {
        if (value == null) {
            throw new IllegalArgumentException("Null values not accepted");
        }

        add(key, String.valueOf(value));
    }

    /**
     * Adds or replaces a global configuration entry with a boolean value.
     *
     * @param key   non-null configuration key
     * @param value boolean value
     */

    public static void add(@NotNull String key, boolean value) {
        add(key, String.valueOf(value));
    }

    /**
     * Adds or replaces a global configuration entry with an array value. The array is serialized
     * using {@link Arrays#toString(Object[])} (e.g., {@code [a, b, c]}).
     *
     * @param key   non-null configuration key
     * @param value array value; treated as {@code []} if empty
     */

    public static void add(@NotNull String key, Object[] value) {
        String arrayString = Arrays.toString(value);

        add(key, arrayString);
    }

    /**
     * Adds or replaces a global configuration entry with a single character value.
     *
     * @param key   non-null configuration key
     * @param value character value
     */

    public static void add(@NotNull String key, char value) {
        add(key, String.valueOf(value));
    }

    /**
     * Removes a global configuration entry if present.
     *
     * @param key configuration key
     * @return {@code true} if an entry was removed; {@code false} otherwise
     */

    public static boolean remove(String key) {
        return configs.remove(key) != null;
    }

    /**
     * Clears all configuration entries from the global configurations list.
     */
    public static void clear() {
        configs.clear();
    }

    /**
     * Persists the current global configuration map to a file. Entries are written as {@code key:value}
     * per line with keys sorted for deterministic output. A snapshot of the map is taken to avoid
     * concurrent modification and to guarantee stable ordering.
     *
     * @param savePath             path to the destination file
     * @param createMissingFolders if {@code true}, missing parent directories are created
     * @throws IOException if an I/O error occurs while writing
     */

    public static void persistConfig(@NotNull Path savePath, boolean createMissingFolders) throws IOException {
        if (configs.isEmpty()) {
            return;
        }

        if (createMissingFolders) {
            Path folders = savePath.getParent();

            if (folders != null) {
                Files.createDirectories(folders);
            }
        }

        Map<String, String> snapshot = new HashMap<>(configs);

        String configsString = snapshot.keySet().stream()
                .sorted()
                .map(key -> {
                    String value = snapshot.get(key);

                    return key.concat(":").concat(value);
                })
                .collect(Collectors.joining(System.lineSeparator()));

        try (OutputStream outStream = Files.newOutputStream(savePath, CREATE, TRUNCATE_EXISTING);
             BufferedOutputStream stream = new BufferedOutputStream(outStream)) {

            stream.write(configsString.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Reads configuration lines from a file and returns an immutable {@link Config} instance
     * built from those lines. Each file line is treated as a raw entry and parsed as
     * {@code key:value} during construction. Missing files yield an empty configuration.
     *
     * @param configPath path to the configuration file
     * @return an immutable {@link Config} built from the file contents
     * @throws IOException if an I/O error occurs while reading
     */

    public static @NotNull Config readConfig(Path configPath) throws IOException {
        if (Files.notExists(configPath)) {
            return new Config(new ArrayList<>());
        }

        try (BufferedReader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {

            List<String> configLines = new ArrayList<>();

            String line;

            while ((line = reader.readLine()) != null) {
                configLines.add(line);
            }

            return new Config(configLines);
        }
    }
}
