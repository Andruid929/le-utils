package io.github.andruid929.leutils.time;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeUtilTest {

    private final TimeUtil time = new TimeUtil() {
        @Override
        public long getEpochMillis() {
            return 1_116_671_460_000L;
        }
    };

    @Test
    void getEpochSecond() {
        assertEquals(1_116_671_460, time.getEpochSecond());
    }

    @Test
    void toInstant() {
        assertEquals(1_116_671_460, time.toInstant().getEpochSecond());
    }

    @Test
    void getTime() {
        String expTime = "21 May, 2005 | 12:31:00 GMT+02:00";

        String format = "dd MMMM, yyyy | HH:mm:ss z";

        assertEquals(expTime, time.getTime(format, ZoneId.of("GMT+2")));
    }

    @Test
    void getActualTime() {
        assertEquals("12:31:00", time.getActualTime());
    }

    @Test
    void getFullDate() {
        assertEquals("May 21, 2005", time.getFullDate());
    }

    @Test
    void getDay() {
        assertEquals("21", time.getDay());
    }

    @Test
    void getMonth() {
        assertEquals("May", time.getMonth());
    }

    @Test
    void getYear() {
        assertEquals("2005", time.getYear());
    }

    @Test
    void getHour() {
        assertEquals("12", time.getHour());
    }

    @Test
    void get8601DateTime() {
        assertEquals("2005-05-21T12:31:00+02:00", time.get8601DateTime());
    }

    @Test
    void getMinute() {
        assertEquals("31", time.getMinute());
    }

    @Test
    void getSecond() {
        assertEquals("00", time.getSecond());
    }

    @Test
    void toCalendar() {
        Calendar calendar = time.toCalendar();

        assertEquals(4, calendar.get(Calendar.MONTH));
    }
}