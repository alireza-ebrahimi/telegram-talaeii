package android.support.v4.p021e;

import android.os.Build.VERSION;
import java.util.Locale;

/* renamed from: android.support.v4.e.f */
public final class C0459f {
    /* renamed from: a */
    public static final Locale f1214a = new Locale(TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID);
    /* renamed from: b */
    static String f1215b = "Arab";
    /* renamed from: c */
    static String f1216c = "Hebr";
    /* renamed from: d */
    private static final C0457a f1217d;

    /* renamed from: android.support.v4.e.f$a */
    private static class C0457a {
        C0457a() {
        }

        /* renamed from: b */
        private static int m1945b(Locale locale) {
            switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
                case (byte) 1:
                case (byte) 2:
                    return 1;
                default:
                    return 0;
            }
        }

        /* renamed from: a */
        public int mo322a(Locale locale) {
            if (!(locale == null || locale.equals(C0459f.f1214a))) {
                String a = C0446a.m1929a(locale);
                if (a == null) {
                    return C0457a.m1945b(locale);
                }
                if (a.equalsIgnoreCase(C0459f.f1215b) || a.equalsIgnoreCase(C0459f.f1216c)) {
                    return 1;
                }
            }
            return 0;
        }
    }

    /* renamed from: android.support.v4.e.f$b */
    private static class C0458b extends C0457a {
        C0458b() {
        }

        /* renamed from: a */
        public int mo322a(Locale locale) {
            return C0460g.m1949a(locale);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            f1217d = new C0458b();
        } else {
            f1217d = new C0457a();
        }
    }

    /* renamed from: a */
    public static int m1948a(Locale locale) {
        return f1217d.mo322a(locale);
    }
}
