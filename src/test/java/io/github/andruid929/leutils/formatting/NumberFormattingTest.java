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
    void formatWholeNumber() {
        assertEquals("1,234", NumberFormatting.formatWholeNumber(1234L));
        assertEquals("1,234,567", NumberFormatting.formatWholeNumber(1234567));
        assertEquals("123", NumberFormatting.formatWholeNumber(123L));
        assertEquals("12,345", NumberFormatting.formatWholeNumber(12345L));
    }

    @Test
    void formatter() {
        assertEquals("1,234.568", NumberFormatting.formatter(1234.5678, 3));
        assertEquals("1,234.6", NumberFormatting.formatter(1234.5678, 1));
        assertEquals("1,235", NumberFormatting.formatter(1234.5678, 0));
        assertEquals("1,234", NumberFormatting.formatter(1234.1234, 0));
    }

    @Test
    void formatPercentage() {
        assertEquals("100.00%", NumberFormatting.percentage("100"));
        assertEquals("97.00%", NumberFormatting.percentage("96.99999"));
        assertEquals("87.25%", NumberFormatting.percentage("87.252"));
        assertEquals("87.26%", NumberFormatting.percentage("87.255"));
    }

    @Test
    void formatPercentageDecimal() {
        assertEquals("100.00%", NumberFormatting.percentageDecimal("1"));
        assertEquals("97.00%", NumberFormatting.percentageDecimal("0.9699999"));
        assertEquals("87.25%", NumberFormatting.percentageDecimal("0.87252"));
        assertEquals("87.26%", NumberFormatting.percentageDecimal("0.87255"));
    }
}