package io.github.andruid929.leutils.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DataUnitConversionTest {

    @Test
    void calculateLong() {
        assertEquals(1024L, DataUnitConversion.calculate(1L, DataUnit.KILOBYTE, DataUnit.BYTE));
        assertEquals(1L, DataUnitConversion.calculate(1024L, DataUnit.KILOBYTE, DataUnit.MEGABYTE));
        assertEquals(0L, DataUnitConversion.calculate(0L, DataUnit.KILOBYTE, DataUnit.BYTE));

        assertThrows(IllegalArgumentException.class, () -> DataUnitConversion.calculate(-1L, DataUnit.KILOBYTE, DataUnit.BYTE));
    }

    @Test
    void calculateDouble() {
        assertEquals(1024.0, DataUnitConversion.calculate(1.0, DataUnit.KILOBYTE, DataUnit.BYTE));
        assertEquals(0.5, DataUnitConversion.calculate(512.0, DataUnit.KILOBYTE, DataUnit.MEGABYTE));
        assertEquals(0.0, DataUnitConversion.calculate(0.0, DataUnit.KILOBYTE, DataUnit.BYTE));

        assertThrows(IllegalArgumentException.class, () -> DataUnitConversion.calculate(-1.0, DataUnit.KILOBYTE, DataUnit.BYTE));
    }

    @Test
    void calculateAsDouble() {
        assertEquals(1024.0, DataUnitConversion.calculateAsDouble(1L, DataUnit.KILOBYTE, DataUnit.BYTE));
        assertEquals(0.5, DataUnitConversion.calculateAsDouble(512L, DataUnit.KILOBYTE, DataUnit.MEGABYTE));
        assertEquals(0.0, DataUnitConversion.calculateAsDouble(0L, DataUnit.KILOBYTE, DataUnit.BYTE));

        assertThrows(IllegalArgumentException.class, () -> DataUnitConversion.calculateAsDouble(-1L, DataUnit.KILOBYTE, DataUnit.BYTE));
    }

    @Test
    void formatWithUnitLong() {
        assertEquals("1,024B", DataUnitConversion.formatWithUnit(1L, DataUnit.KILOBYTE, DataUnit.BYTE));
        assertEquals("1MB", DataUnitConversion.formatWithUnit(1024L, DataUnit.KILOBYTE, DataUnit.MEGABYTE));
    }

    @Test
    void formatWithUnitDouble() {
        // Without rounding
        assertEquals("1024.0B", DataUnitConversion.formatWithUnit(1.0, DataUnit.KILOBYTE, DataUnit.BYTE));
        assertEquals("0.5MB", DataUnitConversion.formatWithUnit(512.0, DataUnit.KILOBYTE, DataUnit.MEGABYTE));
        
        // With rounding (NumberFormatting.formatNumber uses 2 decimal places and #,### pattern)
        assertEquals("1,024B", DataUnitConversion.formatWithUnit(1.0, DataUnit.KILOBYTE, DataUnit.BYTE, true));
        assertEquals("0.5MB", DataUnitConversion.formatWithUnit(512.0, DataUnit.KILOBYTE, DataUnit.MEGABYTE, true));
        
        // 1 Byte to Gigabyte is 1 / (1024^3) = 9.313225746154785E-10
        // NumberFormatting.formatNumber(9.313225746154785E-10) with 2 decimal places will be "0"
        assertEquals("0GB", DataUnitConversion.formatWithUnit(1.0, DataUnit.BYTE, DataUnit.GIGABYTE, true));
        
        assertEquals("0.25MB", DataUnitConversion.formatWithUnit(256.0, DataUnit.KILOBYTE, DataUnit.MEGABYTE, true));
    }

    @Test
    void overflowPossible() {
        // 9,000 PB to Byte will overflow Long.MAX_VALUE
        // 1 PB = 2^50 bytes = 1,125,899,906,842,624 bytes
        // 9,000 PB = 9000 * 2^50 = 10,133,099,161,583,616,000 bytes
        // Long.MAX_VALUE = 9,223,372,036,854,775,807
        // Upon overflow, it wraps around in standard Java long multiplication
        long overflowValue = DataUnitConversion.calculate(9000L, DataUnit.PETABYTE, DataUnit.BYTE);
        
        // 10,133,099,161,583,616,000 (mod 2^64)
        // = 10,133,099,161,583,616,000 - 18,446,744,073,709,551,616
        // = -8,313,644,912,125,935,616
        assertTrue(overflowValue < 0);
    }
}
