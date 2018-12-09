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

public class Resources_ru extends ListResourceBundle implements C2459d {
    /* renamed from: a */
    private static final Object[][] f8278a = ((Object[][]) Array.newInstance(Object.class, new int[]{0, 0}));

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_ru$1 */
    class C24791 implements C2451d {
        /* renamed from: a */
        final /* synthetic */ Resources_ru f8276a;

        C24791(Resources_ru resources_ru) {
            this.f8276a = resources_ru;
        }

        /* renamed from: b */
        private String m12127b(C2453a c2453a) {
            return c2453a.mo3402d() ? "сейчас" : c2453a.mo3401c() ? "только что" : null;
        }

        /* renamed from: a */
        public String mo3396a(C2453a c2453a) {
            return m12127b(c2453a);
        }

        /* renamed from: a */
        public String mo3397a(C2453a c2453a, String str) {
            return str;
        }
    }

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_ru$a */
    private static class C2480a implements C2451d {
        /* renamed from: a */
        private final String[] f8277a;

        public C2480a(String... strArr) {
            if (strArr.length != 3) {
                throw new IllegalArgumentException("Wrong plural forms number for russian language!");
            }
            this.f8277a = strArr;
        }

        /* renamed from: a */
        private String m12130a(boolean z, boolean z2, long j, String str) {
            int i = (j % 10 != 1 || j % 100 == 11) ? (j % 10 < 2 || j % 10 > 4 || (j % 100 >= 10 && j % 100 < 20)) ? 2 : 1 : 0;
            if (i > 3) {
                throw new IllegalStateException("Wrong plural index was calculated somehow for russian language");
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (z2) {
                stringBuilder.append("через ");
            }
            stringBuilder.append(str);
            stringBuilder.append(' ');
            stringBuilder.append(this.f8277a[i]);
            if (z) {
                stringBuilder.append(" назад");
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
            return m12130a(c2453a.mo3401c(), c2453a.mo3402d(), c2453a.mo3399a(50), str);
        }
    }

    /* renamed from: a */
    public C2451d mo3408a(C2457e c2457e) {
        if (c2457e instanceof C2464e) {
            return new C24791(this);
        }
        if (c2457e instanceof C2460a) {
            return new C2480a("век", "века", "веков");
        } else if (c2457e instanceof C2461b) {
            return new C2480a("день", "дня", "дней");
        } else if (c2457e instanceof C2462c) {
            return new C2480a("десятилетие", "десятилетия", "десятилетий");
        } else if (c2457e instanceof C2463d) {
            return new C2480a("час", "часа", "часов");
        } else if (c2457e instanceof C2465f) {
            return new C2480a("тысячелетие", "тысячелетия", "тысячелетий");
        } else if (c2457e instanceof C2466g) {
            return new C2480a("миллисекунду", "миллисекунды", "миллисекунд");
        } else if (c2457e instanceof C2467h) {
            return new C2480a("минуту", "минуты", "минут");
        } else if (c2457e instanceof C2468i) {
            return new C2480a("месяц", "месяца", "месяцев");
        } else if (c2457e instanceof C2469j) {
            return new C2480a("секунду", "секунды", "секунд");
        } else if (c2457e instanceof C2471l) {
            return new C2480a("неделю", "недели", "недель");
        } else if (!(c2457e instanceof C2472m)) {
            return null;
        } else {
            return new C2480a("год", "года", "лет");
        }
    }

    public Object[][] getContents() {
        return f8278a;
    }
}
