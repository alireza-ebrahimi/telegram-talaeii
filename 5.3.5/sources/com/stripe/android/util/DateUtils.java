package com.stripe.android.util;

import com.stripe.android.time.Clock;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {
    public static boolean hasYearPassed(int year) {
        if (normalizeYear(year) < Clock.getCalendarInstance().get(1)) {
            return true;
        }
        return false;
    }

    public static boolean hasMonthPassed(int year, int month) {
        if (hasYearPassed(year)) {
            return true;
        }
        Calendar now = Clock.getCalendarInstance();
        if (normalizeYear(year) != now.get(1) || month >= now.get(2) + 1) {
            return false;
        }
        return true;
    }

    private static int normalizeYear(int year) {
        if (year >= 100 || year < 0) {
            return year;
        }
        String currentYear = String.valueOf(Clock.getCalendarInstance().get(1));
        String prefix = currentYear.substring(0, currentYear.length() - 2);
        return Integer.parseInt(String.format(Locale.US, "%s%02d", new Object[]{prefix, Integer.valueOf(year)}));
    }
}
