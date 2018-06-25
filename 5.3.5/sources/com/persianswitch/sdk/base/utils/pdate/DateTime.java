package com.persianswitch.sdk.base.utils.pdate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class DateTime {
    private final DateFormat dateFormat;
    private final int day;
    private final int hour;
    private final int minute;
    private final int month;
    private final int second;
    private final int year;

    private DateTime(int year, int month, int day, int hour, int minute, int second, DateFormat dateFormat) {
        this.year = year;
        this.day = day;
        this.month = month;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.dateFormat = dateFormat;
    }

    public static DateTime getInstance(int year, int month, int day) {
        return new DateTime(year, month, day, 0, 0, 0, DateFormat.NOT_SPECIFY);
    }

    public static DateTime getInstance(int year, int month, int day, int hour, int minute) {
        return new DateTime(year, month, day, hour, minute, 0, DateFormat.NOT_SPECIFY);
    }

    public static DateTime getInstance(int year, int month, int day, int hour, int minute, int sec) {
        return new DateTime(year, month, day, hour, minute, sec, DateFormat.NOT_SPECIFY);
    }

    public static DateTime getInstance(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return getInstance(calendar.get(1), calendar.get(2) + 1, calendar.get(5), calendar.get(10), calendar.get(12), calendar.get(13)).setDateFormat(DateFormat.GREGORIAN);
    }

    public static DateTime now() {
        return getInstance(new Date());
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateTime time = (DateTime) o;
        if (this.year != time.year || this.month != time.month || this.day != time.day || this.hour != time.hour || this.minute != time.minute) {
            return false;
        }
        if (this.second != time.second) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((((((((this.year * 31) + this.month) * 31) + this.day) * 31) + this.hour) * 31) + this.minute) * 31) + this.second;
    }

    public String toString() {
        return String.format(Locale.US, "DateTime{%04d/%02d/%02d %02d:%02d:%02d}", new Object[]{Integer.valueOf(this.year), Integer.valueOf(this.month), Integer.valueOf(this.day), Integer.valueOf(this.hour), Integer.valueOf(this.minute), Integer.valueOf(this.second)});
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    public String getMonthName() {
        if (this.dateFormat == DateFormat.PERSIAN) {
            return CalendarConstants.PERSIAN_MONTH_NAMES[Math.abs(getMonth() - 1) % 12];
        }
        return CalendarConstants.GREGORIAN_MONTH_NAME[Math.abs(getMonth() - 1) % 12];
    }

    public DateFormat getDateFormat() {
        return this.dateFormat;
    }

    public DateTime setDateFormat(DateFormat newDateFormat) {
        return new DateTime(this.year, this.month, this.day, this.hour, this.minute, this.second, newDateFormat);
    }
}
