package io.github.andruid929.leutils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Information about the le-utils library itself.
 *
 * @author Andrew Jones
 * @since 5.1.0
 */

public final class LeUtils {

    /**
     * The version reported when the version file is missing or was never filled in by Maven,
     * such as when running from an IDE that skips resource filtering.
     */

    private static final String FALLBACK_VERSION = "dev";

    /**
     * The library version, read once from {@code version.properties} in this package.
     */

    private static final String VERSION = readVersion();

    private LeUtils() {
    }

    /**
     * Get the version of le-utils on the classpath. The version comes from the pom, which Maven writes into
     * {@code version.properties} when it builds the jar, so it stays correct when le-utils is shaded into
     * another jar.
     *
     * @return the library version, such as {@code 5.1.0}, or {@code dev} if it couldn't be read
     * @since 5.1.0
     */

    public static @NotNull String getVersion() {
        return VERSION;
    }

    private static @NotNull String readVersion() {
        try (InputStream stream = LeUtils.class.getResourceAsStream("version.properties")) {

            if (stream == null) {
                return FALLBACK_VERSION;
            }

            Properties properties = new Properties();

            properties.load(stream);

            String version = properties.getProperty("version");

            if (version == null || version.isBlank() || version.startsWith("${")) {
                return FALLBACK_VERSION;
            }

            return version.trim();

        } catch (IOException e) {
            return FALLBACK_VERSION;
        }
    }
}
