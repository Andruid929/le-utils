package io.github.andruid929.leutils.swing.keyinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.VK_A;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class KeystrokesTest {

    @Test
    @DisplayName("Create keystroke with single modifier")
    void testCreateKeystroke() {
        KeyStroke keyStroke = Keystrokes.createKeystroke('A', KeyModifier.CTRL);

        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', CTRL_DOWN_MASK), keyStroke);
    }

    @Test
    @DisplayName("Create keystroke with multiple modifiers")
    void testCreateKeystrokeWithMultipleModifiers() {
        KeyStroke keyStroke = Keystrokes.createKeystroke('A', KeyModifier.SHIFT, KeyModifier.META);

        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', SHIFT_DOWN_MASK | META_DOWN_MASK), keyStroke);
    }

    @Test
    @DisplayName("Create keystroke without modifiers")
    void testCreateKeystrokeWithNoModifiers() {
        KeyStroke keyStroke = Keystrokes.createKeystroke('A');

        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', 0), keyStroke);
    }

    @Test
    @DisplayName("Create keystroke with ctrl modifier convenience method")
    void testCtrlConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.ctrlPlus("A");
        KeyStroke keyStroke = Keystrokes.ctrlPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', CTRL_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', CTRL_DOWN_MASK), keyStroke);
    }

    @Test
    @DisplayName("Create keystroke with shift modifier convenience method")
    void testShiftConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.shiftPlus("A");
        KeyStroke keyStroke = Keystrokes.shiftPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', SHIFT_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', SHIFT_DOWN_MASK), keyStroke);
    }

    @Test
    @DisplayName("Create keystroke with meta modifier convenience method")
    void testMetaConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.metaPlus("A");
        KeyStroke keyStroke = Keystrokes.metaPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', META_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', META_DOWN_MASK), keyStroke);
    }

    @Test
    @DisplayName("Create keystroke with alt modifier convenience method")
    void testAltConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.altPlus("A");
        KeyStroke keyStroke = Keystrokes.altPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', ALT_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', ALT_DOWN_MASK), keyStroke);
    }

    @Test
    @DisplayName("Create keystroke with altgraph modifier convenience method")
    void testAltGraphConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.altGraphPlus("A");
        KeyStroke keyStroke = Keystrokes.altGraphPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', ALT_GRAPH_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', ALT_GRAPH_DOWN_MASK), keyStroke);
    }
}
