package io.github.andruid929.leutils.wora;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * Utility class for resolving paths specific to an operating system.
 * This supports Windows, Mac and Linux. For example, the {@link #USER_HOME} field
 * will return {@code C:\Users\Username} on Windows and {@code /home/username/} on Linux.
 *
 * @author Andrew Jones
 * @since 4.2.0
 */

public class PathFinder {

    /**
     * This field represents the OS specific path to the user's home directory
     *
     * @since 4.2.0
     */

    public static final String USER_HOME = System.getProperty("user.home");

    /**
     * This field represents the OS specific path to the user's documents directory
     *
     * @since 4.2.0
     */

    public static final String DOCUMENTS_FOLDER = getDocumentsFolder().toString();

    /**
     * This field represents the OS specific path to the user's app data directory
     *
     * @since 4.2.0
     */

    public static final String APPDATA_FOLDER = getAppDataFolder().toString();

    private PathFinder() {
    }

    /**
     * Get the path to the documents folder
     *
     * @return the Path to the OS documents folder
     * @since 4.2.0
     */

    @Contract(pure = true)
    public static @NotNull Path getDocumentsFolder() {
        return Path.of(USER_HOME, "Documents");
    }

    /**
     * Get the path to the appdata folder
     *
     * @return the Path to the OS app data folder
     * @since 4.2.0
     */

    public static @NotNull Path getAppDataFolder() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            String appDataPath = System.getenv("APPDATA");

            return Path.of(appDataPath);

        } else if (os.contains("mac")) {
            return Path.of(USER_HOME, "Library", "Application Support");

        } else {
            return Path.of(USER_HOME, ".config");
        }
    }

    /**
     * Get a single folder extending from user home. This method is the simpler version of
     * {@link #createPathFromHomeRoot(String...)}.
     *
     * @param directory the folder to get within the user home directory.
     * @return a single folder extending from the user home directory
     * @since 4.2.0
     */

    @Contract(pure = true)
    public static @NotNull Path getUserFolder(@NotNull String directory) {
        return Path.of(USER_HOME, directory);
    }

    /**
     * This method creates a path extending from {@link #USER_HOME}.
     * Usage example: {@code Path.createFromHomeRoot(".m2", "repository")}
     * will return {@code /home/username/.m2/repository} on Linux and
     * {@code C:\Users\Username\.m2\repository}.
     *
     * @param directories the folders to be added to the path in order
     * @return a path of the specified folders extending from the user's {@link #USER_HOME home directory}
     * @since 4.2.0
     */

    @Contract(pure = true)
    public static @NotNull Path createPathFromHomeRoot(String @NotNull ... directories) {
        return Path.of(USER_HOME, directories);
    }
}
