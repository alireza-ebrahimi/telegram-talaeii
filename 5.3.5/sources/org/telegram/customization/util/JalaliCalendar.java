package org.telegram.customization.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.telegram.ui.ChatActivity;

public class JalaliCalendar extends Calendar {
    public static final int ABAN = 7;
    public static final int AD = 1;
    public static final int AZAR = 8;
    public static final int BAHMAN = 10;
    static final int BCE = 0;
    static final int CE = 1;
    public static final int DEY = 9;
    public static final int ESFAND = 11;
    public static final int FARVARDIN = 0;
    public static final int KHORDAD = 2;
    static final int[] LEAST_MAX_VALUES = new int[]{1, 292269054, 11, 52, 4, 28, 365, 6, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000};
    static final int[] MAX_VALUES = new int[]{1, 292278994, 11, 53, 6, 31, 366, 6, 6, 1, 11, 23, 59, 59, 999, 50400000, 7200000};
    public static final int MEHR = 6;
    static final int[] MIN_VALUES = new int[]{0, 1, 0, 1, 0, 1, 1, 7, 1, 0, 0, 0, 0, 0, 0, -46800000, 0};
    public static final int MORDAD = 4;
    private static final long ONE_DAY = 86400000;
    private static final int ONE_HOUR = 3600000;
    private static final int ONE_MINUTE = 60000;
    private static final int ONE_SECOND = 1000;
    public static final int ORDIBEHESHT = 1;
    public static final int SHAHRIVAR = 5;
    public static final int TIR = 3;
    public static int[] gregorianDaysInMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static boolean isTimeSeted = false;
    public static int[] jalaliDaysInMonth = new int[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
    private static TimeZone timeZone = TimeZone.getDefault();
    private GregorianCalendar cal;

    public static class YearMonthDate {
        private int date;
        private int month;
        private int year;

        public YearMonthDate(int year, int month, int date) {
            this.year = year;
            this.month = month;
            this.date = date;
        }

        public int getYear() {
            return this.year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return this.month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDate() {
            return this.date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public String toString() {
            return getYear() + "/" + getMonth() + "/" + getDate();
        }
    }

    public JalaliCalendar() {
        this(TimeZone.getDefault(), Locale.getDefault());
    }

    public JalaliCalendar(TimeZone zone) {
        this(zone, Locale.getDefault());
    }

    public JalaliCalendar(Locale aLocale) {
        this(TimeZone.getDefault(), aLocale);
    }

    public JalaliCalendar(TimeZone zone, Locale aLocale) {
        super(zone, aLocale);
        timeZone = zone;
        Calendar calendar = Calendar.getInstance(zone, aLocale);
        YearMonthDate yearMonthDate = gregorianToJalali(new YearMonthDate(calendar.get(1), calendar.get(2), calendar.get(5)));
        set(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate());
        complete();
    }

    public JalaliCalendar(int year, int month, int dayOfMonth) {
        this(year, month, dayOfMonth, 0, 0, 0, 0);
    }

    public JalaliCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        this(year, month, dayOfMonth, hourOfDay, minute, 0, 0);
    }

    public JalaliCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
        this(year, month, dayOfMonth, hourOfDay, minute, second, 0);
    }

    JalaliCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second, int millis) {
        set(1, year);
        set(2, month);
        set(5, dayOfMonth);
        if (hourOfDay < 12 || hourOfDay > 23) {
            set(10, hourOfDay);
            set(9, 0);
        } else {
            set(9, 1);
            set(10, hourOfDay - 12);
        }
        set(11, hourOfDay);
        set(12, minute);
        set(13, second);
        set(14, millis);
        YearMonthDate yearMonthDate = jalaliToGregorian(new YearMonthDate(this.fields[1], this.fields[2], this.fields[5]));
        this.cal = new GregorianCalendar(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate(), hourOfDay, minute, second);
        this.time = this.cal.getTimeInMillis();
        isTimeSeted = true;
    }

