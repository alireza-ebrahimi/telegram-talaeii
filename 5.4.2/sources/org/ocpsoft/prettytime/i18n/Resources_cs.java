package org.ocpsoft.prettytime.i18n;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import org.ocpsoft.prettytime.C2451d;
import org.ocpsoft.prettytime.C2453a;
import org.ocpsoft.prettytime.C2457e;
import org.ocpsoft.prettytime.p146a.C2452a;
import org.ocpsoft.prettytime.p147b.C2459d;
import org.ocpsoft.prettytime.p148c.C2461b;
import org.ocpsoft.prettytime.p148c.C2463d;
import org.ocpsoft.prettytime.p148c.C2467h;
import org.ocpsoft.prettytime.p148c.C2468i;
import org.ocpsoft.prettytime.p148c.C2471l;
import org.ocpsoft.prettytime.p148c.C2472m;

public class Resources_cs extends ListResourceBundle implements C2459d {
    /* renamed from: a */
    private static final Object[][] f8233a;

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_cs$a */
    private static class C2474a implements Comparable<C2474a> {
        /* renamed from: a */
        private final boolean f8226a;
        /* renamed from: b */
        private final String f8227b;
        /* renamed from: c */
        private final Long f8228c;

        public C2474a(boolean z, String str, Long l) {
            this.f8226a = z;
            this.f8227b = str;
            this.f8228c = l;
        }

        /* renamed from: a */
        public int m12076a(C2474a c2474a) {
            return this.f8228c.compareTo(Long.valueOf(c2474a.m12079c()));
        }

        /* renamed from: a */
        public boolean m12077a() {
            return this.f8226a;
        }

        /* renamed from: b */
        public String m12078b() {
            return this.f8227b;
        }

        /* renamed from: c */
        public long m12079c() {
            return this.f8228c.longValue();
        }

        public /* synthetic */ int compareTo(Object obj) {
            return m12076a((C2474a) obj);
        }
    }

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_cs$b */
    private static class C2475b extends C2452a implements C2451d {
        /* renamed from: a */
        private final List<C2474a> f8229a = new ArrayList();
        /* renamed from: b */
        private final List<C2474a> f8230b = new ArrayList();

        public C2475b(String str, ResourceBundle resourceBundle, Collection<C2474a> collection) {
            m12011a(resourceBundle.getString(str + "Pattern"));
            m12013b(resourceBundle.getString(str + "FuturePrefix"));
            m12014c(resourceBundle.getString(str + "FutureSuffix"));
            m12015d(resourceBundle.getString(str + "PastPrefix"));
            m12016e(resourceBundle.getString(str + "PastSuffix"));
            m12017f(resourceBundle.getString(str + "SingularName"));
            m12018g(resourceBundle.getString(str + "PluralName"));
            try {
                mo3411i(resourceBundle.getString(str + "FuturePluralName"));
            } catch (Exception e) {
            }
            try {
                m12019h(resourceBundle.getString(str + "FutureSingularName"));
            } catch (Exception e2) {
            }
            try {
                mo3412k(resourceBundle.getString(str + "PastPluralName"));
            } catch (Exception e3) {
            }
            try {
                m12021j(resourceBundle.getString(str + "PastSingularName"));
            } catch (Exception e4) {
            }
            for (C2474a c2474a : collection) {
                if (c2474a.m12077a()) {
                    this.f8229a.add(c2474a);
                } else {
                    this.f8230b.add(c2474a);
                }
            }
            Collections.sort(this.f8229a);
            Collections.sort(this.f8230b);
        }

        /* renamed from: a */
        private String m12080a(long j, List<C2474a> list) {
            for (C2474a c2474a : list) {
                if (c2474a.m12079c() >= j) {
                    return c2474a.m12078b();
                }
            }
            throw new IllegalStateException("Invalid resource bundle configuration");
        }

        /* renamed from: b */
        protected String mo3407b(C2453a c2453a, boolean z) {
            long abs = Math.abs(m12006a(c2453a, z));
            return c2453a.mo3402d() ? m12080a(abs, this.f8229a) : m12080a(abs, this.f8230b);
        }
    }

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_cs$c */
    private static class C2476c {
        /* renamed from: a */
        private List<C2474a> f8231a = new ArrayList();
        /* renamed from: b */
        private String f8232b;

        C2476c(String str) {
            this.f8232b = str;
        }

        /* renamed from: a */
        private C2476c m12082a(boolean z, String str, long j) {
            if (str == null) {
                throw new IllegalArgumentException();
            }
            this.f8231a.add(new C2474a(z, str, Long.valueOf(j)));
            return this;
        }

        /* renamed from: a */
        C2475b m12083a(ResourceBundle resourceBundle) {
            return new C2475b(this.f8232b, resourceBundle, this.f8231a);
        }

