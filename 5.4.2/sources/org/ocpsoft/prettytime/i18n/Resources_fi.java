package org.ocpsoft.prettytime.i18n;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.ocpsoft.prettytime.C2451d;
import org.ocpsoft.prettytime.C2453a;
import org.ocpsoft.prettytime.C2457e;
import org.ocpsoft.prettytime.p146a.C2452a;
import org.ocpsoft.prettytime.p147b.C2459d;
import org.ocpsoft.prettytime.p148c.C2461b;

public class Resources_fi extends ListResourceBundle implements C2459d {
    /* renamed from: a */
    private static Object[][] f8237a;
    /* renamed from: b */
    private volatile ConcurrentMap<C2457e, C2451d> f8238b = new ConcurrentHashMap();

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_fi$a */
    private static class C2477a extends C2452a {
        /* renamed from: a */
        private final ResourceBundle f8241a;
        /* renamed from: b */
        private String f8242b = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: c */
        private String f8243c = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: d */
        private String f8244d = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: e */
        private String f8245e = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: f */
        private String f8246f = TtmlNode.ANONYMOUS_REGION_ID;

        public C2477a(ResourceBundle resourceBundle, C2457e c2457e) {
            this.f8241a = resourceBundle;
            if (this.f8241a.containsKey(m12088a(c2457e) + "PastSingularName")) {
                m12099l(this.f8241a.getString(m12088a(c2457e) + "PastSingularName")).m12100m(this.f8241a.getString(m12088a(c2457e) + "FutureSingularName")).m12101n(this.f8241a.getString(m12088a(c2457e) + "PastSingularName")).m12102o(this.f8241a.getString(m12088a(c2457e) + "FutureSingularName")).m12103p(this.f8241a.getString(m12088a(c2457e) + "Pattern"));
                if (this.f8241a.containsKey(m12088a(c2457e) + "PastPluralName")) {
                    m12101n(this.f8241a.getString(m12088a(c2457e) + "PastPluralName"));
                }
                if (this.f8241a.containsKey(m12088a(c2457e) + "FuturePluralName")) {
                    m12102o(this.f8241a.getString(m12088a(c2457e) + "FuturePluralName"));
                }
                if (this.f8241a.containsKey(m12088a(c2457e) + "PluralPattern")) {
                    m12103p(this.f8241a.getString(m12088a(c2457e) + "PluralPattern"));
                }
                m12011a(this.f8241a.getString(m12088a(c2457e) + "Pattern")).m12016e(this.f8241a.getString(m12088a(c2457e) + "PastSuffix")).m12014c(this.f8241a.getString(m12088a(c2457e) + "FutureSuffix")).m12013b(TtmlNode.ANONYMOUS_REGION_ID).m12015d(TtmlNode.ANONYMOUS_REGION_ID).m12017f(TtmlNode.ANONYMOUS_REGION_ID).m12018g(TtmlNode.ANONYMOUS_REGION_ID);
            }
        }

        /* renamed from: a */
        private String m12088a(C2457e c2457e) {
            return c2457e.getClass().getSimpleName();
        }

        /* renamed from: a */
        protected String mo3410a(long j) {
            return Math.abs(j) == 1 ? m12007a() : m12096f();
        }

        /* renamed from: a */
        public String mo3397a(C2453a c2453a, String str) {
            String str2 = TtmlNode.ANONYMOUS_REGION_ID;
            return ((c2453a.mo3400b() instanceof C2461b) && Math.abs(c2453a.mo3399a(50)) == 1) ? str : super.mo3397a(c2453a, str);
        }

        /* renamed from: b */
        public String m12091b() {
            return this.f8242b;
        }

        /* renamed from: b */
        protected String mo3407b(C2453a c2453a, boolean z) {
            return (Math.abs(m12006a(c2453a, z)) == 0 || Math.abs(m12006a(c2453a, z)) > 1) ? c2453a.mo3401c() ? m12094d() : m12095e() : c2453a.mo3401c() ? m12091b() : m12093c();
        }

        /* renamed from: c */
        public String m12093c() {
            return this.f8243c;
        }

