package com.p077f.p078a.p086b.p091d;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.InputStream;
import java.util.Locale;

/* renamed from: com.f.a.b.d.b */
public interface C1572b {

    /* renamed from: com.f.a.b.d.b$a */
    public enum C1574a {
        HTTP("http"),
        HTTPS("https"),
        FILE("file"),
        CONTENT(C1797b.CONTENT),
        ASSETS("assets"),
        DRAWABLE("drawable"),
        UNKNOWN(TtmlNode.ANONYMOUS_REGION_ID);
        
        /* renamed from: h */
        private String f4799h;
        /* renamed from: i */
        private String f4800i;

        private C1574a(String str) {
            this.f4799h = str;
            this.f4800i = str + "://";
        }

        /* renamed from: a */
        public static C1574a m7802a(String str) {
            if (str != null) {
                for (C1574a c1574a : C1574a.values()) {
                    if (c1574a.m7803d(str)) {
                        return c1574a;
                    }
                }
            }
            return UNKNOWN;
        }

        /* renamed from: d */
        private boolean m7803d(String str) {
            return str.toLowerCase(Locale.US).startsWith(this.f4800i);
        }

        /* renamed from: b */
        public String m7804b(String str) {
            return this.f4800i + str;
        }

        /* renamed from: c */
        public String m7805c(String str) {
            if (m7803d(str)) {
                return str.substring(this.f4800i.length());
            }
            throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", new Object[]{str, this.f4799h}));
        }
    }

    /* renamed from: a */
    InputStream mo1227a(String str, Object obj);
}
