package io.github.andruid929.leutils.time;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Utility class for managing and formatting time-related operations.
 * Provides methods for accessing and converting time in different formats
 * and standards using the epoch time as a base.
 *
 * @author Andrew Jones
 * @since 2.1.0
 */
public class TimeUtil {

    /**
     * Default time format pattern (24-hour format with seconds).
     * Example: {@code 13:14:01}
     */

    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Default date format pattern showing full month name, day, and year.
     * Example: {@code 2025 05, 2026}
     */

    public static final String DATE_FORMAT = "MMMM dd, uuuu";

    /**
     * The captured timestamp in epoch milliseconds.
     * This value is immutable and represents the instant when this TimeUtil instance was created.
     */

    private final long nowInstant;

    /**
     * Package-private constructor that captures the current system time.
     * Use {@link #captureInstant()} to create instances of this class.
     */
    TimeUtil() {
        nowInstant = Instant.now().toEpochMilli();
    }

    /**
     * Returns the captured timestamp in epoch milliseconds.
     *
     * @return the number of milliseconds since the Unix epoch (January 1, 1970, 00:00:00 UTC).
     */
    public long getEpochMillis() {
        return nowInstant;
    }

    /**
     * Returns the captured timestamp in epoch seconds.
     *
     * @return the number of seconds since the Unix epoch (January 1, 1970, 00:00:00 UTC).
     */
    public long getEpochSecond() {
        return Instant.ofEpochMilli(getEpochMillis()).getEpochSecond();
    }

    /**
     * Formats the captured time according to the specified pattern using UTC timezone.
     *
     * @param timeFormat the pattern to format the time (e.g. "HH:mm:ss", "yyyy-MM-dd").
     * @return the formatted time string in the UTC timezone.
     */
    public String getTime(String timeFormat) {
        return getTime(timeFormat, ZoneId.systemDefault());
    }

    /**
     * Formats the captured time according to the specified pattern and timezone.
     *
     * @param timeFormat the pattern to format the time (e.g. "HH:mm:ss", "yyyy-MM-dd").
     * @param zoneId     the timezone to use for formatting.
     * @return the formatted time string in the specified timezone.
     */
    public String getTime(String timeFormat, ZoneId zoneId) {
        ZonedDateTime instant = toInstant().atZone(zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);

        return instant.format(formatter);
    }

    /**
     * Returns the time in the {@link #TIME_FORMAT default format} in the UTC timezone.
     *
     * @return the time formatted as HH:mm:ss.
     */
    public String getActualTime() {
        return getTime(TIME_FORMAT, ZoneId.systemDefault());
    }

    /**
     * Returns the full date in the {@link #DATE_FORMAT default format} in the UTC timezone.
     *
     * @return the date formatted as "Month day, Year".
     */
    public String getFullDate() {
        return getTime(DATE_FORMAT, ZoneId.systemDefault());
    }

    /**
     * Returns the day of the month as a two-digit string.
     *
     * @return the day (from 01 to 31).
     */
    public String getDay() {
        return getTime("dd");
    }

    /**
     * Returns the full month name.
     *
     * @return the month name (e.g. "January", "February").
     */
    public String getMonth() {
        return getTime("MMMM");
    }

    /**
     * Returns the year as a four-digit string.
     *
     * @return the year (e.g. "2024").
     */
    public String getYear() {
        return getTime("uuuu");
    }

    /**
     * Returns the hour in 24-hour format as a two-digit string.
     *
     * @return the hour (from 00 to 23).
     */
    public String getHour() {
        return getTime("HH");
    }

    /**
     * Returns the minute as a two-digit string.
     *
     * @return the minute (from 00 to 59).
     */
    public String getMinute() {
        return getTime("mm");
    }

    /**
     * Returns the second as a two-digit string.
     *
     * @return the second (from 00 to 59).
     */
    public String getSecond() {
        return getTime("ss");
    }

    /**
     * Returns the captured timestamp as an ISO-8601 formatted date-time string.
     * The output includes the date, time, and offset from UTC (e.g. "2023-03-15T10:15:30+01:00").
     *
     * @return a string representation of the captured timestamp in ISO-8601 format.
     */
    public String get8601DateTime() {
        return toZonedDateTime(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    /**
     * Converts the captured timestamp to a {@link Calendar} object.
     *
     * @return a Calendar instance set to the captured timestamp.
     */
    public Calendar toCalendar() {
        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.systemDefault());
        
        Calendar calendar = Calendar.getInstance(timeZone);

        calendar.setTimeInMillis(getEpochMillis());

        return calendar;
    }

    /**
     * Converts the captured timestamp to an {@link Instant} object.
     *
     * @return an Instant representing the captured moment in time.
     */
    public Instant toInstant() {
        return Instant.ofEpochMilli(getEpochMillis());
    }

    /**
     * Converts the captured timestamp to a {@link LocalDateTime} object in the specified timezone.
     *
     * @param zoneId the timezone to use for the conversion.
     * @return a LocalDateTime representing the captured moment in the specified timezone.
     */
    public LocalDateTime toLocalDateTime(ZoneId zoneId) {
        return LocalDateTime.ofInstant(toInstant(), zoneId);
    }

    /**
     * Converts the captured timestamp to a {@link ZonedDateTime} object in the specified timezone.
     *
     * @param zoneId the timezone to use for the conversion.
     * @return a ZonedDateTime representing the captured moment in the specified timezone.
     */
    public ZonedDateTime toZonedDateTime(ZoneId zoneId) {
        return toInstant().atZone(zoneId);
    }

    /**
     * Creates a new TimeUtil instance capturing the current moment in time.
     * This is the factory method for creating TimeUtil instances.
     *
     * @return a new TimeUtil instance with the current timestamp.
     */
    @Contract(" -> new")
    public static @NotNull TimeUtil captureInstant() {
        return new TimeUtil();
    }

    /**
     * Parses a given ISO-8601 formatted time string into a {@link TimeUtil} instance.
     * The provided string must conform to the {@link DateTimeFormatter#ISO_OFFSET_DATE_TIME} standard.
     *
     * @param timeString the ISO-8601 formatted time string to be parsed, e.g. "2023-03-15T10:15:30+01:00".
     * @return a {@link TimeUtil} instance representing the parsed time.
     * @throws DateTimeParseException if the string cannot be parsed as a valid ISO-8601 date-time.
     * @throws NullPointerException if the provided time string is null.
     */
    public static @NotNull TimeUtil parseFromString(String timeString) {
        ZonedDateTime time = ZonedDateTime.parse(timeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        return new TimeUtil() {
            @Override
            public long getEpochMillis() {
                return time.toInstant().toEpochMilli();
            }
        };
    }

    /**
     * Returns the current system time in epoch milliseconds.
     * This is a static utility method that does not create a TimeUtil instance.
     *
     * @return the current time in milliseconds since the Unix epoch.
     */
    public static long getNow() {
        return Instant.now().toEpochMilli();
    }

    @Override
    public String toString() {
        return "TimeUtil[" + getEpochMillis() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        TimeUtil timeUtil = (TimeUtil) o;

        return nowInstant == timeUtil.nowInstant;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(nowInstant);
    }
}
