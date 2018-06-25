package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p127b.C2148g;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.persianswitch.a.l */
public final class C2208l {
    /* renamed from: a */
    private static final Pattern f6764a = Pattern.compile("(\\d{2,4})[^\\d]*");
    /* renamed from: b */
    private static final Pattern f6765b = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
    /* renamed from: c */
    private static final Pattern f6766c = Pattern.compile("(\\d{1,2})[^\\d]*");
    /* renamed from: d */
    private static final Pattern f6767d = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
    /* renamed from: e */
    private final String f6768e;
    /* renamed from: f */
    private final String f6769f;
    /* renamed from: g */
    private final long f6770g;
    /* renamed from: h */
    private final String f6771h;
    /* renamed from: i */
    private final String f6772i;
    /* renamed from: j */
    private final boolean f6773j;
    /* renamed from: k */
    private final boolean f6774k;
    /* renamed from: l */
    private final boolean f6775l;
    /* renamed from: m */
    private final boolean f6776m;

    private C2208l(String str, String str2, long j, String str3, String str4, boolean z, boolean z2, boolean z3, boolean z4) {
        this.f6768e = str;
        this.f6769f = str2;
        this.f6770g = j;
        this.f6771h = str3;
        this.f6772i = str4;
        this.f6773j = z;
        this.f6774k = z2;
        this.f6776m = z3;
        this.f6775l = z4;
    }

    /* renamed from: a */
    private static int m9987a(String str, int i, int i2, boolean z) {
        for (int i3 = i; i3 < i2; i3++) {
            char charAt = str.charAt(i3);
            Object obj = ((charAt >= ' ' || charAt == '\t') && charAt < '' && ((charAt < '0' || charAt > '9') && ((charAt < 'a' || charAt > 'z') && ((charAt < 'A' || charAt > 'Z') && charAt != ':')))) ? null : 1;
            if (obj == (!z ? 1 : null)) {
                return i3;
            }
        }
        return i2;
    }

    /* renamed from: a */
    private static long m9988a(String str) {
        try {
            long parseLong = Long.parseLong(str);
            return parseLong <= 0 ? Long.MIN_VALUE : parseLong;
        } catch (NumberFormatException e) {
            if (str.matches("-?\\d+")) {
                return !str.startsWith("-") ? Long.MAX_VALUE : Long.MIN_VALUE;
            } else {
                throw e;
            }
        }
    }

    /* renamed from: a */
    private static long m9989a(String str, int i, int i2) {
        int a = C2208l.m9987a(str, i, i2, false);
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        int i6 = -1;
        int i7 = -1;
        int i8 = -1;
        Matcher matcher = f6767d.matcher(str);
        while (a < i2) {
            int a2 = C2208l.m9987a(str, a + 1, i2, true);
            matcher.region(a, a2);
            if (i3 == -1 && matcher.usePattern(f6767d).matches()) {
                i3 = Integer.parseInt(matcher.group(1));
                i4 = Integer.parseInt(matcher.group(2));
                i5 = Integer.parseInt(matcher.group(3));
            } else if (i6 == -1 && matcher.usePattern(f6766c).matches()) {
                i6 = Integer.parseInt(matcher.group(1));
            } else if (i7 == -1 && matcher.usePattern(f6765b).matches()) {
                i7 = f6765b.pattern().indexOf(matcher.group(1).toLowerCase(Locale.US)) / 4;
            } else if (i8 == -1 && matcher.usePattern(f6764a).matches()) {
                i8 = Integer.parseInt(matcher.group(1));
            }
            a = C2208l.m9987a(str, a2 + 1, i2, false);
        }
        if (i8 >= 70 && i8 <= 99) {
            i8 += 1900;
        }
        if (i8 >= 0 && i8 <= 69) {
            i8 += 2000;
        }
        if (i8 < 1601) {
            throw new IllegalArgumentException();
        } else if (i7 == -1) {
            throw new IllegalArgumentException();
        } else if (i6 < 1 || i6 > 31) {
            throw new IllegalArgumentException();
        } else if (i3 < 0 || i3 > 23) {
            throw new IllegalArgumentException();
        } else if (i4 < 0 || i4 > 59) {
            throw new IllegalArgumentException();
        } else if (i5 < 0 || i5 > 59) {
            throw new IllegalArgumentException();
        } else {
            Calendar gregorianCalendar = new GregorianCalendar(C2187l.f6637d);
            gregorianCalendar.setLenient(false);
            gregorianCalendar.set(1, i8);
            gregorianCalendar.set(2, i7 - 1);
            gregorianCalendar.set(5, i6);
            gregorianCalendar.set(11, i3);
            gregorianCalendar.set(12, i4);
            gregorianCalendar.set(13, i5);
            gregorianCalendar.set(14, 0);
            return gregorianCalendar.getTimeInMillis();
        }
    }

