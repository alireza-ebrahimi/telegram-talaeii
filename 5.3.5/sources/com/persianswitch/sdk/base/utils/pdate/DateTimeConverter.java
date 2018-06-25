package com.persianswitch.sdk.base.utils.pdate;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTimeConverter {
    private static DateTimeConverter instance;
    private Calendar mGregCalendar = GregorianCalendar.getInstance();
    private Roozh mRoozh = new Roozh();

    public static synchronized DateTimeConverter getInstance() {
        DateTimeConverter dateTimeConverter;
        synchronized (DateTimeConverter.class) {
            if (instance == null) {
                instance = new DateTimeConverter();
            }
            dateTimeConverter = instance;
        }
        return dateTimeConverter;
    }

    public synchronized DateTime gregorianToPersian(DateTime gregDate) {
        this.mRoozh.GregorianToPersian(gregDate.getYear(), gregDate.getMonth(), gregDate.getDay());
        return DateTime.getInstance(this.mRoozh.getYear(), this.mRoozh.getMonth(), this.mRoozh.getDay(), gregDate.getHour(), gregDate.getMinute(), gregDate.getSecond()).setDateFormat(DateFormat.PERSIAN);
    }

    public synchronized DateTime persianToGregorian(DateTime perDate) {
        this.mRoozh.PersianToGregorian(perDate.getYear(), perDate.getMonth(), perDate.getDay());
        return DateTime.getInstance(this.mRoozh.getYear(), this.mRoozh.getMonth(), this.mRoozh.getDay(), perDate.getHour(), perDate.getMinute(), perDate.getSecond()).setDateFormat(DateFormat.GREGORIAN);
    }

    public synchronized long toSecondSince1970(DateTime dateTime) {
        DateTime gregDateTime;
        if (dateTime.getDateFormat() == DateFormat.PERSIAN) {
            gregDateTime = persianToGregorian(dateTime);
        } else {
            gregDateTime = dateTime;
        }
        this.mGregCalendar.clear();
        this.mGregCalendar.set(gregDateTime.getYear(), gregDateTime.getMonth() - 1, gregDateTime.getDay(), gregDateTime.getHour(), gregDateTime.getMinute(), gregDateTime.getSecond());
        return this.mGregCalendar.getTimeInMillis() / 1000;
    }
}
