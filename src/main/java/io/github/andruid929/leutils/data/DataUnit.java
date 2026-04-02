package io.github.andruid929.leutils.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents different units of data storage size.
 * <p>
 * Provides conversion factors and helper methods for converting values between these units.
 * The underlying base is 1,024 (binary prefix).
 *
 * @since 3.4.0
 * @author Andrew Jones
 */
public enum DataUnit {
    /**
     * 1 Byte
     */
    BYTE(1L, "B", "Byte"),
    /**
     * 1,024 Bytes
     */
    KILOBYTE(1_024L, "KB", "Kilobyte"),
    /**
     * 1,024 Kilobytes
     */
    MEGABYTE(1_024L * 1_024L, "MB", "Megabyte"),
    /**
     * 1,024 Megabytes
     */
    GIGABYTE(1_024L * 1_024L * 1_024L, "GB", "Gigabyte"),
    /**
     * 1,024 Gigabytes
     */
    TERABYTE(1_024L * 1_024L * 1_024L * 1_024L, "TB", "Terabyte"),
    /**
     * 1,024 Terabytes
     */
    PETABYTE(1_024L * 1_024L * 1_024L * 1_024L * 1_024L, "PB", "Petabyte");

    private final long numberOfBytes;
    private final String unitSuffix;
    private final String name;

    /**
     * Constructs a DataUnit.
     *
     * @param numberOfBytes the number of bytes this unit represents
     * @param unitSuffix    the short suffix for this unit (e.g. "KB")
     * @param name          the human-readable name of the unit
     */
    DataUnit(long numberOfBytes, String unitSuffix, String name) {
        this.numberOfBytes = numberOfBytes;
        this.unitSuffix = unitSuffix;
        this.name = name;
    }

    /**
     * Converts a value from this unit to another unit.
     * <p>
     * <b>Note:</b> This method uses long arithmetic. Extremely large conversions (e.g., from Petabyte to Byte)
     * may overflow {@link Long#MAX_VALUE}, resulting in wrap-around behaviour.
     *
     * @param value the numeric value to convert
     * @param to    the target unit to convert to
     * @return the converted value in the target unit
     */
    @Contract(pure = true)
    public long convertTo(long value, @NotNull DataUnit to) {
        long inBytes = numberOfBytes * value;

        return inBytes / to.numberOfBytes;
    }

    /**
     * Converts a value from this unit to another unit using double precision.
     *
     * @param value the numeric value to convert
     * @param to    the target unit to convert to
     * @return the converted value in the target unit
     */
    @Contract(pure = true)
    public double convertTo(double value, @NotNull DataUnit to) {
        double inBytes = numberOfBytes * value;

        return inBytes / to.numberOfBytes;
    }

    /**
     * Returns the unit suffix (e.g. "KB", "MB").
     *
     * @return the unit suffix
     */
    public String getUnitSuffix() {
        return unitSuffix;
    }

    /**
     * Returns the number of bytes this unit represents.
     *
     * @return the number of bytes
     */
    public long getNumberOfBytes() {
        return numberOfBytes;
    }

    /**
     * Returns the human-readable name of this unit (e.g. "Kilobyte").
     *
     * @return the name of the unit
     */
    public String getName() {
        return name;
    }
}