    /* renamed from: a */
    static C2208l m9990a(long j, C2221r c2221r, String str) {
        int length = str.length();
        int a = C2187l.m9887a(str, 0, length, ';');
        int a2 = C2187l.m9887a(str, 0, a, '=');
        if (a2 == a) {
            return null;
        }
        String c = C2187l.m9911c(str, 0, a2);
        if (c.isEmpty()) {
            return null;
        }
        String substring;
        String c2 = C2187l.m9911c(str, a2 + 1, a);
        long j2 = 253402300799999L;
        long j3 = -1;
        String str2 = null;
        String str3 = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = true;
        boolean z4 = false;
        a++;
        while (a < length) {
            long j4;
            int a3 = C2187l.m9887a(str, a, length, ';');
            int a4 = C2187l.m9887a(str, a, a3, '=');
            String c3 = C2187l.m9911c(str, a, a4);
            String c4 = a4 < a3 ? C2187l.m9911c(str, a4 + 1, a3) : TtmlNode.ANONYMOUS_REGION_ID;
            if (c3.equalsIgnoreCase("expires")) {
                try {
                    j2 = C2208l.m9989a(c4, 0, c4.length());
                    z4 = true;
                    c4 = str2;
                    j4 = j2;
                } catch (IllegalArgumentException e) {
                    c4 = str2;
                    j4 = j2;
                }
            } else {
                if (c3.equalsIgnoreCase("max-age")) {
                    try {
                        j3 = C2208l.m9988a(c4);
                        z4 = true;
                        c4 = str2;
                        j4 = j2;
                    } catch (NumberFormatException e2) {
                        c4 = str2;
                        j4 = j2;
                    }
                } else {
                    if (c3.equalsIgnoreCase("domain")) {
                        try {
                            c4 = C2208l.m9993b(c4);
                            z3 = false;
                            j4 = j2;
                        } catch (IllegalArgumentException e3) {
                            c4 = str2;
                            j4 = j2;
                        }
                    } else {
                        if (c3.equalsIgnoreCase("path")) {
                            str3 = c4;
                            c4 = str2;
                            j4 = j2;
                        } else {
                            if (c3.equalsIgnoreCase("secure")) {
                                z = true;
                                c4 = str2;
                                j4 = j2;
                            } else {
                                if (c3.equalsIgnoreCase("httponly")) {
                                    z2 = true;
                                    c4 = str2;
                                    j4 = j2;
                                } else {
                                    c4 = str2;
                                    j4 = j2;
                                }
                            }
                        }
                    }
                }
            }
            String str4 = c4;
            a = a3 + 1;
            j2 = j4;
            str2 = str4;
        }
        if (j3 == Long.MIN_VALUE) {
            j3 = Long.MIN_VALUE;
        } else if (j3 != -1) {
            j3 = (j3 <= 9223372036854775L ? j3 * 1000 : Long.MAX_VALUE) + j;
            if (j3 < j || j3 > 253402300799999L) {
                j3 = 253402300799999L;
            }
        } else {
            j3 = j2;
        }
        if (str2 == null) {
            str2 = c2221r.m10075f();
        } else if (!C2208l.m9994b(c2221r, str2)) {
            return null;
        }
        if (str3 == null || !str3.startsWith("/")) {
            str3 = c2221r.m10077h();
            a = str3.lastIndexOf(47);
            substring = a != 0 ? str3.substring(0, a) : "/";
        } else {
            substring = str3;
        }
        return new C2208l(c, c2, j3, str2, substring, z, z2, z3, z4);
    }

