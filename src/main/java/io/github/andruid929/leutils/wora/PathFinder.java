package io.github.andruid929.leutils.wora;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * Utility class for resolving paths specific to an operating system.
 * This supports Windows, Mac and Linux. For example, the {@link #USER_HOME} field
 * will return {@code C:\Users\<Username>} on Windows and {@code /home/<username>/} on Linux.
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
     * Get the path to the appdata folder. On Windows this is the {@code APPDATA} folder, or
     * {@code AppData\Roaming} in the user's home directory if that variable is not set. On macOS it is
     * {@code Library/Application Support} and on any other operating system {@code .config}, both in the
     * user's home directory
     *
     * @return the Path to the operating system's app data folder
     * @since 4.2.0
     */

    public static @NotNull Path getAppDataFolder() {
        if (OperatingSystem.isWindows()) {
            String appdataDir = System.getenv("APPDATA");

            if (appdataDir == null || appdataDir.isBlank()) {
                return Path.of(USER_HOME, "AppData", "Roaming");

            } else {
                return Path.of(appdataDir);
            }

        } else if (OperatingSystem.isMacOS()) {
            return Path.of(USER_HOME, "Library", "Application Support");

        } else {
            return Path.of(USER_HOME, ".config");
        }
    }

    /**
     * Get the path to the local (non-roaming) app data folder. On Windows this is the {@code LOCALAPPDATA}
     * folder, or {@code AppData\Local} in the user's home directory if that variable is not set. Other
     * operating systems have no separate local folder, so this is the same as {@link #getAppDataFolder()}
     *
     * @return the Path to the operating system's local app data folder
     * @since 5.1.0
     */

    public static @NotNull Path getLocalAppDataFolder() {
        if (OperatingSystem.isWindows()) {
            String localAppDataDir = System.getenv("LOCALAPPDATA");

            if (localAppDataDir == null || localAppDataDir.isBlank()) {
                return Path.of(USER_HOME, "AppData", "Local");

            } else {
                return Path.of(localAppDataDir);
            }
        }

        return getAppDataFolder();
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
     * Usage example: {@code PathFinder.createFromHomeRoot(".m2", "repository")}
     * will return {@code /home/<username>/.m2/repository} on Linux and
     * {@code C:\Users\<Username>\.m2\repository}.
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
