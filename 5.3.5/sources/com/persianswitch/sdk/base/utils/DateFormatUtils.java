package com.persianswitch.sdk.base.utils;

import com.persianswitch.sdk.base.utils.pdate.CalendarConstants;
import com.persianswitch.sdk.base.utils.pdate.DateTime;
import com.persianswitch.sdk.base.utils.pdate.DateTimeConverter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateFormatUtils {
    public static String shortDate(Date date, boolean toPersian) {
        if (date == null) {
            return "";
        }
        DateTime dateTime;
        if (toPersian) {
            dateTime = DateTimeConverter.getInstance().gregorianToPersian(DateTime.getInstance(date));
        } else {
            dateTime = DateTime.getInstance(date);
        }
        return String.format(Locale.US, "%04d/%02d/%02d", new Object[]{Integer.valueOf(dateTime.getYear()), Integer.valueOf(dateTime.getMonth()), Integer.valueOf(dateTime.getDay())});
    }

    public static String shortTime(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("HH:mm:ss", Locale.US).format(date);
    }

    public static String longDateTime(Date date, boolean toPersian) {
        if (date == null) {
            return "";
        }
        DateTime dateTime;
        if (toPersian) {
            dateTime = DateTimeConverter.getInstance().gregorianToPersian(DateTime.getInstance(date));
        } else {
            dateTime = DateTime.getInstance(date);
        }
        int year = dateTime.getYear();
        int month = dateTime.getMonth();
        int day = dateTime.getDay();
        String monthName;
        if (toPersian) {
            monthName = CalendarConstants.PERSIAN_MONTH_NAMES[month - 1];
        } else {
            monthName = new SimpleDateFormat("MMMM", Locale.US).format(date);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        int hour = calendar.get(11);
        int minute = calendar.get(12);
        return String.format(Locale.US, "%04d/%02d/%02d %02d:%02d", new Object[]{Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day), Integer.valueOf(hour), Integer.valueOf(minute)});
    }
}