    public static YearMonthDate gregorianToJalali(YearMonthDate gregorian) {
        if (gregorian.getMonth() > 11 || gregorian.getMonth() < -11) {
            throw new IllegalArgumentException();
        }
        int i;
        gregorian.setYear(gregorian.getYear() - 1600);
        gregorian.setDate(gregorian.getDate() - 1);
        int gregorianDayNo = (((gregorian.getYear() * 365) + ((int) Math.floor((double) ((gregorian.getYear() + 3) / 4)))) - ((int) Math.floor((double) ((gregorian.getYear() + 99) / 100)))) + ((int) Math.floor((double) ((gregorian.getYear() + 399) / ChatActivity.scheduleDownloads)));
        for (i = 0; i < gregorian.getMonth(); i++) {
            gregorianDayNo += gregorianDaysInMonth[i];
        }
        if (gregorian.getMonth() > 1 && ((gregorian.getYear() % 4 == 0 && gregorian.getYear() % 100 != 0) || gregorian.getYear() % ChatActivity.scheduleDownloads == 0)) {
            gregorianDayNo++;
        }
        int jalaliDayNo = (gregorianDayNo + gregorian.getDate()) - 79;
        jalaliDayNo %= 12053;
        int jalaliYear = ((((int) Math.floor((double) (jalaliDayNo / 12053))) * 33) + 979) + ((jalaliDayNo / 1461) * 4);
        jalaliDayNo %= 1461;
        if (jalaliDayNo >= 366) {
            jalaliYear += (int) Math.floor((double) ((jalaliDayNo - 1) / 365));
            jalaliDayNo = (jalaliDayNo - 1) % 365;
        }
        i = 0;
        while (i < 11 && jalaliDayNo >= jalaliDaysInMonth[i]) {
            jalaliDayNo -= jalaliDaysInMonth[i];
            i++;
        }
        return new YearMonthDate(jalaliYear, i, jalaliDayNo + 1);
    }

    public static YearMonthDate jalaliToGregorian(YearMonthDate jalali) {
        if (jalali.getMonth() > 11 || jalali.getMonth() < -11) {
            throw new IllegalArgumentException();
        }
        int i;
        jalali.setYear(jalali.getYear() - 979);
        jalali.setDate(jalali.getDate() - 1);
        int jalaliDayNo = ((jalali.getYear() * 365) + ((jalali.getYear() / 33) * 8)) + ((int) Math.floor((double) (((jalali.getYear() % 33) + 3) / 4)));
        for (i = 0; i < jalali.getMonth(); i++) {
            jalaliDayNo += jalaliDaysInMonth[i];
        }
        int gregorianDayNo = (jalaliDayNo + jalali.getDate()) + 79;
        int gregorianYear = (((int) Math.floor((double) (gregorianDayNo / 146097))) * ChatActivity.scheduleDownloads) + 1600;
        gregorianDayNo %= 146097;
        int leap = 1;
        if (gregorianDayNo >= 36525) {
            gregorianDayNo--;
            gregorianYear += ((int) Math.floor((double) (gregorianDayNo / 36524))) * 100;
            gregorianDayNo %= 36524;
            if (gregorianDayNo >= 365) {
                gregorianDayNo++;
            } else {
                leap = 0;
            }
        }
        gregorianYear += ((int) Math.floor((double) (gregorianDayNo / 1461))) * 4;
        gregorianDayNo %= 1461;
        if (gregorianDayNo >= 366) {
            leap = 0;
            gregorianDayNo--;
            gregorianYear += (int) Math.floor((double) (gregorianDayNo / 365));
            gregorianDayNo %= 365;
        }
        i = 0;
        while (true) {
            int i2 = gregorianDaysInMonth[i];
            int i3 = (i == 1 && leap == 1) ? i : 0;
            if (gregorianDayNo < i3 + i2) {
                return new YearMonthDate(gregorianYear, i, gregorianDayNo + 1);
            }
            i2 = gregorianDaysInMonth[i];
            if (i == 1 && leap == 1) {
                i3 = i;
            } else {
                i3 = 0;
            }
            gregorianDayNo -= i3 + i2;
            i++;
        }
    }

