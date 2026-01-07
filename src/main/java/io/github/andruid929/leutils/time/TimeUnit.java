package io.github.andruid929.leutils.time;

/**
 * Enum representing common time units with their string suffixes and millisecond conversion values.
 * <p>
 * This enum provides a convenient way to work with different time units and convert between them
 * using milliseconds as the base unit.
 * </p>
 *
 * @author Andrew Jones
 * @since 2.2.0
 */

public enum TimeUnit {

    /**
     * Milliseconds time unit with suffix "ms" and value of 1 millisecond.
     */
    MILLISECONDS("ms", 1),

    /**
     * Seconds time unit with suffix "s" and value of 1,000 milliseconds.
     */
    SECONDS("s", 1_000),

    /**
     * Minutes time unit with suffix "m" and value of 60,000 milliseconds.
     */
    MINUTES("m", 60_000),

    /**
     * Hours time unit with suffix "h" and value of 3.6 million milliseconds.
     */
    HOURS("h", 3_600_000),

    /**
     * Days time unit with suffix "d" and value of 86.4 million milliseconds.
     */
    DAYS("d", 86_400_000);

    /**
     * The string suffix representing this time unit (e.g. "ms", "s", "m").
     */
    private final String unitSuffix;

    /**
     * The value of this time unit expressed in milliseconds.
     */
    private final long inMillis;

    /**
     * Constructs a TimeUnit with the specified suffix and millisecond value.
     *
     * @param unitSuffix the string suffix for this time unit
     * @param inMillis   the value of this time unit in milliseconds
     */
    TimeUnit(String unitSuffix, long inMillis) {
        this.unitSuffix = unitSuffix;
        this.inMillis = inMillis;
    }

    /**
     * Returns the value of this time unit in milliseconds.
     *
     * @return the millisecond value of this time unit
     */
    public long getInMillis() {
        return inMillis;
    }

    /**
     * Returns the string suffix representing this time unit.
     *
     * @return the unit suffix (e.g. "ms", "s", "m", "h", "d")
     */
    public String getUnitSuffix() {
        return unitSuffix;
    }
}
