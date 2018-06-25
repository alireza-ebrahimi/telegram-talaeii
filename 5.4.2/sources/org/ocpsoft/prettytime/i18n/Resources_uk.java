package org.ocpsoft.prettytime.i18n;

import java.lang.reflect.Array;
import java.util.ListResourceBundle;
import org.ocpsoft.prettytime.C2451d;
import org.ocpsoft.prettytime.C2453a;
import org.ocpsoft.prettytime.C2457e;
import org.ocpsoft.prettytime.p147b.C2459d;
import org.ocpsoft.prettytime.p148c.C2460a;
import org.ocpsoft.prettytime.p148c.C2461b;
import org.ocpsoft.prettytime.p148c.C2462c;
import org.ocpsoft.prettytime.p148c.C2463d;
import org.ocpsoft.prettytime.p148c.C2464e;
import org.ocpsoft.prettytime.p148c.C2465f;
import org.ocpsoft.prettytime.p148c.C2466g;
import org.ocpsoft.prettytime.p148c.C2467h;
import org.ocpsoft.prettytime.p148c.C2468i;
import org.ocpsoft.prettytime.p148c.C2469j;
import org.ocpsoft.prettytime.p148c.C2471l;
import org.ocpsoft.prettytime.p148c.C2472m;

public class Resources_uk extends ListResourceBundle implements C2459d {
    /* renamed from: a */
    private static final Object[][] f8294a = ((Object[][]) Array.newInstance(Object.class, new int[]{0, 0}));

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_uk$1 */
    class C24841 implements C2451d {
        /* renamed from: a */
        final /* synthetic */ Resources_uk f8292a;

        C24841(Resources_uk resources_uk) {
            this.f8292a = resources_uk;
        }

        /* renamed from: b */
        private String m12145b(C2453a c2453a) {
            return c2453a.mo3402d() ? "зараз" : c2453a.mo3401c() ? "щойно" : null;
        }

        /* renamed from: a */
        public String mo3396a(C2453a c2453a) {
            return m12145b(c2453a);
        }

        /* renamed from: a */
        public String mo3397a(C2453a c2453a, String str) {
            return str;
        }
    }

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_uk$a */
    private static class C2485a implements C2451d {
        /* renamed from: a */
        private final String[] f8293a;

        public C2485a(String... strArr) {
            if (strArr.length != 3) {
                throw new IllegalArgumentException("Wrong plural forms number for slavic language!");
            }
            this.f8293a = strArr;
        }

        /* renamed from: a */
        private String m12148a(boolean z, boolean z2, long j, String str) {
            int i = (j % 10 != 1 || j % 100 == 11) ? (j % 10 < 2 || j % 10 > 4 || (j % 100 >= 10 && j % 100 < 20)) ? 2 : 1 : 0;
            if (i > 3) {
                throw new IllegalStateException("Wrong plural index was calculated somehow for slavic language");
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (z2) {
                stringBuilder.append("через ");
            }
            stringBuilder.append(str);
            stringBuilder.append(' ');
            stringBuilder.append(this.f8293a[i]);
            if (z) {
                stringBuilder.append(" тому");
            }
            return stringBuilder.toString();
        }

        /* renamed from: a */
        public String mo3396a(C2453a c2453a) {
            long a = c2453a.mo3399a(50);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(a);
            return stringBuilder.toString();
        }

        /* renamed from: a */
        public String mo3397a(C2453a c2453a, String str) {
            return m12148a(c2453a.mo3401c(), c2453a.mo3402d(), c2453a.mo3399a(50), str);
        }
    }

    /* renamed from: a */
    public C2451d mo3408a(C2457e c2457e) {
        if (c2457e instanceof C2464e) {
            return new C24841(this);
        }
        if (c2457e instanceof C2460a) {
            return new C2485a("століття", "століття", "столітть");
        } else if (c2457e instanceof C2461b) {
            return new C2485a("день", "дні", "днів");
        } else if (c2457e instanceof C2462c) {
            return new C2485a("десятиліття", "десятиліття", "десятиліть");
        } else if (c2457e instanceof C2463d) {
            return new C2485a("годину", "години", "годин");
        } else if (c2457e instanceof C2465f) {
            return new C2485a("тисячоліття", "тисячоліття", "тисячоліть");
        } else if (c2457e instanceof C2466g) {
            return new C2485a("мілісекунду", "мілісекунди", "мілісекунд");
        } else if (c2457e instanceof C2467h) {
            return new C2485a("хвилину", "хвилини", "хвилин");
        } else if (c2457e instanceof C2468i) {
            return new C2485a("місяць", "місяці", "місяців");
        } else if (c2457e instanceof C2469j) {
            return new C2485a("секунду", "секунди", "секунд");
        } else if (c2457e instanceof C2471l) {
            return new C2485a("тиждень", "тижні", "тижнів");
        } else if (!(c2457e instanceof C2472m)) {
            return null;
        } else {
            return new C2485a("рік", "роки", "років");
        }
    }

    public Object[][] getContents() {
        return f8294a;
    }
}
