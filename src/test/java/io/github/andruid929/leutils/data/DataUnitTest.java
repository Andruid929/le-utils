package io.github.andruid929.leutils.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DataUnitTest {

    @Test
    void convertTo() {
        assertEquals(2048, DataUnit.GIGABYTE.convertTo(2, DataUnit.MEGABYTE));
        assertEquals(4, DataUnit.GIGABYTE.convertTo(4096, DataUnit.TERABYTE));
        assertEquals(1024, DataUnit.KILOBYTE.convertTo(1, DataUnit.BYTE));
        assertEquals(0, DataUnit.BYTE.convertTo(1, DataUnit.KILOBYTE));
    }

    @Test
    void convertToDouble() {
        assertEquals(2048.0, DataUnit.GIGABYTE.convertTo(2.0, DataUnit.MEGABYTE));
        assertEquals(0.5, DataUnit.GIGABYTE.convertTo(512.0, DataUnit.TERABYTE));
        assertEquals(1024.0, DataUnit.KILOBYTE.convertTo(1.0, DataUnit.BYTE));
        assertEquals(0.25, DataUnit.KILOBYTE.convertTo(256.0, DataUnit.MEGABYTE));
    }

    @Test
    void getUnitSuffix() {
        assertEquals("B", DataUnit.BYTE.getUnitSuffix());
        assertEquals("KB", DataUnit.KILOBYTE.getUnitSuffix());
        assertEquals("MB", DataUnit.MEGABYTE.getUnitSuffix());
        assertEquals("GB", DataUnit.GIGABYTE.getUnitSuffix());
        assertEquals("TB", DataUnit.TERABYTE.getUnitSuffix());
        assertEquals("PB", DataUnit.PETABYTE.getUnitSuffix());
    }

    @Test
    void getNumberOfBytes() {
        assertEquals(1L, DataUnit.BYTE.getNumberOfBytes());
        assertEquals(1024L, DataUnit.KILOBYTE.getNumberOfBytes());
        assertEquals(1024L * 1024L, DataUnit.MEGABYTE.getNumberOfBytes());
        assertEquals(1024L * 1024L * 1024L, DataUnit.GIGABYTE.getNumberOfBytes());
        assertEquals(1024L * 1024L * 1024L * 1024L, DataUnit.TERABYTE.getNumberOfBytes());
        assertEquals(1024L * 1024L * 1024L * 1024L * 1024L, DataUnit.PETABYTE.getNumberOfBytes());
    }

    @Test
    void getName() {
        assertEquals("Byte", DataUnit.BYTE.getName());
        assertEquals("Kilobyte", DataUnit.KILOBYTE.getName());
        assertEquals("Megabyte", DataUnit.MEGABYTE.getName());
        assertEquals("Gigabyte", DataUnit.GIGABYTE.getName());
        assertEquals("Terabyte", DataUnit.TERABYTE.getName());
        assertEquals("Petabyte", DataUnit.PETABYTE.getName());
    }

    @Test
    void values() {
        assertEquals(6, DataUnit.values().length);
        assertNotNull(DataUnit.valueOf("BYTE"));
    }

}