    /* renamed from: a */
    public static C2208l m9991a(C2221r c2221r, String str) {
        return C2208l.m9990a(System.currentTimeMillis(), c2221r, str);
    }

    /* renamed from: a */
    public static List<C2208l> m9992a(C2221r c2221r, C2217q c2217q) {
        List c = c2217q.m10029c("Set-Cookie");
        List list = null;
        int size = c.size();
        for (int i = 0; i < size; i++) {
            C2208l a = C2208l.m9991a(c2221r, (String) c.get(i));
            if (a != null) {
                List arrayList = list == null ? new ArrayList() : list;
                arrayList.add(a);
                list = arrayList;
            }
        }
        return list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
    }

    /* renamed from: b */
    private static String m9993b(String str) {
        if (str.endsWith(".")) {
            throw new IllegalArgumentException();
        }
        if (str.startsWith(".")) {
            str = str.substring(1);
        }
        String a = C2187l.m9891a(str);
        if (a != null) {
            return a;
        }
        throw new IllegalArgumentException();
    }

    /* renamed from: b */
    private static boolean m9994b(C2221r c2221r, String str) {
        String f = c2221r.m10075f();
        return f.equals(str) ? true : f.endsWith(str) && f.charAt((f.length() - str.length()) - 1) == '.' && !C2187l.m9909b(f);
    }

    /* renamed from: a */
    public String m9995a() {
        return this.f6768e;
    }

    /* renamed from: b */
    public String m9996b() {
        return this.f6769f;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C2208l)) {
            return false;
        }
        C2208l c2208l = (C2208l) obj;
        return c2208l.f6768e.equals(this.f6768e) && c2208l.f6769f.equals(this.f6769f) && c2208l.f6771h.equals(this.f6771h) && c2208l.f6772i.equals(this.f6772i) && c2208l.f6770g == this.f6770g && c2208l.f6773j == this.f6773j && c2208l.f6774k == this.f6774k && c2208l.f6775l == this.f6775l && c2208l.f6776m == this.f6776m;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.f6775l ? 0 : 1) + (((this.f6774k ? 0 : 1) + (((this.f6773j ? 0 : 1) + ((((((((((this.f6768e.hashCode() + 527) * 31) + this.f6769f.hashCode()) * 31) + this.f6771h.hashCode()) * 31) + this.f6772i.hashCode()) * 31) + ((int) (this.f6770g ^ (this.f6770g >>> 32)))) * 31)) * 31)) * 31)) * 31;
        if (!this.f6776m) {
            i = 1;
        }
        return hashCode + i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f6768e);
        stringBuilder.append('=');
        stringBuilder.append(this.f6769f);
        if (this.f6775l) {
            if (this.f6770g == Long.MIN_VALUE) {
                stringBuilder.append("; max-age=0");
            } else {
                stringBuilder.append("; expires=").append(C2148g.m9693a(new Date(this.f6770g)));
            }
        }
        if (!this.f6776m) {
            stringBuilder.append("; domain=").append(this.f6771h);
        }
        stringBuilder.append("; path=").append(this.f6772i);
        if (this.f6773j) {
            stringBuilder.append("; secure");
        }
        if (this.f6774k) {
            stringBuilder.append("; httponly");
        }
        return stringBuilder.toString();
    }
}