    public static int weekOfYear(int dayOfYear, int year) {
        switch (dayOfWeek(jalaliToGregorian(new YearMonthDate(year, 0, 1)))) {
            case 2:
                dayOfYear++;
                break;
            case 3:
                dayOfYear += 2;
                break;
            case 4:
                dayOfYear += 3;
                break;
            case 5:
                dayOfYear += 4;
                break;
            case 6:
                dayOfYear += 5;
                break;
            case 7:
                dayOfYear--;
                break;
        }
        return ((int) Math.floor((double) (dayOfYear / 7))) + 1;
    }

    public static int dayOfWeek(YearMonthDate yearMonthDate) {
        return new GregorianCalendar(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate()).get(7);
    }

    public static boolean isLeepYear(int year) {
        if (year % 33 == 1 || year % 33 == 5 || year % 33 == 9 || year % 33 == 13 || year % 33 == 17 || year % 33 == 22 || year % 33 == 26 || year % 33 == 30) {
            return true;
        }
        return false;
    }

    protected void computeTime() {
        YearMonthDate yearMonthDate;
        if (!this.isTimeSet && !isTimeSeted) {
            Calendar cal = GregorianCalendar.getInstance(timeZone);
            if (!isSet(11)) {
                super.set(11, cal.get(11));
            }
            if (!isSet(10)) {
                super.set(10, cal.get(10));
            }
            if (!isSet(12)) {
                super.set(12, cal.get(12));
            }
            if (!isSet(13)) {
                super.set(13, cal.get(13));
            }
            if (!isSet(14)) {
                super.set(14, cal.get(14));
            }
            if (!isSet(15)) {
                super.set(15, cal.get(15));
            }
            if (!isSet(16)) {
                super.set(16, cal.get(16));
            }
            if (!isSet(9)) {
                super.set(9, cal.get(9));
            }
            if (internalGet(11) < 12 || internalGet(11) > 23) {
                super.set(10, internalGet(11));
                super.set(9, 0);
            } else {
                super.set(9, 1);
                super.set(10, internalGet(11) - 12);
            }
            yearMonthDate = jalaliToGregorian(new YearMonthDate(internalGet(1), internalGet(2), internalGet(5)));
            cal.set(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate(), internalGet(11), internalGet(12), internalGet(13));
            this.time = cal.getTimeInMillis();
        } else if (!this.isTimeSet && isTimeSeted) {
            if (internalGet(11) < 12 || internalGet(11) > 23) {
                super.set(10, internalGet(11));
                super.set(9, 0);
            } else {
                super.set(9, 1);
                super.set(10, internalGet(11) - 12);
            }
            this.cal = new GregorianCalendar();
            super.set(15, timeZone.getRawOffset());
            super.set(16, timeZone.getDSTSavings());
            yearMonthDate = jalaliToGregorian(new YearMonthDate(internalGet(1), internalGet(2), internalGet(5)));
            this.cal.set(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate(), internalGet(11), internalGet(12), internalGet(13));
            this.time = this.cal.getTimeInMillis();
        }
    }

    public void set(int field, int value) {
        switch (field) {
            case 2:
                if (value > 11) {
                    super.set(field, 11);
                    add(field, value - 11);
                    return;
                } else if (value < 0) {
                    super.set(field, 0);
                    add(field, value);
                    return;
                } else {
                    super.set(field, value);
                    return;
                }
            case 3:
                if (isSet(1) && isSet(2) && isSet(5)) {
                    add(field, value - get(3));
                    return;
                } else {
                    super.set(field, value);
                    return;
                }
            case 4:
                if (isSet(1) && isSet(2) && isSet(5)) {
                    add(field, value - get(4));
                    return;
                } else {
                    super.set(field, value);
                    return;
                }
            case 5:
                super.set(field, 0);
                add(field, value);
                return;
            case 6:
                if (isSet(1) && isSet(2) && isSet(5)) {
                    super.set(1, internalGet(1));
                    super.set(2, 0);
                    super.set(5, 0);
                    add(field, value);
                    return;
                }
                super.set(field, value);
                return;
            case 7:
                if (isSet(1) && isSet(2) && isSet(5)) {
                    add(7, (value % 7) - get(7));
                    return;
                } else {
                    super.set(field, value);
                    return;
                }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                if (isSet(1) && isSet(2) && isSet(5) && isSet(10) && isSet(11) && isSet(12) && isSet(13) && isSet(14)) {
                    this.cal = new GregorianCalendar();
                    YearMonthDate yearMonthDate = jalaliToGregorian(new YearMonthDate(internalGet(1), internalGet(2), internalGet(5)));
                    this.cal.set(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate(), internalGet(11), internalGet(12), internalGet(13));
                    this.cal.set(field, value);
                    yearMonthDate = gregorianToJalali(new YearMonthDate(this.cal.get(1), this.cal.get(2), this.cal.get(5)));
                    super.set(1, yearMonthDate.getYear());
                    super.set(2, yearMonthDate.getMonth());
                    super.set(5, yearMonthDate.getDate());
                    super.set(11, this.cal.get(11));
                    super.set(12, this.cal.get(12));
                    super.set(13, this.cal.get(13));
                    return;
                }
                super.set(field, value);
                return;
            default:
                super.set(field, value);
                return;
        }
    }

