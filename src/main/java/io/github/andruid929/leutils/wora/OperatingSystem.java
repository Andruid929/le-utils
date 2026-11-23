package io.github.andruid929.leutils.wora;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * Enum with the popular operating systems Linux, macOS and Windows, plus the Unix-like Solaris, FreeBSD and AIX.
 *
 * @author Andrew Jones
 * @since 5.1.0
 */
public enum OperatingSystem {

    /**
     * Microsoft Windows
     */
    WINDOWS("win"),

    /**
     * Apple's macOS
     */
    MAC_OS("mac"),

    /**
     * Linus Torvalds' Linux
     */
    LINUX("linux"),

    /**
     * Oracle's Solaris, which Java reports as {@code SunOS}
     */
    SUN_OS("solaris"),

    /**
     * The FreeBSD Project's FreeBSD
     */
    FREE_BSD("freebsd"),

    /**
     * IBM's AIX
     */
    AIX("aix");

    /**
     * The lowercase short code for the operating system
     */
    private final String code;

    OperatingSystem(String code) {
        this.code = code;
    }

    /**
     * Get the operating system an OS name refers to, in the form Java reports it in the {@code os.name} system
     * property, such as {@code Windows 11}, {@code Mac OS X}, {@code Linux} or {@code SunOS}. The name is trimmed
     * and matched ignoring case, so it can also come from somewhere other than this JVM, such as another machine.
     *
     * @param osName the OS name to identify; may be {@code null}
     * @return the operating system the name refers to, or {@code null} if {@code osName} is {@code null}, blank or
     * names none of these, such as OpenBSD or z/OS
     * @see #current()
     * @since 5.1.0
     */
    @Contract("null -> null")
    public static @Nullable OperatingSystem fromName(String osName) {
        if (osName == null) {
            return null;
        }

        String trimmedLowercaseName = osName.trim().toLowerCase(Locale.ROOT);

        if (trimmedLowercaseName.isBlank()) {
            return null;
        }

        if (trimmedLowercaseName.startsWith("windows")) {
            return WINDOWS;

        } else if (trimmedLowercaseName.contains("mac")) {
            return MAC_OS;

        } else if (trimmedLowercaseName.contains("linux")) {
            return LINUX;

        } else if (trimmedLowercaseName.contains("sunos") || trimmedLowercaseName.contains("solaris")) {
            return SUN_OS;

        } else if (trimmedLowercaseName.contains("freebsd")) {
            return FREE_BSD;

        } else if (trimmedLowercaseName.startsWith("aix")) {
            return AIX;

        } else {
            return null;
        }
    }

    /**
     * Get the operating system this program is running on, worked out from the {@code os.name} system property
     *
     * @return the current operating system, or {@code null} if it is none of these, such as OpenBSD or z/OS
     * @since 5.1.0
     */
    public static @Nullable OperatingSystem current() {
        return fromName(System.getProperty("os.name"));
    }

    /**
     * Check if this program is running on Windows
     *
     * @return {@code true} if the current operating system is Windows
     * @since 5.1.0
     */
    public static boolean isWindows() {
        return checkOS(WINDOWS);
    }

    /**
     * Check if this program is running on macOS
     *
     * @return {@code true} if the current operating system is macOS
     * @since 5.1.0
     */
    public static boolean isMacOS() {
        return checkOS(MAC_OS);
    }

    /**
     * Check if this program is running on Linux
     *
     * @return {@code true} if the current operating system is Linux, {@code false} for other Unix-like systems
     * such as FreeBSD
     * @since 5.1.0
     */
    public static boolean isLinux() {
        return checkOS(LINUX);
    }

    /**
     * Check if this program is running on Solaris, which Java reports as {@code SunOS}
     *
     * @return {@code true} if the current operating system is Solaris
     * @since 5.1.0
     */
    public static boolean isSunOS() {
        return checkOS(SUN_OS);
    }

    /**
     * Check if this program is running on FreeBSD
     *
     * @return {@code true} if the current operating system is FreeBSD
     * @since 5.1.0
     */
    public static boolean isFreeBSD() {
        return checkOS(FREE_BSD);
    }

    /**
     * Check if this program is running on AIX
     *
     * @return {@code true} if the current operating system is AIX
     * @since 5.1.0
     */
    public static boolean isAIX() {
        return checkOS(AIX);
    }

    private static boolean checkOS(OperatingSystem os) {
        OperatingSystem currentOS = current();

        return currentOS != null && currentOS.equals(os);
    }

    /**
     * Get the short code for this operating system
     *
     * @return the lowercase short code, such as {@code win}, {@code mac} or {@code linux}
     * @since 5.1.0
     */
    public String getCode() {
        return code;
    }
}