        /* renamed from: d */
        public String m12094d() {
            return this.f8244d;
        }

        /* renamed from: e */
        public String m12095e() {
            return this.f8245e;
        }

        /* renamed from: f */
        public String m12096f() {
            return this.f8246f;
        }

        /* renamed from: i */
        public /* synthetic */ C2452a mo3411i(String str) {
            return m12102o(str);
        }

        /* renamed from: k */
        public /* synthetic */ C2452a mo3412k(String str) {
            return m12101n(str);
        }

        /* renamed from: l */
        public C2477a m12099l(String str) {
            this.f8242b = str;
            return this;
        }

        /* renamed from: m */
        public C2477a m12100m(String str) {
            this.f8243c = str;
            return this;
        }

        /* renamed from: n */
        public C2477a m12101n(String str) {
            this.f8244d = str;
            return this;
        }

        /* renamed from: o */
        public C2477a m12102o(String str) {
            this.f8245e = str;
            return this;
        }

        /* renamed from: p */
        public C2477a m12103p(String str) {
            this.f8246f = str;
            return this;
        }
    }

    static {
        r0 = new Object[84][];
        r0[0] = new Object[]{"JustNowPattern", "%u"};
        r0[1] = new Object[]{"JustNowPastSingularName", "hetki"};
        r0[2] = new Object[]{"JustNowFutureSingularName", "hetken"};
        r0[3] = new Object[]{"JustNowPastSuffix", "sitten"};
        r0[4] = new Object[]{"JustNowFutureSuffix", "päästä"};
        r0[5] = new Object[]{"MillisecondPattern", "%u"};
        r0[6] = new Object[]{"MillisecondPluralPattern", "%n %u"};
        r0[7] = new Object[]{"MillisecondPastSingularName", "millisekunti"};
        r0[8] = new Object[]{"MillisecondPastPluralName", "millisekuntia"};
        r0[9] = new Object[]{"MillisecondFutureSingularName", "millisekunnin"};
        r0[10] = new Object[]{"MillisecondPastSuffix", "sitten"};
        r0[11] = new Object[]{"MillisecondFutureSuffix", "päästä"};
        r0[12] = new Object[]{"SecondPattern", "%u"};
        r0[13] = new Object[]{"SecondPluralPattern", "%n %u"};
        r0[14] = new Object[]{"SecondPastSingularName", "sekunti"};
        r0[15] = new Object[]{"SecondPastPluralName", "sekuntia"};
        r0[16] = new Object[]{"SecondFutureSingularName", "sekunnin"};
        r0[17] = new Object[]{"SecondPastSuffix", "sitten"};
        r0[18] = new Object[]{"SecondFutureSuffix", "päästä"};
        r0[19] = new Object[]{"MinutePattern", "%u"};
        r0[20] = new Object[]{"MinutePluralPattern", "%n %u"};
        r0[21] = new Object[]{"MinutePastSingularName", "minuutti"};
        r0[22] = new Object[]{"MinutePastPluralName", "minuuttia"};
        r0[23] = new Object[]{"MinuteFutureSingularName", "minuutin"};
        r0[24] = new Object[]{"MinutePastSuffix", "sitten"};
        r0[25] = new Object[]{"MinuteFutureSuffix", "päästä"};
        r0[26] = new Object[]{"HourPattern", "%u"};
        r0[27] = new Object[]{"HourPluralPattern", "%n %u"};
        r0[28] = new Object[]{"HourPastSingularName", "tunti"};
        r0[29] = new Object[]{"HourPastPluralName", "tuntia"};
        r0[30] = new Object[]{"HourFutureSingularName", "tunnin"};
        r0[31] = new Object[]{"HourPastSuffix", "sitten"};
        r0[32] = new Object[]{"HourFutureSuffix", "päästä"};
        r0[33] = new Object[]{"DayPattern", "%u"};
        r0[34] = new Object[]{"DayPluralPattern", "%n %u"};
        r0[35] = new Object[]{"DayPastSingularName", "eilen"};
        r0[36] = new Object[]{"DayPastPluralName", "päivää"};
        r0[37] = new Object[]{"DayFutureSingularName", "huomenna"};
        r0[38] = new Object[]{"DayFuturePluralName", "päivän"};
        r0[39] = new Object[]{"DayPastSuffix", "sitten"};
        r0[40] = new Object[]{"DayFutureSuffix", "päästä"};
        r0[41] = new Object[]{"WeekPattern", "%u"};
        r0[42] = new Object[]{"WeekPluralPattern", "%n %u"};
        r0[43] = new Object[]{"WeekPastSingularName", "viikko"};
        r0[44] = new Object[]{"WeekPastPluralName", "viikkoa"};
        r0[45] = new Object[]{"WeekFutureSingularName", "viikon"};
        r0[46] = new Object[]{"WeekFuturePluralName", "viikon"};
        r0[47] = new Object[]{"WeekPastSuffix", "sitten"};
        r0[48] = new Object[]{"WeekFutureSuffix", "päästä"};
        r0[49] = new Object[]{"MonthPattern", "%u"};
        r0[50] = new Object[]{"MonthPluralPattern", "%n %u"};
        r0[51] = new Object[]{"MonthPastSingularName", "kuukausi"};
        r0[52] = new Object[]{"MonthPastPluralName", "kuukautta"};
        r0[53] = new Object[]{"MonthFutureSingularName", "kuukauden"};
        r0[54] = new Object[]{"MonthPastSuffix", "sitten"};
        r0[55] = new Object[]{"MonthFutureSuffix", "päästä"};
        r0[56] = new Object[]{"YearPattern", "%u"};
        r0[57] = new Object[]{"YearPluralPattern", "%n %u"};
        r0[58] = new Object[]{"YearPastSingularName", "vuosi"};
        r0[59] = new Object[]{"YearPastPluralName", "vuotta"};
        r0[60] = new Object[]{"YearFutureSingularName", "vuoden"};
        r0[61] = new Object[]{"YearPastSuffix", "sitten"};
        r0[62] = new Object[]{"YearFutureSuffix", "päästä"};
        r0[63] = new Object[]{"DecadePattern", "%u"};
        r0[64] = new Object[]{"DecadePluralPattern", "%n %u"};
        r0[65] = new Object[]{"DecadePastSingularName", "vuosikymmen"};
        r0[66] = new Object[]{"DecadePastPluralName", "vuosikymmentä"};
        r0[67] = new Object[]{"DecadeFutureSingularName", "vuosikymmenen"};
        r0[68] = new Object[]{"DecadePastSuffix", "sitten"};
        r0[69] = new Object[]{"DecadeFutureSuffix", "päästä"};
        r0[70] = new Object[]{"CenturyPattern", "%u"};
        r0[71] = new Object[]{"CenturyPluralPattern", "%n %u"};
        r0[72] = new Object[]{"CenturyPastSingularName", "vuosisata"};
        r0[73] = new Object[]{"CenturyPastPluralName", "vuosisataa"};
        r0[74] = new Object[]{"CenturyFutureSingularName", "vuosisadan"};
        r0[75] = new Object[]{"CenturyPastSuffix", "sitten"};
        r0[76] = new Object[]{"CenturyFutureSuffix", "päästä"};
        r0[77] = new Object[]{"MillenniumPattern", "%u"};
        r0[78] = new Object[]{"MillenniumPluralPattern", "%n %u"};
        r0[79] = new Object[]{"MillenniumPastSingularName", "vuosituhat"};
        r0[80] = new Object[]{"MillenniumPastPluralName", "vuosituhatta"};
        r0[81] = new Object[]{"MillenniumFutureSingularName", "vuosituhannen"};
        r0[82] = new Object[]{"MillenniumPastSuffix", "sitten"};
        r0[83] = new Object[]{"MillenniumFutureSuffix", "päästä"};
        f8237a = r0;
    }

    /* renamed from: a */
    public C2451d mo3408a(C2457e c2457e) {
        if (!this.f8238b.containsKey(c2457e)) {
            this.f8238b.putIfAbsent(c2457e, new C2477a(this, c2457e));
        }
        return (C2451d) this.f8238b.get(c2457e);
    }

    protected Object[][] getContents() {
        return f8237a;
    }
}
