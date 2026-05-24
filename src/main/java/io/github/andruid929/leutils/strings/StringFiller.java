package io.github.andruid929.leutils.strings;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.regex.Pattern;

public final class StringFiller {

    private final String original;

    private final Map<String, String> keyValueMap;

    private int modifications = 0;

    private final StringBuilder FILLED_STRING_BUILDER = new StringBuilder();

    public StringFiller(@NotNull String original, @NotNull Map<String, String> keyValueMap) {
        this.original = original;
        this.keyValueMap = keyValueMap;

        if (keyValueMap.isEmpty()) {
            return;
        }

        for (String key : keyValueMap.keySet()) {

            String placeholder = getPlaceholder(key);

            if (original.contains(placeholder)) {
                String value = keyValueMap.get(key);

                String filled = replaceEach(placeholder, value);

                FILLED_STRING_BUILDER.setLength(0);
                FILLED_STRING_BUILDER.append(filled);

                modifications++;
            }
        }
    }

    public int getNumberOfModifications() {
        return modifications;
    }

    public @NotNull String getOriginal() {
        return original;
    }

    public @NotNull String get() {
        if (modifications == 0) {
            return original;
        }

        return FILLED_STRING_BUILDER.toString();
    }

    public Map<String, String> getKeyValueMap() {
        return keyValueMap;
    }

    private @NotNull String replaceEach(String placeholder, String value) {
        String targetString;

        if (FILLED_STRING_BUILDER.length() == 0) {
            targetString = original;

        } else {
            targetString = FILLED_STRING_BUILDER.toString();
        }

        return targetString.replaceAll(Pattern.quote(placeholder), value);
    }

    private @NotNull String getPlaceholder(String key) {
        return "$".concat(key).concat("$");
    }

    @Contract("_, _, _ -> new")
    public static @NotNull StringFiller onceOff(@NotNull String original, @NotNull String key, @NotNull String value) {
        return new StringFiller(original, Map.of(key, value));
    }

    public static @NotNull String replace(@NotNull String original, @NotNull String key, @NotNull String value) {
        return onceOff(original, key, value).get();
    }

}