    protected void computeFields() {
        boolean temp = this.isTimeSet;
        if (!this.areFieldsSet) {
            setMinimalDaysInFirstWeek(1);
            setFirstDayOfWeek(7);
            int dayOfYear = 0;
            for (int index = 0; index < this.fields[2]; index++) {
                dayOfYear += jalaliDaysInMonth[index];
            }
            super.set(6, dayOfYear + this.fields[5]);
            super.set(7, dayOfWeek(jalaliToGregorian(new YearMonthDate(this.fields[1], this.fields[2], this.fields[5]))));
            if (this.fields[5] > 0 && this.fields[5] < 8) {
                super.set(8, 1);
            }
            if (7 < this.fields[5] && this.fields[5] < 15) {
                super.set(8, 2);
            }
            if (14 < this.fields[5] && this.fields[5] < 22) {
                super.set(8, 3);
            }
            if (21 < this.fields[5] && this.fields[5] < 29) {
                super.set(8, 4);
            }
            if (28 < this.fields[5] && this.fields[5] < 32) {
                super.set(8, 5);
            }
            super.set(3, weekOfYear(this.fields[6], this.fields[1]));
            super.set(4, (weekOfYear(this.fields[6], this.fields[1]) - weekOfYear(this.fields[6] - this.fields[5], this.fields[1])) + 1);
            this.isTimeSet = temp;
        }
    }

    public void add(int field, int amount) {
        if (field == 2) {
            amount += get(2);
            add(1, amount / 12);
            super.set(2, amount % 12);
            if (get(5) > jalaliDaysInMonth[amount % 12]) {
                super.set(5, jalaliDaysInMonth[amount % 12]);
                if (get(2) == 11 && isLeepYear(get(1))) {
                    super.set(5, 30);
                }
            }
            complete();
        } else if (field == 1) {
            super.set(1, get(1) + amount);
            if (get(5) == 30 && get(2) == 11 && !isLeepYear(get(1))) {
                super.set(5, 29);
            }
            complete();
        } else {
            YearMonthDate yearMonthDate = jalaliToGregorian(new YearMonthDate(get(1), get(2), get(5)));
            Calendar gc = new GregorianCalendar(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate(), get(11), get(12), get(13));
            gc.add(field, amount);
            yearMonthDate = gregorianToJalali(new YearMonthDate(gc.get(1), gc.get(2), gc.get(5)));
            super.set(1, yearMonthDate.getYear());
            super.set(2, yearMonthDate.getMonth());
            super.set(5, yearMonthDate.getDate());
            super.set(11, gc.get(11));
            super.set(12, gc.get(12));
            super.set(13, gc.get(13));
            complete();
        }
    }

    public void roll(int field, boolean up) {
        roll(field, up ? 1 : -1);
    }

