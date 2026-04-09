package io.github.andruid929.leutils.swing.keyinput;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;

/**
 * Utility class for creating KeyStroke objects with various modifier combinations.
 * <p>
 * This class provides convenient factory methods for creating KeyStroke instances
 * commonly used in Swing key bindings, with support for CTRL, SHIFT, and ALT modifiers.
 * <p>
 * <b>Usage Examples:</b>
 * <pre>{@code
 * // Create a simple Ctrl+S keystroke
 * KeyStroke saveKeystroke = Keystrokes.ctrlPlus("S");
 *
 * // Create a Shift+F5 keystroke
 * KeyStroke refreshKeystroke = Keystrokes.shiftPlus(KeyEvent.VK_F5);
 *
 * // Create a Ctrl+Shift+N keystroke
 * KeyStroke newWindowKeystroke = Keystrokes.ctrlShiftPlus("N");
 *
 * // Create an Alt+F4 keystroke
 * KeyStroke closeKeystroke = Keystrokes.altPlus(KeyEvent.VK_F4);
 *
 * // Create a custom keystroke with multiple modifiers
 * KeyStroke customKeystroke = Keystrokes.createKeystroke("Q",
 *     KeyModifier.CTRL, KeyModifier.ALT);
 *
 * // Use with Keybind to register an action
 * Keybind saveKeybind = new Keybind(
 *     frame,
 *     "save-action",
 *     Keystrokes.ctrlPlus("S"),
 *     new AbstractAction() {
 *         public void actionPerformed(ActionEvent e) {
 *             // Save logic here
 *         }
 *     }
 * );
 * }</pre>
 *
 * @author Andrew Jones
 * @see Keybind
 * @see KeyModifier
 * @since 4.0.0
 */
public final class Keystrokes {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Keystrokes() {
    }

    /**
     * Creates a KeyStroke with the specified key and modifiers.
     *
     * @param key          the key character as a String (will be converted to uppercase)
     * @param keyModifiers optional varargs of KeyModifier values to apply
     * @return a KeyStroke representing the key combination
     */
    public static KeyStroke createKeystroke(@NotNull String key, KeyModifier... keyModifiers) {
        String modifiers = modifierBuilder(keyModifiers);

        System.out.println(modifiers);

        String validKey = key.toUpperCase();

        return KeyStroke.getKeyStroke(modifiers.concat(validKey).trim());
    }

    /**
     * Creates a KeyStroke with the specified key code and modifiers.
     *
     * @param keyCode      the key code from KeyEvent (e.g., KeyEvent.VK_F5)
     * @param keyModifiers optional varargs of KeyModifier values to apply
     * @return a KeyStroke representing the key combination
     */
    public static KeyStroke createKeystroke(int keyCode, KeyModifier... keyModifiers) {
        String modifiers = modifierBuilder(keyModifiers);

        String validKey = KeyEvent.getKeyText(keyCode);

        return KeyStroke.getKeyStroke(modifiers.concat(validKey).trim());
    }

    /**
     * Creates a KeyStroke with the CTRL modifier and the specified key.
     *
     * @param key the key character as a String
     * @return a KeyStroke representing Ctrl+key
     */
    public static KeyStroke ctrlPlus(String key) {
        return createKeystroke(key, KeyModifier.CTRL);
    }

    /**
     * Creates a KeyStroke with the CTRL modifier and the specified key code.
     *
     * @param keyCode the key code from KeyEvent
     * @return a KeyStroke representing Ctrl+key
     */
    public static KeyStroke ctrlPlus(int keyCode) {
        return createKeystroke(keyCode, KeyModifier.CTRL);
    }

    /**
     * Creates a KeyStroke with the SHIFT modifier and the specified key.
     *
     * @param key the key character as a String
     * @return a KeyStroke representing Shift+key
     */
    public static KeyStroke shiftPlus(String key) {
        return createKeystroke(key, KeyModifier.SHIFT);
    }

    /**
     * Creates a KeyStroke with the SHIFT modifier and the specified key code.
     *
     * @param keyCode the key code from KeyEvent
     * @return a KeyStroke representing Shift+key
     */
    public static KeyStroke shiftPlus(int keyCode) {
        return createKeystroke(keyCode, KeyModifier.SHIFT);
    }

    /**
     * Creates a KeyStroke with the ALT modifier and the specified key.
     *
     * @param key the key character as a String
     * @return a KeyStroke representing Alt+key
     */
    public static KeyStroke altPlus(String key) {
        return createKeystroke(key, KeyModifier.ALT);
    }

    /**
     * Creates a KeyStroke with the ALT modifier and the specified key code.
     *
     * @param keyCode the key code from KeyEvent
     * @return a KeyStroke representing Alt+key
     */
    public static KeyStroke altPlus(int keyCode) {
        return createKeystroke(keyCode, KeyModifier.ALT);
    }

    /**
     * Creates a KeyStroke with the CTRL and SHIFT modifiers and the specified key.
     *
     * @param key the key character as a String
     * @return a KeyStroke representing Ctrl+Shift+key
     */
    public static KeyStroke ctrlShiftPlus(String key) {
        return createKeystroke(key, KeyModifier.CTRL, KeyModifier.SHIFT);
    }

    /**
     * Creates a KeyStroke with the CTRL and SHIFT modifiers and the specified key code.
     *
     * @param keyCode the key code from KeyEvent
     * @return a KeyStroke representing Ctrl+Shift+key
     */
    public static KeyStroke ctrlShiftPlus(int keyCode) {
        return createKeystroke(keyCode, KeyModifier.CTRL, KeyModifier.SHIFT);
    }

    /**
     * Builds a modifier string from the provided KeyModifier values.
     * <p>
     * This method constructs a space-separated string of modifier literals,
     * removing duplicates if present in the input array.
     *
     * @param modifiers the KeyModifier values to build into a string
     * @return a space-separated string of modifier literals, or empty string if no modifiers
     */
    @Contract(pure = true)
    static @NotNull String modifierBuilder(KeyModifier @NotNull ... modifiers) {
        if (modifiers.length == 0) {
            return "";
        }

        Set<KeyModifier> availableModifiers = Stream.of(KeyModifier.values()).collect(Collectors.toSet());

        StringBuilder builder = new StringBuilder();

        for (KeyModifier modifier : modifiers) {
            
            if (availableModifiers.contains(modifier)) {
                builder.append(modifier.getLiteral()).append(" ");

                availableModifiers.remove(modifier);
            }
        }

        return builder.toString();
    }
}