        /* renamed from: a */
        C2476c m12084a(String str, long j) {
            return m12082a(true, str, j);
        }

        /* renamed from: b */
        C2476c m12085b(String str, long j) {
            return m12082a(false, str, j);
        }
    }

    static {
        r0 = new Object[103][];
        r0[0] = new Object[]{"CenturyPattern", "%n %u"};
        r0[1] = new Object[]{"CenturyFuturePrefix", "za "};
        r0[2] = new Object[]{"CenturyFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[3] = new Object[]{"CenturyPastPrefix", "před "};
        r0[4] = new Object[]{"CenturyPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[5] = new Object[]{"CenturySingularName", "století"};
        r0[6] = new Object[]{"CenturyPluralName", "století"};
        r0[7] = new Object[]{"CenturyPastSingularName", "stoletím"};
        r0[8] = new Object[]{"CenturyPastPluralName", "stoletími"};
        r0[9] = new Object[]{"CenturyFutureSingularName", "století"};
        r0[10] = new Object[]{"CenturyFuturePluralName", "století"};
        r0[11] = new Object[]{"DayPattern", "%n %u"};
        r0[12] = new Object[]{"DayFuturePrefix", "za "};
        r0[13] = new Object[]{"DayFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[14] = new Object[]{"DayPastPrefix", "před "};
        r0[15] = new Object[]{"DayPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[16] = new Object[]{"DaySingularName", "den"};
        r0[17] = new Object[]{"DayPluralName", "dny"};
        r0[18] = new Object[]{"DecadePattern", "%n %u"};
        r0[19] = new Object[]{"DecadeFuturePrefix", "za "};
        r0[20] = new Object[]{"DecadeFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[21] = new Object[]{"DecadePastPrefix", "před "};
        r0[22] = new Object[]{"DecadePastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[23] = new Object[]{"DecadeSingularName", "desetiletí"};
        r0[24] = new Object[]{"DecadePluralName", "desetiletí"};
        r0[25] = new Object[]{"DecadePastSingularName", "desetiletím"};
        r0[26] = new Object[]{"DecadePastPluralName", "desetiletími"};
        r0[27] = new Object[]{"DecadeFutureSingularName", "desetiletí"};
        r0[28] = new Object[]{"DecadeFuturePluralName", "desetiletí"};
        r0[29] = new Object[]{"HourPattern", "%n %u"};
        r0[30] = new Object[]{"HourFuturePrefix", "za "};
        r0[31] = new Object[]{"HourFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[32] = new Object[]{"HourPastPrefix", "před"};
        r0[33] = new Object[]{"HourPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[34] = new Object[]{"HourSingularName", "hodina"};
        r0[35] = new Object[]{"HourPluralName", "hodiny"};
        r0[36] = new Object[]{"JustNowPattern", "%u"};
        r0[37] = new Object[]{"JustNowFuturePrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[38] = new Object[]{"JustNowFutureSuffix", "za chvíli"};
        r0[39] = new Object[]{"JustNowPastPrefix", "před chvílí"};
        r0[40] = new Object[]{"JustNowPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[41] = new Object[]{"JustNowSingularName", TtmlNode.ANONYMOUS_REGION_ID};
        r0[42] = new Object[]{"JustNowPluralName", TtmlNode.ANONYMOUS_REGION_ID};
        r0[43] = new Object[]{"MillenniumPattern", "%n %u"};
        r0[44] = new Object[]{"MillenniumFuturePrefix", "za "};
        r0[45] = new Object[]{"MillenniumFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[46] = new Object[]{"MillenniumPastPrefix", "před "};
        r0[47] = new Object[]{"MillenniumPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[48] = new Object[]{"MillenniumSingularName", "tisíciletí"};
        r0[49] = new Object[]{"MillenniumPluralName", "tisíciletí"};
        r0[50] = new Object[]{"MillisecondPattern", "%n %u"};
        r0[51] = new Object[]{"MillisecondFuturePrefix", "za "};
        r0[52] = new Object[]{"MillisecondFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[53] = new Object[]{"MillisecondPastPrefix", "před "};
        r0[54] = new Object[]{"MillisecondPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[55] = new Object[]{"MillisecondSingularName", "milisekunda"};
        r0[56] = new Object[]{"MillisecondPluralName", "milisekundy"};
        r0[57] = new Object[]{"MillisecondPastSingularName", "milisekundou"};
        r0[58] = new Object[]{"MillisecondPastPluralName", "milisekundami"};
        r0[59] = new Object[]{"MillisecondFutureSingularName", "milisekundu"};
        r0[60] = new Object[]{"MillisecondFuturePluralName", "milisekund"};
        r0[61] = new Object[]{"MinutePattern", "%n %u"};
        r0[62] = new Object[]{"MinuteFuturePrefix", "za "};
        r0[63] = new Object[]{"MinuteFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[64] = new Object[]{"MinutePastPrefix", "před "};
        r0[65] = new Object[]{"MinutePastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[66] = new Object[]{"MinuteSingularName", "minuta"};
        r0[67] = new Object[]{"MinutePluralName", "minuty"};
        r0[68] = new Object[]{"MonthPattern", "%n %u"};
        r0[69] = new Object[]{"MonthFuturePrefix", "za "};
        r0[70] = new Object[]{"MonthFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[71] = new Object[]{"MonthPastPrefix", "před "};
        r0[72] = new Object[]{"MonthPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[73] = new Object[]{"MonthSingularName", "měsíc"};
        r0[74] = new Object[]{"MonthPluralName", "měsíce"};
        r0[75] = new Object[]{"SecondPattern", "%n %u"};
        r0[76] = new Object[]{"SecondFuturePrefix", "za "};
        r0[77] = new Object[]{"SecondFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[78] = new Object[]{"SecondPastPrefix", "před "};
        r0[79] = new Object[]{"SecondPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[80] = new Object[]{"SecondSingularName", "sekunda"};
        r0[81] = new Object[]{"SecondPluralName", "sekundy"};
        r0[82] = new Object[]{"WeekPattern", "%n %u"};
        r0[83] = new Object[]{"WeekFuturePrefix", "za "};
        r0[84] = new Object[]{"WeekFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[85] = new Object[]{"WeekPastPrefix", "před "};
        r0[86] = new Object[]{"WeekPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[87] = new Object[]{"WeekSingularName", "týden"};
        r0[88] = new Object[]{"WeekPluralName", "týdny"};
        r0[89] = new Object[]{"YearPattern", "%n %u"};
        r0[90] = new Object[]{"YearFuturePrefix", "za "};
        r0[91] = new Object[]{"YearFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[92] = new Object[]{"YearPastPrefix", "před "};
        r0[93] = new Object[]{"YearPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[94] = new Object[]{"YearSingularName", "rok"};
        r0[95] = new Object[]{"YearPluralName", "roky"};
        r0[96] = new Object[]{"AbstractTimeUnitPattern", TtmlNode.ANONYMOUS_REGION_ID};
        r0[97] = new Object[]{"AbstractTimeUnitFuturePrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[98] = new Object[]{"AbstractTimeUnitFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[99] = new Object[]{"AbstractTimeUnitPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[100] = new Object[]{"AbstractTimeUnitPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[101] = new Object[]{"AbstractTimeUnitSingularName", TtmlNode.ANONYMOUS_REGION_ID};
        r0[102] = new Object[]{"AbstractTimeUnitPluralName", TtmlNode.ANONYMOUS_REGION_ID};
        f8233a = r0;
    }

    /* renamed from: a */
    public C2451d mo3408a(C2457e c2457e) {
        return c2457e instanceof C2467h ? new C2476c("Minute").m12084a("minutu", 1).m12084a("minuty", 4).m12084a("minut", Long.MAX_VALUE).m12085b("minutou", 1).m12085b("minutami", Long.MAX_VALUE).m12083a(this) : c2457e instanceof C2463d ? new C2476c("Hour").m12084a("hodinu", 1).m12084a("hodiny", 4).m12084a("hodin", Long.MAX_VALUE).m12085b("hodinou", 1).m12085b("hodinami", Long.MAX_VALUE).m12083a(this) : c2457e instanceof C2461b ? new C2476c("Day").m12084a("den", 1).m12084a("dny", 4).m12084a("dní", Long.MAX_VALUE).m12085b("dnem", 1).m12085b("dny", Long.MAX_VALUE).m12083a(this) : c2457e instanceof C2471l ? new C2476c("Week").m12084a("týden", 1).m12084a("týdny", 4).m12084a("týdnů", Long.MAX_VALUE).m12085b("týdnem", 1).m12085b("týdny", Long.MAX_VALUE).m12083a(this) : c2457e instanceof C2468i ? new C2476c("Month").m12084a("měsíc", 1).m12084a("měsíce", 4).m12084a("měsíců", Long.MAX_VALUE).m12085b("měsícem", 1).m12085b("měsíci", Long.MAX_VALUE).m12083a(this) : c2457e instanceof C2472m ? new C2476c("Year").m12084a("rok", 1).m12084a("roky", 4).m12084a("let", Long.MAX_VALUE).m12085b("rokem", 1).m12085b("roky", Long.MAX_VALUE).m12083a(this) : null;
    }

    public Object[][] getContents() {
        return f8233a;
    }
}
