package io.github.andruid929.leutils.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.andruid929.leutils.data.DataUnitConversion.DataUnit;

class DataUnitConversionTest {

    @Test
    void testCalculate() {
        assertEquals(1024, DataUnitConversion.calculate(1, DataUnit.PETABYTE, DataUnit.TERABYTE), 0.05);
    }

}