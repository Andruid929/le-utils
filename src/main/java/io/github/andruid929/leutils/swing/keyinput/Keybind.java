package io.github.andruid929.leutils.swing.keyinput;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.swing.*;

/**
 * Represents a keyboard shortcut binding for a Swing JFrame.
 * <p>
 * This class encapsulates a key binding configuration that associates a keystroke
 * with an action to be performed when the key combination is pressed within a JFrame.
 *
 * @author Andrew Jones
 * @since 4.0.0
 */
public final class Keybind {

    /**
     * The JFrame to which this keybind is attached.
     */
    private final JFrame frame;
    /**
     * The unique identifier key used to map this keybind in the InputMap and ActionMap.
     */
    @NonNls
    private final Object keyForBind;
    /**
     * The keystroke that triggers this keybind.
     */
    private final KeyStroke keyStroke;
    /**
     * The action to be executed when the keystroke is triggered.
     */
    private final Action action;

    /**
     * Creates a new keybind and registers it with the specified JFrame.
     *
     * @param frame      the JFrame to attach this keybind to
     * @param keyForBind the unique identifier key for this keybind
     * @param keyStroke  the keystroke that will trigger the action
     * @param action     the action to execute when the keystroke is triggered
     */
    public Keybind(JFrame frame, @NonNls Object keyForBind, KeyStroke keyStroke, Action action) {
        this.frame = frame;
        this.keyForBind = keyForBind;
        this.keyStroke = keyStroke;
        this.action = action;

        addKeybind(frame, keyForBind, keyStroke, action);
    }


    /**
     * Adds a keybind to the specified JFrame with optional input map conditions.
     * <p>
     * This static utility method registers a keystroke-action mapping in the frame's
     * InputMap and ActionMap. If no condition is provided, the default InputMap is used.
     *
     * @param frame      the JFrame to attach the keybind to
     * @param keyForBind the unique identifier key for this keybind
     * @param keyStroke  the keystroke that will trigger the action
     * @param action     the action to execute when the keystroke is triggered
     * @param condition  optional varargs specifying the InputMap condition (e.g. {@link JOptionPane#WHEN_IN_FOCUSED_WINDOW})
     */
    @SuppressWarnings("MagicConstant")
    public static void addKeybind(@NotNull JFrame frame, @NonNls Object keyForBind, KeyStroke keyStroke, Action action, int @NotNull ... condition) {
        JRootPane rootPane = frame.getRootPane();

        ActionMap am = rootPane.getActionMap();

        InputMap im;

        if (condition.length == 0) {
            im = rootPane.getInputMap();

        } else {
            im = rootPane.getInputMap(condition[0]);
        }

        im.put(keyStroke, keyForBind);

        am.put(keyForBind, action);
    }

    /**
     * Adds a keybind to the specified JFrame with a specific input map condition.
     * <p>
     * This is a convenience method that delegates to the varargs version of addKeybind.
     *
     * @param frame      the JFrame to attach the keybind to
     * @param keyForBind the unique identifier key for this keybind
     * @param keyStroke  the keystroke that will trigger the action
     * @param action     the action to execute when the keystroke is triggered
     * @param condition  the InputMap condition (WHEN_FOCUSED, WHEN_IN_FOCUSED_WINDOW, or WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
     */
    public static void addKeybind(@NotNull JFrame frame, @NonNls Object keyForBind, KeyStroke keyStroke, Action action,
                                  @MagicConstant(intValues = {JComponent.WHEN_FOCUSED, JComponent.WHEN_IN_FOCUSED_WINDOW, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT}) int condition) {

        addKeybind(frame, keyForBind, keyStroke, action, condition, condition);
    }

    /**
     * Returns the unique identifier key used to map this keybind.
     *
     * @return the key identifier
     */
    @NonNls
    public Object keyForBind() {
        return keyForBind;
    }

    /**
     * Returns the keystroke that triggers this keybind.
     *
     * @return the associated KeyStroke
     */
    public KeyStroke keyStroke() {
        return keyStroke;
    }

    /**
     * Returns the action executed when the keystroke is triggered.
     *
     * @return the associated Action
     */
    public Action action() {
        return action;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Keybind) obj;
        return Objects.equals(this.frame, that.frame) &&
                Objects.equals(this.keyForBind, that.keyForBind) &&
                Objects.equals(this.keyStroke, that.keyStroke) &&
                Objects.equals(this.action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frame, keyForBind, keyStroke, action);
    }

    @Override
    public String toString() {
        return "Keybind[" +
                "frame=" + frame + ", " +
                "keyForBind=" + keyForBind + ", " +
                "keyStroke=" + keyStroke + ", " +
                "action=" + action + ']';
    }
    
}
