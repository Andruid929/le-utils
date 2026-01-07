package io.github.andruid929.leutils.time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TimeUnitConversionTest {

    @Test
    void milliToSecond() {
        assertEquals(0.5, TimeUnitConversion.milliToSecond(500.0));
    }

    @Test
    void secondToMinute() {
        assertEquals(0.5, TimeUnitConversion.secondToMinute(30.0));
    }

    @Test
    void minuteToHour() {
        assertEquals(0.25, TimeUnitConversion.minuteToHour(15.0));
    }

    @Test
    void hourToDay() {
        assertEquals(0.25, TimeUnitConversion.hourToDay(6.0));
    }

    @Test
    void longMilliToSecond() {
        assertEquals(2, TimeUnitConversion.milliToSecond(2000));
    }

    @Test
    void longSecondToMinute() {
        assertEquals(2, TimeUnitConversion.secondToMinute(120));
    }

    @Test
    void longMinuteToHour() {
        assertEquals(3, TimeUnitConversion.minuteToHour(180));
    }

    @Test
    void longHourToDay() {
        assertEquals(3, TimeUnitConversion.hourToDay(72));
    }

    @ParameterizedTest
    @CsvSource({
            "0.1, DAYS, HOURS, 2.4",
            "0.5, HOURS, MINUTES, 30.0",
            "360.0, SECONDS, HOURS, 0.1"
    })
    void calculate(double value, TimeUnit from, TimeUnit to, double expected) {
        assertEquals(expected, TimeUnitConversion.calculate(value, from, to));
    }

    @ParameterizedTest
    @CsvSource({
            "2, DAYS, HOURS, 48",
            "4, HOURS, MINUTES, 240",
            "1, HOURS, SECONDS, 3600"
    })
    void longCalculate(long value, TimeUnit from, TimeUnit to, long expected) {
        assertEquals(expected, TimeUnitConversion.calculate(value, from, to));
    }

    @Test
    void throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> TimeUnitConversion.milliToSecond(-2.0));
    }

    @Test
    void longThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> TimeUnitConversion.milliToSecond(-2));
    }

    @Test
    void returnsZero() {
        assertEquals(0, TimeUnitConversion.milliToSecond(0.0));
    }

    @Test
    void longReturnsZero() {
        assertEquals(0, TimeUnitConversion.milliToSecond(0));
    }

    @Test
    void truncatesLong() {
        assertEquals(1, TimeUnitConversion.secondToMinute(90));
    }

    @Test
    void overflows() {
        long hugeAmountOfDays = 202_601_070_000L;

        long value = TimeUnitConversion.calculate(hugeAmountOfDays, TimeUnit.DAYS, TimeUnit.MILLISECONDS);

        assertTrue(value < 0);
    }
}