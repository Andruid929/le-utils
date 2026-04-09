package io.github.andruid929.leutils.swing.keyinput;

/**
 * Represents keyboard modifier keys used in key bindings.
 * <p>
 * This enum provides constants for common keyboard modifiers such as Control, Shift,
 * Meta, Alt, and AltGraph, along with their string literal representations.
 *
 * @author Andrew Jones
 * @since 4.0.0
 */
public enum KeyModifier {

    /**
     * The Control modifier key.
     */
    CTRL("control"),
    /**
     * The Shift modifier key.
     */
    SHIFT("shift"),
    /**
     * The Meta modifier key (Command on Mac, Windows key on Windows).
     */
    META("meta"),
    /**
     * The Alt modifier key.
     */
    ALT("alt"),
    /**
     * The AltGraph modifier key (used for typing alternate characters).
     */
    ALT_GRAPH("altGraph");

    /**
     * The string literal representation of the modifier key.
     */
    private final String literal;

    /**
     * Creates a new KeyModifier with the specified string literal.
     *
     * @param literal the string literal representation of the modifier key
     */
    KeyModifier(String literal) {
        this.literal = literal;
    }

    /**
     * Returns the string literal representation of this modifier key.
     *
     * @return the literal string for this modifier
     */
    public String getLiteral() {
        return literal;
    }
}
