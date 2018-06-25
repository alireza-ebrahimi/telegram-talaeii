package com.persianswitch.sdk.base.utils.pdate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class DateTime {
    /* renamed from: a */
    private final int f7113a;
    /* renamed from: b */
    private final int f7114b;
    /* renamed from: c */
    private final int f7115c;
    /* renamed from: d */
    private final int f7116d;
    /* renamed from: e */
    private final int f7117e;
    /* renamed from: f */
    private final int f7118f;
    /* renamed from: g */
    private final DateFormat f7119g;

    private DateTime(int i, int i2, int i3, int i4, int i5, int i6, DateFormat dateFormat) {
        this.f7113a = i;
        this.f7115c = i3;
        this.f7114b = i2;
        this.f7116d = i4;
        this.f7117e = i5;
        this.f7118f = i6;
        this.f7119g = dateFormat;
    }

    /* renamed from: a */
    public static DateTime m10777a(int i, int i2, int i3, int i4, int i5, int i6) {
        return new DateTime(i, i2, i3, i4, i5, i6, DateFormat.NOT_SPECIFY);
    }

    /* renamed from: a */
    public static DateTime m10778a(Date date) {
        Calendar instance = GregorianCalendar.getInstance();
        instance.setTime(date);
        return m10777a(instance.get(1), instance.get(2) + 1, instance.get(5), instance.get(10), instance.get(12), instance.get(13)).m10780a(DateFormat.GREGORIAN);
    }

    /* renamed from: a */
    public int m10779a() {
        return this.f7113a;
    }

    /* renamed from: a */
    public DateTime m10780a(DateFormat dateFormat) {
        return new DateTime(this.f7113a, this.f7114b, this.f7115c, this.f7116d, this.f7117e, this.f7118f, dateFormat);
    }

    /* renamed from: b */
    public int m10781b() {
        return this.f7114b;
    }

    /* renamed from: c */
    public int m10782c() {
        return this.f7115c;
    }

    /* renamed from: d */
    public int m10783d() {
        return this.f7116d;
    }

    /* renamed from: e */
    public int m10784e() {
        return this.f7117e;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DateTime dateTime = (DateTime) obj;
        if (this.f7113a != dateTime.f7113a || this.f7114b != dateTime.f7114b || this.f7115c != dateTime.f7115c || this.f7116d != dateTime.f7116d || this.f7117e != dateTime.f7117e) {
            return false;
        }
        if (this.f7118f != dateTime.f7118f) {
            z = false;
        }
        return z;
    }

    /* renamed from: f */
    public int m10785f() {
        return this.f7118f;
    }

    public int hashCode() {
        return (((((((((this.f7113a * 31) + this.f7114b) * 31) + this.f7115c) * 31) + this.f7116d) * 31) + this.f7117e) * 31) + this.f7118f;
    }

    public String toString() {
        return String.format(Locale.US, "DateTime{%04d/%02d/%02d %02d:%02d:%02d}", new Object[]{Integer.valueOf(this.f7113a), Integer.valueOf(this.f7114b), Integer.valueOf(this.f7115c), Integer.valueOf(this.f7116d), Integer.valueOf(this.f7117e), Integer.valueOf(this.f7118f)});
    }
}
