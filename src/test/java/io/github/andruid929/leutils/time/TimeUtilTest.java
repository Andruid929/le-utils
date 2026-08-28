package io.github.andruid929.leutils.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.Calendar;

class TimeUtilTest {

    private final TimeUtil time = new TimeUtil() {
        @Override
        public long getEpochMillis() {
            return 1_116_671_460_000L;
        }
    };

    @Test
    @DisplayName("Get epoch second from millis")
    void getEpochSecond() {
        assertEquals(1_116_671_460, time.getEpochSecond());
    }

    @Test
    @DisplayName("Convert to instant")
    void toInstant() {
        assertEquals(1_116_671_460, time.toInstant().getEpochSecond());
    }

    @Test
    @DisplayName("Format time with custom format and timezone")
    void getTime() {
        String expTime = "21 May, 2005 | 12:31:00 GMT+02:00";

        String format = "dd MMMM, yyyy | HH:mm:ss z";

        assertEquals(expTime, time.getTime(format, ZoneId.of("GMT+2")));
    }

    @Test
    @DisplayName("Get actual time in HH:mm:ss format")
    void getActualTime() {
        assertEquals("12:31:00", time.getActualTime());
    }

    @Test
    @DisplayName("Get full date in Month Day, Year format")
    void getFullDate() {
        assertEquals("May 21, 2005", time.getFullDate());
    }

    @Test
    @DisplayName("Get day of month")
    void getDay() {
        assertEquals("21", time.getDay());
    }

    @Test
    @DisplayName("Get month name")
    void getMonth() {
        assertEquals("May", time.getMonth());
    }

    @Test
    @DisplayName("Get year")
    void getYear() {
        assertEquals("2005", time.getYear());
    }

    @Test
    @DisplayName("Get hour of day")
    void getHour() {
        assertEquals("12", time.getHour());
    }

    @Test
    @DisplayName("Get ISO 8601 datetime format")
    void get8601DateTime() {
        assertEquals("2005-05-21T12:31:00+02:00", time.get8601DateTime());
    }

    @Test
    @DisplayName("Get minute of hour")
    void getMinute() {
        assertEquals("31", time.getMinute());
    }

    @Test
    @DisplayName("Get second of minute")
    void getSecond() {
        assertEquals("00", time.getSecond());
    }

    @Test
    @DisplayName("Convert to calendar object")
    void toCalendar() {
        Calendar calendar = time.toCalendar();

        assertEquals(4, calendar.get(Calendar.MONTH));
    }
}
