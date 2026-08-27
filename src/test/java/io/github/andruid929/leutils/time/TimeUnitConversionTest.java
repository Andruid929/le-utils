package io.github.andruid929.leutils.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TimeUnitConversionTest {

    @Test
    @DisplayName("Convert milliseconds to seconds")
    void milliToSecond() {
        assertEquals(0.5, TimeUnitConversion.milliToSecond(500.0));
    }

    @Test
    @DisplayName("Convert seconds to minutes")
    void secondToMinute() {
        assertEquals(0.5, TimeUnitConversion.secondToMinute(30.0));
    }

    @Test
    @DisplayName("Convert minutes to hours")
    void minuteToHour() {
        assertEquals(0.25, TimeUnitConversion.minuteToHour(15.0));
    }

    @Test
    @DisplayName("Convert hours to days")
    void hourToDay() {
        assertEquals(0.25, TimeUnitConversion.hourToDay(6.0));
    }

    @Test
    @DisplayName("Format time value with unit conversion")
    void formatWithUnit() {
        String formatLongWithUnit = TimeUnitConversion.formatWithUnit(2, TimeUnit.DAYS, TimeUnit.MINUTES);

        String formatWithUnit = TimeUnitConversion.formatWithUnit(45.0, TimeUnit.SECONDS, TimeUnit.HOURS);
        String formatWithUnitRounded = TimeUnitConversion.formatWithUnit(45.0, TimeUnit.SECONDS, TimeUnit.HOURS, true);

        assertEquals("2880m", formatLongWithUnit);
        assertEquals("0.0125h", formatWithUnit);
        assertEquals("0.013h", formatWithUnitRounded);
    }

    @Test
    @DisplayName("Convert long milliseconds to seconds")
    void longMilliToSecond() {
        assertEquals(2, TimeUnitConversion.milliToSecond(2000));
    }

    @Test
    @DisplayName("Convert long seconds to minutes")
    void longSecondToMinute() {
        assertEquals(2, TimeUnitConversion.secondToMinute(120));
    }

    @Test
    @DisplayName("Convert long minutes to hours")
    void longMinuteToHour() {
        assertEquals(3, TimeUnitConversion.minuteToHour(180));
    }

    @Test
    @DisplayName("Convert long hours to days")
    void longHourToDay() {
        assertEquals(3, TimeUnitConversion.hourToDay(72));
    }

    @ParameterizedTest
    @DisplayName("Calculate double value conversions between time units")
    @CsvSource({
            "0.1, DAYS, HOURS, 2.4",
            "0.5, HOURS, MINUTES, 30.0",
            "360.0, SECONDS, HOURS, 0.1"
    })
    void calculate(double value, TimeUnit from, TimeUnit to, double expected) {
        assertEquals(expected, TimeUnitConversion.calculate(value, from, to));
    }

    @ParameterizedTest
    @DisplayName("Calculate long value conversions between time units")
    @CsvSource({
            "2, DAYS, HOURS, 48",
            "4, HOURS, MINUTES, 240",
            "1, HOURS, SECONDS, 3600"
    })
    void longCalculate(long value, TimeUnit from, TimeUnit to, long expected) {
        assertEquals(expected, TimeUnitConversion.calculate(value, from, to));
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for negative double values")
    void throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> TimeUnitConversion.milliToSecond(-2.0));
    }

    @Test
    @DisplayName("Throw IllegalArgumentException for negative long values")
    void longThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> TimeUnitConversion.milliToSecond(-2));
    }

    @Test
    @DisplayName("Return zero for zero double input")
    void returnsZero() {
        assertEquals(0, TimeUnitConversion.milliToSecond(0.0));
    }

    @Test
    @DisplayName("Return zero for zero long input")
    void longReturnsZero() {
        assertEquals(0, TimeUnitConversion.milliToSecond(0));
    }

    @Test
    @DisplayName("Truncate long values on conversion")
    void truncatesLong() {
        assertEquals(1, TimeUnitConversion.secondToMinute(90));
    }

    @Test
    @DisplayName("Overflow on very large time conversions")
    void overflows() {
        long hugeAmountOfDays = 202_601_070_000L;

        long value = TimeUnitConversion.calculate(hugeAmountOfDays, TimeUnit.DAYS, TimeUnit.MILLISECONDS);

        assertTrue(value < 0);
    }

    @Test
    @DisplayName("Calculate as double for long inputs")
    void calculateAsDouble() {
        assertEquals(0.5, TimeUnitConversion.calculateAsDouble(30, TimeUnit.SECONDS, TimeUnit.MINUTES));
        assertEquals(1.5, TimeUnitConversion.calculateAsDouble(90, TimeUnit.SECONDS, TimeUnit.MINUTES));
    }
}
