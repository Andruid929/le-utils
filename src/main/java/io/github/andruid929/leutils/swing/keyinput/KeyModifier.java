package io.github.andruid929.leutils.swing.keyinput;

import java.awt.event.InputEvent;

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
    CTRL("control", InputEvent.CTRL_DOWN_MASK),
    /**
     * The Shift modifier key.
     */
    SHIFT("shift", InputEvent.SHIFT_DOWN_MASK),
    /**
     * The Meta modifier key (Command on Mac, Windows key on Windows).
     */
    META("meta", InputEvent.META_DOWN_MASK),
    /**
     * The Alt modifier key.
     */
    ALT("alt", InputEvent.ALT_DOWN_MASK),
    /**
     * The AltGraph modifier key (used for typing alternate characters).
     */
    ALT_GRAPH("altGraph", InputEvent.ALT_GRAPH_DOWN_MASK);

    /**
     * The string literal representation of the modifier key.
     */
    private final String literal;

    private final int modifierValue;

    /**
     * Creates a new KeyModifier with the specified string literal.
     *
     * @param literal the string literal representation of the modifier key
     */
    KeyModifier(String literal, int modifierValue) {
        this.literal = literal;
        this.modifierValue = modifierValue;
    }

    /**
     * Returns the string literal representation of this modifier key.
     *
     * @return the literal string for this modifier
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Retrieves the integer value of the modifier key.
     *
     * @return the modifier value associated with this key
     */
    public int getModifierValue() {
        return modifierValue;
    }
}
