package io.github.andruid929.leutils.swing.keyinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.VK_A;

import org.junit.jupiter.api.Test;

import javax.swing.*;

class KeystrokesTest {

    @Test
    void testCreateKeystroke() {
        KeyStroke keyStroke = Keystrokes.createKeystroke('A', KeyModifier.CTRL);

        assertNotNull(keyStroke);
        
        assertEquals(KeyStroke.getKeyStroke('A', CTRL_DOWN_MASK), keyStroke);
    }

    @Test
    void testCreateKeystrokeWithMultipleModifiers() {
        KeyStroke keyStroke = Keystrokes.createKeystroke('A', KeyModifier.SHIFT, KeyModifier.META);

        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', SHIFT_DOWN_MASK | META_DOWN_MASK), keyStroke);
    }

    @Test
    void testCreateKeystrokeWithNoModifiers() {
        KeyStroke keyStroke = Keystrokes.createKeystroke('A');

        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', 0), keyStroke);
    }

    @Test
    void testCtrlConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.ctrlPlus("A");
        KeyStroke keyStroke = Keystrokes.ctrlPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', CTRL_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', CTRL_DOWN_MASK), keyStroke);
    }

    @Test
    void testShiftConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.shiftPlus("A");
        KeyStroke keyStroke = Keystrokes.shiftPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', SHIFT_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', SHIFT_DOWN_MASK), keyStroke);
    }

    @Test
    void testMetaConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.metaPlus("A");
        KeyStroke keyStroke = Keystrokes.metaPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', META_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', META_DOWN_MASK), keyStroke);
    }

    @Test
    void testAltConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.altPlus("A");
        KeyStroke keyStroke = Keystrokes.altPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', ALT_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', ALT_DOWN_MASK), keyStroke);
    }

    @Test
    void testAltGraphConvenienceMethod() {
        KeyStroke keyStrokeString = Keystrokes.altGraphPlus("A");
        KeyStroke keyStroke = Keystrokes.altGraphPlus(VK_A);

        assertNotNull(keyStrokeString);
        assertNotNull(keyStroke);

        assertEquals(KeyStroke.getKeyStroke('A', ALT_GRAPH_DOWN_MASK), keyStrokeString);
        assertEquals(KeyStroke.getKeyStroke('A', ALT_GRAPH_DOWN_MASK), keyStroke);
    }
}
