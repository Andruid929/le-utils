package io.github.andruid929.leutils.wora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The names here are the values real JVMs report in {@code os.name}.
 */
class OperatingSystemTest {

    @ParameterizedTest(name = "\"{0}\" is {1}")
    @DisplayName("Names reported by real JVMs are identified")
    @CsvSource({
            "Windows 11, WINDOWS",
            "Windows 10, WINDOWS",
            "Windows Server 2022, WINDOWS",
            "Mac OS X, MAC_OS", // Every macOS version, including those no longer called OS X
            "Linux, LINUX", // Also what WSL, ChromeOS and Android report
            "SunOS, SUN_OS",
            "FreeBSD, FREE_BSD",
            "AIX, AIX"
    })
    void fromName_realJvmNames(String osName, OperatingSystem expected) {
        assertEquals(expected, OperatingSystem.fromName(osName));
    }

    @ParameterizedTest(name = "\"{0}\" is {1}")
    @DisplayName("Names from other sources are matched regardless of case and surrounding whitespace")
    @CsvSource(value = {
            "'  Windows 11  '|WINDOWS",
            "'Linux\n'|LINUX",
            "WINDOWS 11|WINDOWS",
            "mac os x|MAC_OS",
            "freebsd|FREE_BSD"
    }, delimiter = '|')
    void fromName_caseAndWhitespace(String osName, OperatingSystem expected) {
        assertEquals(expected, OperatingSystem.fromName(osName));
    }

    @ParameterizedTest(name = "\"{0}\" is unknown")
    @DisplayName("Systems that aren't listed are unknown rather than guessed")
    @ValueSource(strings = {"OpenBSD", "NetBSD", "z/OS", "OS/400", "HP-UX"})
    void fromName_unlistedSystems(String osName) {
        assertNull(OperatingSystem.fromName(osName));
    }

    /**
     * "darwin" contains "win", so a check for "win" anywhere in the name would take it for Windows and give it
     * Windows paths.
     */
    @Test
    @DisplayName("Darwin is not mistaken for Windows")
    void fromName_darwin() {
        assertNull(OperatingSystem.fromName("Darwin"));
    }

    /**
     * {@code -Dos.name=} on the command line gives an empty name. Throwing here would stop {@link PathFinder} from
     * loading, since its static fields detect the OS.
     */
    @ParameterizedTest(name = "\"{0}\" is unknown")
    @DisplayName("Missing or blank names are unknown")
    @NullSource
    @ValueSource(strings = {"", "   ", "\t\n"})
    void fromName_missingOrBlank(String osName) {
        assertNull(OperatingSystem.fromName(osName));
    }

    @Test
    @DisplayName("The machine running the tests is identified")
    void current_buildMachine() {
        assertNotNull(OperatingSystem.current(), System.getProperty("os.name"));
    }
}
