package io.github.andruid929.leutils.stringutil;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * Utility class for string normalisation tasks, such as URL formatting.
 *
 * @author Andrew Jones
 * @since 4.4.0
 */
public final class StringNormaliser {

    private StringNormaliser() {
    }

    /**
     * Normalises a URL by trimming whitespace, replacing spaces according to the specified mode,
     * and converting backslashes to forward slashes.
     *
     * @param input non-null URL string to normalise
     * @param mode  space replacement strategy (e.g. %20 or hyphen)
     * @return normalised URL string
     */
    public static @NotNull String normaliseUrl(@NotNull String input, @NotNull SpaceMode mode) {
        String trimmedUrl = input.trim();

        return trimmedUrl.replaceAll(" ", mode.literalReplacement)
                .replaceAll(Pattern.quote("\\"), "/");
    }

    /**
     * Defines how spaces should be replaced in URL normalisation.
     */
    public enum SpaceMode {

        /**
         * Replace spaces with an encoded space (%20).
         */
        SPACE("%20"),

        /**
         * Replace spaces with underscores (_)
         *
         * @since 4.4.0
         */

        UNDERSCORE("_"),

        /**
         * Replace spaces with hyphens (-).
         */
        HYPHEN("-");

        /**
         * Replacement text used for spaces in the selected mode.
         */
        public final String literalReplacement;

        SpaceMode(String literalReplacement) {
            this.literalReplacement = literalReplacement;
        }

    }

}
