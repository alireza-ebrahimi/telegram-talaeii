package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.p123a.C2187l;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.persianswitch.a.a.b.g */
public final class C2148g {
    /* renamed from: a */
    private static final ThreadLocal<DateFormat> f6510a = new C21471();
    /* renamed from: b */
    private static final String[] f6511b = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
    /* renamed from: c */
    private static final DateFormat[] f6512c = new DateFormat[f6511b.length];

    /* renamed from: com.persianswitch.a.a.b.g$1 */
    static class C21471 extends ThreadLocal<DateFormat> {
        C21471() {
        }

        /* renamed from: a */
        protected DateFormat m9692a() {
            DateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            simpleDateFormat.setLenient(false);
            simpleDateFormat.setTimeZone(C2187l.f6637d);
            return simpleDateFormat;
        }

        protected /* synthetic */ Object initialValue() {
            return m9692a();
        }
    }

    /* renamed from: a */
    public static String m9693a(Date date) {
        return ((DateFormat) f6510a.get()).format(date);
    }

    /* renamed from: a */
    public static Date m9694a(String str) {
        int i = 0;
        if (str.length() == 0) {
            return null;
        }
        ParsePosition parsePosition = new ParsePosition(0);
        Date parse = ((DateFormat) f6510a.get()).parse(str, parsePosition);
        if (parsePosition.getIndex() == str.length()) {
            return parse;
        }
        synchronized (f6511b) {
            int length = f6511b.length;
            while (i < length) {
                DateFormat dateFormat = f6512c[i];
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(f6511b[i], Locale.US);
                    dateFormat.setTimeZone(C2187l.f6637d);
                    f6512c[i] = dateFormat;
                }
                parsePosition.setIndex(0);
                parse = dateFormat.parse(str, parsePosition);
                if (parsePosition.getIndex() != 0) {
                    return parse;
                }
                i++;
            }
            return null;
        }
    }
}