    public void roll(int field, int amount) {
        if (amount != 0) {
            if (field < 0 || field >= 15) {
                throw new IllegalArgumentException();
            }
            complete();
            int i;
            int[] iArr;
            switch (field) {
                case 1:
                    super.set(1, internalGet(1) + amount);
                    if (internalGet(2) == 11 && internalGet(5) == 30 && !isLeepYear(internalGet(1))) {
                        super.set(5, 29);
                        return;
                    }
                    return;
                case 2:
                    break;
                case 3:
                    return;
                case 5:
                    i = 0;
                    if (get(2) >= 0 && get(2) <= 5) {
                        i = 31;
                    }
                    if (6 <= get(2) && get(2) <= 10) {
                        i = 30;
                    }
                    if (get(2) == 11) {
                        if (isLeepYear(get(1))) {
                            i = 30;
                        } else {
                            i = 29;
                        }
                    }
                    int d = (get(5) + amount) % i;
                    if (d < 0) {
                        d += i;
                    }
                    super.set(5, d);
                    return;
                case 6:
                    i = isLeepYear(internalGet(1)) ? 366 : 365;
                    int dayOfYear = (internalGet(6) + amount) % i;
                    if (dayOfYear <= 0) {
                        dayOfYear += i;
                    }
                    int temp = 0;
                    int month = 0;
                    while (dayOfYear > temp) {
                        temp += jalaliDaysInMonth[month];
                        month++;
                    }
                    super.set(2, month - 1);
                    super.set(5, jalaliDaysInMonth[internalGet(2)] - (temp - dayOfYear));
                    return;
                case 7:
                    int index = amount % 7;
                    if (index < 0) {
                        index += 7;
                    }
                    for (int i2 = 0; i2 != index; i2++) {
                        if (internalGet(7) == 6) {
                            add(5, -6);
                        } else {
                            add(5, 1);
                        }
                    }
                    return;
                case 9:
                    if (amount % 2 != 0) {
                        if (internalGet(9) == 0) {
                            this.fields[9] = 1;
                        } else {
                            this.fields[9] = 0;
                        }
                        if (get(9) == 0) {
                            super.set(11, get(10));
                            return;
                        } else {
                            super.set(11, get(10) + 12);
                            return;
                        }
                    }
                    return;
                case 10:
                    super.set(10, (internalGet(10) + amount) % 12);
                    if (internalGet(10) < 0) {
                        iArr = this.fields;
                        iArr[10] = iArr[10] + 12;
                    }
                    if (internalGet(9) == 0) {
                        super.set(11, internalGet(10));
                        return;
                    } else {
                        super.set(11, internalGet(10) + 12);
                        return;
                    }
                case 11:
                    this.fields[11] = (internalGet(11) + amount) % 24;
                    if (internalGet(11) < 0) {
                        iArr = this.fields;
                        iArr[11] = iArr[11] + 24;
                    }
                    if (internalGet(11) >= 12) {
                        this.fields[9] = 1;
                        this.fields[10] = internalGet(11) - 12;
                        break;
                    }
                    this.fields[9] = 0;
                    this.fields[10] = internalGet(11);
                    break;
                case 12:
                    int m = (internalGet(12) + amount) % 60;
                    if (m < 0) {
                        m += 60;
                    }
                    super.set(12, m);
                    return;
                case 13:
                    int s = (internalGet(13) + amount) % 60;
                    if (s < 0) {
                        s += 60;
                    }
                    super.set(13, s);
                    return;
                case 14:
                    int ms = (internalGet(14) + amount) % 1000;
                    if (ms < 0) {
                        ms += 1000;
                    }
                    super.set(14, ms);
                    return;
                default:
                    throw new IllegalArgumentException();
            }
            int mon = (internalGet(2) + amount) % 12;
            if (mon < 0) {
                mon += 12;
            }
            super.set(2, mon);
            int monthLen = jalaliDaysInMonth[mon];
            if (internalGet(2) == 11 && isLeepYear(internalGet(1))) {
                monthLen = 30;
            }
            if (internalGet(5) > monthLen) {
                super.set(5, monthLen);
            }
        }
    }

    public int getMinimum(int field) {
        return MIN_VALUES[field];
    }

    public int getMaximum(int field) {
        return MAX_VALUES[field];
    }

    public int getGreatestMinimum(int field) {
        return MIN_VALUES[field];
    }

    public int getLeastMaximum(int field) {
        return LEAST_MAX_VALUES[field];
    }
}
