package org.telegram.customization.util;

import com.google.android.gms.common.util.GmsVersion;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.telegram.ui.ChatActivity;

/* renamed from: org.telegram.customization.util.g */
public class C2881g extends Calendar {
    /* renamed from: a */
    public static int[] f9503a = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    /* renamed from: b */
    public static int[] f9504b = new int[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
    /* renamed from: c */
    static final int[] f9505c = new int[]{0, 1, 0, 1, 0, 1, 1, 7, 1, 0, 0, 0, 0, 0, 0, -46800000, 0};
    /* renamed from: d */
    static final int[] f9506d = new int[]{1, 292269054, 11, 52, 4, 28, 365, 6, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000};
    /* renamed from: e */
    static final int[] f9507e = new int[]{1, 292278994, 11, 53, 6, 31, 366, 6, 6, 1, 11, 23, 59, 59, 999, 50400000, GmsVersion.VERSION_PARMESAN};
    /* renamed from: f */
    private static TimeZone f9508f = TimeZone.getDefault();
    /* renamed from: g */
    private static boolean f9509g = false;
    /* renamed from: h */
    private GregorianCalendar f9510h;

    /* renamed from: org.telegram.customization.util.g$a */
    public static class C2880a {
        /* renamed from: a */
        private int f9500a;
        /* renamed from: b */
        private int f9501b;
        /* renamed from: c */
        private int f9502c;

        public C2880a(int i, int i2, int i3) {
            this.f9500a = i;
            this.f9501b = i2;
            this.f9502c = i3;
        }

        /* renamed from: a */
        public int m13358a() {
            return this.f9500a;
        }

        /* renamed from: a */
        public void m13359a(int i) {
            this.f9500a = i;
        }

        /* renamed from: b */
        public int m13360b() {
            return this.f9501b;
        }

        /* renamed from: b */
        public void m13361b(int i) {
            this.f9502c = i;
        }

        /* renamed from: c */
        public int m13362c() {
            return this.f9502c;
        }

        public String toString() {
            return m13358a() + "/" + m13360b() + "/" + m13362c();
        }
    }

    /* renamed from: a */
    public static int m13363a(int i, int i2) {
        switch (C2881g.m13367c(C2881g.m13366b(new C2880a(i2, 0, 1)))) {
            case 2:
                i++;
                break;
            case 3:
                i += 2;
                break;
            case 4:
                i += 3;
                break;
            case 5:
                i += 4;
                break;
            case 6:
                i += 5;
                break;
            case 7:
                i--;
                break;
        }
        return ((int) Math.floor((double) (i / 7))) + 1;
    }

    /* renamed from: a */
    public static C2880a m13364a(C2880a c2880a) {
        int i = 0;
        if (c2880a.m13360b() > 11 || c2880a.m13360b() < -11) {
            throw new IllegalArgumentException();
        }
        int i2;
        c2880a.m13359a(c2880a.m13358a() - 1600);
        c2880a.m13361b(c2880a.m13362c() - 1);
        int a = (((c2880a.m13358a() * 365) + ((int) Math.floor((double) ((c2880a.m13358a() + 3) / 4)))) - ((int) Math.floor((double) ((c2880a.m13358a() + 99) / 100)))) + ((int) Math.floor((double) ((c2880a.m13358a() + 399) / ChatActivity.scheduleDownloads)));
        for (i2 = 0; i2 < c2880a.m13360b(); i2++) {
            a += f9503a[i2];
        }
        if (c2880a.m13360b() > 1 && ((c2880a.m13358a() % 4 == 0 && c2880a.m13358a() % 100 != 0) || c2880a.m13358a() % ChatActivity.scheduleDownloads == 0)) {
            a++;
        }
        i2 = (c2880a.m13362c() + a) - 79;
        i2 %= 12053;
        a = ((((int) Math.floor((double) (i2 / 12053))) * 33) + 979) + ((i2 / 1461) * 4);
        i2 %= 1461;
        if (i2 >= 366) {
            a += (int) Math.floor((double) ((i2 - 1) / 365));
            i2 = (i2 - 1) % 365;
        }
        while (i < 11 && i2 >= f9504b[i]) {
            i2 -= f9504b[i];
            i++;
        }
        return new C2880a(a, i, i2 + 1);
    }

    /* renamed from: a */
    public static boolean m13365a(int i) {
        return i % 33 == 1 || i % 33 == 5 || i % 33 == 9 || i % 33 == 13 || i % 33 == 17 || i % 33 == 22 || i % 33 == 26 || i % 33 == 30;
    }

    /* renamed from: b */
    public static C2880a m13366b(C2880a c2880a) {
        if (c2880a.m13360b() > 11 || c2880a.m13360b() < -11) {
            throw new IllegalArgumentException();
        }
        int i;
        int i2;
        Object obj;
        c2880a.m13359a(c2880a.m13358a() - 979);
        c2880a.m13361b(c2880a.m13362c() - 1);
        int a = ((c2880a.m13358a() * 365) + ((c2880a.m13358a() / 33) * 8)) + ((int) Math.floor((double) (((c2880a.m13358a() % 33) + 3) / 4)));
        for (i = 0; i < c2880a.m13360b(); i++) {
            a += f9504b[i];
        }
        i = (c2880a.m13362c() + a) + 79;
        a = (((int) Math.floor((double) (i / 146097))) * ChatActivity.scheduleDownloads) + 1600;
        i %= 146097;
        if (i >= 36525) {
            i--;
            a += ((int) Math.floor((double) (i / 36524))) * 100;
            i %= 36524;
            if (i >= 365) {
                i2 = a;
                a = i + 1;
                obj = 1;
            } else {
                i2 = a;
                a = i;
                obj = null;
            }
        } else {
            i2 = a;
            a = i;
            i = 1;
        }
        i2 += ((int) Math.floor((double) (a / 1461))) * 4;
        a %= 1461;
        if (a >= 366) {
            i = a - 1;
            i2 = ((int) Math.floor((double) (i / 365))) + i2;
            a = i % 365;
            obj = null;
        }
        int i3 = a;
        a = 0;
        while (true) {
            int i4 = f9503a[a];
            int i5 = (a == 1 && obj == 1) ? a : 0;
            if (i3 < i5 + i4) {
                return new C2880a(i2, a, i3 + 1);
            }
            i4 = f9503a[a];
            i5 = (a == 1 && obj == 1) ? a : 0;
            i3 -= i5 + i4;
            a++;
        }
    }

    /* renamed from: c */
    public static int m13367c(C2880a c2880a) {
        return new GregorianCalendar(c2880a.m13358a(), c2880a.m13360b(), c2880a.m13362c()).get(7);
    }

    public void add(int i, int i2) {
        if (i == 2) {
            int i3 = get(2) + i2;
            add(1, i3 / 12);
            super.set(2, i3 % 12);
            if (get(5) > f9504b[i3 % 12]) {
                super.set(5, f9504b[i3 % 12]);
                if (get(2) == 11 && C2881g.m13365a(get(1))) {
                    super.set(5, 30);
                }
            }
            complete();
        } else if (i == 1) {
            super.set(1, get(1) + i2);
            if (get(5) == 30 && get(2) == 11 && !C2881g.m13365a(get(1))) {
                super.set(5, 29);
            }
            complete();
        } else {
            C2880a b = C2881g.m13366b(new C2880a(get(1), get(2), get(5)));
            Calendar gregorianCalendar = new GregorianCalendar(b.m13358a(), b.m13360b(), b.m13362c(), get(11), get(12), get(13));
            gregorianCalendar.add(i, i2);
            C2880a a = C2881g.m13364a(new C2880a(gregorianCalendar.get(1), gregorianCalendar.get(2), gregorianCalendar.get(5)));
            super.set(1, a.m13358a());
            super.set(2, a.m13360b());
            super.set(5, a.m13362c());
            super.set(11, gregorianCalendar.get(11));
            super.set(12, gregorianCalendar.get(12));
            super.set(13, gregorianCalendar.get(13));
            complete();
        }
    }

    protected void computeFields() {
        boolean z = this.isTimeSet;
        if (!this.areFieldsSet) {
            setMinimalDaysInFirstWeek(1);
            setFirstDayOfWeek(7);
            int i = 0;
            for (int i2 = 0; i2 < this.fields[2]; i2++) {
                i = f9504b[i2] + i;
            }
            super.set(6, this.fields[5] + i);
            super.set(7, C2881g.m13367c(C2881g.m13366b(new C2880a(this.fields[1], this.fields[2], this.fields[5]))));
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
            super.set(3, C2881g.m13363a(this.fields[6], this.fields[1]));
            super.set(4, (C2881g.m13363a(this.fields[6], this.fields[1]) - C2881g.m13363a(this.fields[6] - this.fields[5], this.fields[1])) + 1);
            this.isTimeSet = z;
        }
    }

    protected void computeTime() {
        C2880a b;
        if (!this.isTimeSet && !f9509g) {
            Calendar instance = GregorianCalendar.getInstance(f9508f);
            if (!isSet(11)) {
                super.set(11, instance.get(11));
            }
            if (!isSet(10)) {
                super.set(10, instance.get(10));
            }
            if (!isSet(12)) {
                super.set(12, instance.get(12));
            }
            if (!isSet(13)) {
                super.set(13, instance.get(13));
            }
            if (!isSet(14)) {
                super.set(14, instance.get(14));
            }
            if (!isSet(15)) {
                super.set(15, instance.get(15));
            }
            if (!isSet(16)) {
                super.set(16, instance.get(16));
            }
            if (!isSet(9)) {
                super.set(9, instance.get(9));
            }
            if (internalGet(11) < 12 || internalGet(11) > 23) {
                super.set(10, internalGet(11));
                super.set(9, 0);
            } else {
                super.set(9, 1);
                super.set(10, internalGet(11) - 12);
            }
            b = C2881g.m13366b(new C2880a(internalGet(1), internalGet(2), internalGet(5)));
            instance.set(b.m13358a(), b.m13360b(), b.m13362c(), internalGet(11), internalGet(12), internalGet(13));
            this.time = instance.getTimeInMillis();
        } else if (!this.isTimeSet && f9509g) {
            if (internalGet(11) < 12 || internalGet(11) > 23) {
                super.set(10, internalGet(11));
                super.set(9, 0);
            } else {
                super.set(9, 1);
                super.set(10, internalGet(11) - 12);
            }
            this.f9510h = new GregorianCalendar();
            super.set(15, f9508f.getRawOffset());
            super.set(16, f9508f.getDSTSavings());
            b = C2881g.m13366b(new C2880a(internalGet(1), internalGet(2), internalGet(5)));
            this.f9510h.set(b.m13358a(), b.m13360b(), b.m13362c(), internalGet(11), internalGet(12), internalGet(13));
            this.time = this.f9510h.getTimeInMillis();
        }
    }

    public int getGreatestMinimum(int i) {
        return f9505c[i];
    }

    public int getLeastMaximum(int i) {
        return f9506d[i];
    }

    public int getMaximum(int i) {
        return f9507e[i];
    }

    public int getMinimum(int i) {
        return f9505c[i];
    }

    public void roll(int i, int i2) {
        if (i2 != 0) {
            if (i < 0 || i >= 15) {
                throw new IllegalArgumentException();
            }
            int i3;
            complete();
            int i4;
            int[] iArr;
            switch (i) {
                case 1:
                    super.set(1, internalGet(1) + i2);
                    if (internalGet(2) == 11 && internalGet(5) == 30 && !C2881g.m13365a(internalGet(1))) {
                        super.set(5, 29);
                        return;
                    }
                    return;
                case 2:
                    break;
                case 3:
                    return;
                case 5:
                    i3 = 0;
                    if (get(2) >= 0 && get(2) <= 5) {
                        i3 = 31;
                    }
                    if (6 <= get(2) && get(2) <= 10) {
                        i3 = 30;
                    }
                    if (get(2) == 11) {
                        i3 = C2881g.m13365a(get(1)) ? 30 : 29;
                    }
                    i4 = (get(5) + i2) % i3;
                    super.set(5, i4 < 0 ? i3 + i4 : i4);
                    return;
                case 6:
                    i3 = C2881g.m13365a(internalGet(1)) ? 366 : 365;
                    i4 = (internalGet(6) + i2) % i3;
                    i3 = i4 > 0 ? i4 : i3 + i4;
                    int i5 = 0;
                    i4 = 0;
                    while (i3 > i4) {
                        i4 += f9504b[i5];
                        i5++;
                    }
                    super.set(2, i5 - 1);
                    super.set(5, f9504b[internalGet(2)] - (i4 - i3));
                    return;
                case 7:
                    i3 = i2 % 7;
                    if (i3 < 0) {
                        i3 += 7;
                    }
                    for (i4 = 0; i4 != i3; i4++) {
                        if (internalGet(7) == 6) {
                            add(5, -6);
                        } else {
                            add(5, 1);
                        }
                    }
                    return;
                case 9:
                    if (i2 % 2 != 0) {
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
                    super.set(10, (internalGet(10) + i2) % 12);
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
                    this.fields[11] = (internalGet(11) + i2) % 24;
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
                    i3 = (internalGet(12) + i2) % 60;
                    if (i3 < 0) {
                        i3 += 60;
                    }
                    super.set(12, i3);
                    return;
                case 13:
                    i3 = (internalGet(13) + i2) % 60;
                    if (i3 < 0) {
                        i3 += 60;
                    }
                    super.set(13, i3);
                    return;
                case 14:
                    i3 = (internalGet(14) + i2) % 1000;
                    if (i3 < 0) {
                        i3 += 1000;
                    }
                    super.set(14, i3);
                    return;
                default:
                    throw new IllegalArgumentException();
            }
            i3 = (internalGet(2) + i2) % 12;
            if (i3 < 0) {
                i3 += 12;
            }
            super.set(2, i3);
            i3 = f9504b[i3];
            if (internalGet(2) == 11 && C2881g.m13365a(internalGet(1))) {
                i3 = 30;
            }
            if (internalGet(5) > i3) {
                super.set(5, i3);
            }
        }
    }

    public void roll(int i, boolean z) {
        roll(i, z ? 1 : -1);
    }

    public void set(int i, int i2) {
        switch (i) {
            case 2:
                if (i2 > 11) {
                    super.set(i, 11);
                    add(i, i2 - 11);
                    return;
                } else if (i2 < 0) {
                    super.set(i, 0);
                    add(i, i2);
                    return;
                } else {
                    super.set(i, i2);
                    return;
                }
            case 3:
                if (isSet(1) && isSet(2) && isSet(5)) {
                    add(i, i2 - get(3));
                    return;
                } else {
                    super.set(i, i2);
                    return;
                }
            case 4:
                if (isSet(1) && isSet(2) && isSet(5)) {
                    add(i, i2 - get(4));
                    return;
                } else {
                    super.set(i, i2);
                    return;
                }
            case 5:
                super.set(i, 0);
                add(i, i2);
                return;
            case 6:
                if (isSet(1) && isSet(2) && isSet(5)) {
                    super.set(1, internalGet(1));
                    super.set(2, 0);
                    super.set(5, 0);
                    add(i, i2);
                    return;
                }
                super.set(i, i2);
                return;
            case 7:
                if (isSet(1) && isSet(2) && isSet(5)) {
                    add(7, (i2 % 7) - get(7));
                    return;
                } else {
                    super.set(i, i2);
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
                    this.f9510h = new GregorianCalendar();
                    C2880a b = C2881g.m13366b(new C2880a(internalGet(1), internalGet(2), internalGet(5)));
                    this.f9510h.set(b.m13358a(), b.m13360b(), b.m13362c(), internalGet(11), internalGet(12), internalGet(13));
                    this.f9510h.set(i, i2);
                    C2880a a = C2881g.m13364a(new C2880a(this.f9510h.get(1), this.f9510h.get(2), this.f9510h.get(5)));
                    super.set(1, a.m13358a());
                    super.set(2, a.m13360b());
                    super.set(5, a.m13362c());
                    super.set(11, this.f9510h.get(11));
                    super.set(12, this.f9510h.get(12));
                    super.set(13, this.f9510h.get(13));
                    return;
                }
                super.set(i, i2);
                return;
            default:
                super.set(i, i2);
                return;
        }
    }
}
