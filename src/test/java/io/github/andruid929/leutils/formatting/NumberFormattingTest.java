package io.github.andruid929.leutils.formatting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class NumberFormattingTest {

    @Test
    void formatPatternBuilder() {
        assertEquals("#,###", NumberFormatting.formatterPatternBuilder(0));
        assertEquals("#,###.#", NumberFormatting.formatterPatternBuilder(1));
        assertEquals("#,###.##", NumberFormatting.formatterPatternBuilder(2));
        assertEquals("#,###.###", NumberFormatting.formatterPatternBuilder(3));

        assertThrows(IllegalArgumentException.class, () -> NumberFormatting.formatterPatternBuilder(-1));
    }

    @Test
    void formatNumber() {
        assertEquals("1,234.57", NumberFormatting.formatNumber(1234.5678));
        assertEquals("1,234", NumberFormatting.formatNumber(1234));
        assertEquals("1,234.5", NumberFormatting.formatNumber(1234.5));
        assertEquals("0.12", NumberFormatting.formatNumber(0.123));
    }

    @Test
    void formatter() {
        assertEquals("1,234.568", NumberFormatting.formatter(1234.5678, 3));
        assertEquals("1,234.6", NumberFormatting.formatter(1234.5678, 1));
        assertEquals("1,235", NumberFormatting.formatter(1234.5678, 0));
        assertEquals("1,234", NumberFormatting.formatter(1234.1234, 0));
    }
}