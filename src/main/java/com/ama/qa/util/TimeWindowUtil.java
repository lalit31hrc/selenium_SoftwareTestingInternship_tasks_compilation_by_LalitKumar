package com.ama.qa.util;

import java.time.LocalTime;

/**
 * Utility to check whether the current system time falls within
 * allowed test execution windows:
 * - 3:00 PM to 6:00 PM (15:00 - 18:00)
 * - 6:00 PM to 7:00 PM (18:00 - 19:00)
 */
public class TimeWindowUtil {

    private static final LocalTime WINDOW_START_3PM = LocalTime.of(15, 0); // 3:00 PM (15:00)
    private static final LocalTime WINDOW_END_6PM   = LocalTime.of(18, 0); // 6:00 PM (18:00)
    private static final LocalTime WINDOW_END_7PM   = LocalTime.of(19, 0); // 7:00 PM (19:00)

    /**
     * Checks if system time is strictly between 3:00 PM and 6:00 PM.
     */
    public static boolean isWithin3To6PMWindow() {
        LocalTime now = LocalTime.now();
        return !now.isBefore(WINDOW_START_3PM) && !now.isAfter(WINDOW_END_6PM);
    }

    /**
     * Checks if system time is strictly between 6:00 PM and 7:00 PM.
     */
    public static boolean isWithin6To7PMWindow() {
        LocalTime now = LocalTime.now();
        return !now.isBefore(WINDOW_END_6PM) && !now.isAfter(WINDOW_END_7PM);
    }

    /**
     * General execution window check (3:00 PM to 6:00 PM default).
     */
    public static boolean isWithinAllowedWindow() {
        return isWithin3To6PMWindow();
    }
}