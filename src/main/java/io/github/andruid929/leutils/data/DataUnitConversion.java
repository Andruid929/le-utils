package io.github.andruid929.leutils.data;

public final class DataUnitConversion {

    public static double calculate(double value, DataUnit from, DataUnit to) {
        if (value == 0) {
            return 0;
        } else if (value < 0) {
            throw new IllegalArgumentException("Negative data is not possible");
        }

        double valueInBytes = value * from.getNumberOfBytes();

        return valueInBytes / to.getNumberOfBytes();
    }

    public enum DataUnit {
        BYTE(1, "B"),
        KILOBYTE(1_024, "KB"),
        MEGABYTE(1_024_576, "MB"),
        GIGABYTE(1.07374182e9, "GB"),
        TERABYTE(1.09951163e12, "TB"),
        PETABYTE(1.12589991e15, "PB");

        private final double numberOfBytes;
        private final String unitSuffix;

        DataUnit(double numberOfBinaryBytes, String unitSuffix) {
            this.numberOfBytes = numberOfBinaryBytes;
            this.unitSuffix = unitSuffix;
        }

        public double getNumberOfBytes() {
            return numberOfBytes;
        }

        public String getUnitSuffix() {
            return unitSuffix;
        }
    }

}
