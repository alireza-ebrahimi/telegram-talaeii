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

public class Resources_sk extends ListResourceBundle implements C2459d {
    /* renamed from: a */
    private static final Object[][] f8286a;

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_sk$a */
    private static class C2481a implements Comparable<C2481a> {
        /* renamed from: a */
        private final boolean f8279a;
        /* renamed from: b */
        private final String f8280b;
        /* renamed from: c */
        private final Long f8281c;

        public C2481a(boolean z, String str, Long l) {
            this.f8279a = z;
            this.f8280b = str;
            this.f8281c = l;
        }

        /* renamed from: a */
        public int m12134a(C2481a c2481a) {
            return this.f8281c.compareTo(Long.valueOf(c2481a.m12137c()));
        }

        /* renamed from: a */
        public boolean m12135a() {
            return this.f8279a;
        }

        /* renamed from: b */
        public String m12136b() {
            return this.f8280b;
        }

        /* renamed from: c */
        public long m12137c() {
            return this.f8281c.longValue();
        }

        public /* synthetic */ int compareTo(Object obj) {
            return m12134a((C2481a) obj);
        }
    }

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_sk$b */
    private static class C2482b extends C2452a implements C2451d {
        /* renamed from: a */
        private final List<C2481a> f8282a = new ArrayList();
        /* renamed from: b */
        private final List<C2481a> f8283b = new ArrayList();

        public C2482b(String str, ResourceBundle resourceBundle, Collection<C2481a> collection) {
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
            for (C2481a c2481a : collection) {
                if (c2481a.m12135a()) {
                    this.f8282a.add(c2481a);
                } else {
                    this.f8283b.add(c2481a);
                }
            }
            Collections.sort(this.f8282a);
            Collections.sort(this.f8283b);
        }

        /* renamed from: a */
        private String m12138a(long j, List<C2481a> list) {
            for (C2481a c2481a : list) {
                if (c2481a.m12137c() >= j) {
                    return c2481a.m12136b();
                }
            }
            throw new IllegalStateException("Invalid resource bundle configuration");
        }

        /* renamed from: b */
        protected String mo3407b(C2453a c2453a, boolean z) {
            long abs = Math.abs(m12006a(c2453a, z));
            return c2453a.mo3402d() ? m12138a(abs, this.f8282a) : m12138a(abs, this.f8283b);
        }
    }

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_sk$c */
    private static class C2483c {
        /* renamed from: a */
        private List<C2481a> f8284a = new ArrayList();
        /* renamed from: b */
        private String f8285b;

        C2483c(String str) {
            this.f8285b = str;
        }

        /* renamed from: a */
        private C2483c m12140a(boolean z, String str, long j) {
            if (str == null) {
                throw new IllegalArgumentException();
            }
            this.f8284a.add(new C2481a(z, str, Long.valueOf(j)));
            return this;
        }

        /* renamed from: a */
        C2482b m12141a(ResourceBundle resourceBundle) {
            return new C2482b(this.f8285b, resourceBundle, this.f8284a);
        }

        /* renamed from: a */
        C2483c m12142a(String str, long j) {
            return m12140a(true, str, j);
        }

        /* renamed from: b */
        C2483c m12143b(String str, long j) {
            return m12140a(false, str, j);
        }
    }

    static {
        r0 = new Object[103][];
        r0[0] = new Object[]{"CenturyPattern", "%n %u"};
        r0[1] = new Object[]{"CenturyFuturePrefix", "o "};
        r0[2] = new Object[]{"CenturyFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[3] = new Object[]{"CenturyPastPrefix", "pred "};
        r0[4] = new Object[]{"CenturyPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[5] = new Object[]{"CenturySingularName", "storočie"};
        r0[6] = new Object[]{"CenturyPluralName", "storočia"};
        r0[7] = new Object[]{"CenturyPastSingularName", "storočím"};
        r0[8] = new Object[]{"CenturyPastPluralName", "storočiami"};
        r0[9] = new Object[]{"CenturyFutureSingularName", "storočí"};
        r0[10] = new Object[]{"CenturyFuturePluralName", "storočia"};
        r0[11] = new Object[]{"DayPattern", "%n %u"};
        r0[12] = new Object[]{"DayFuturePrefix", "o "};
        r0[13] = new Object[]{"DayFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[14] = new Object[]{"DayPastPrefix", "pred "};
        r0[15] = new Object[]{"DayPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[16] = new Object[]{"DaySingularName", "deň"};
        r0[17] = new Object[]{"DayPluralName", "dni"};
        r0[18] = new Object[]{"DecadePattern", "%n %u"};
        r0[19] = new Object[]{"DecadeFuturePrefix", "o "};
        r0[20] = new Object[]{"DecadeFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[21] = new Object[]{"DecadePastPrefix", "pred "};
        r0[22] = new Object[]{"DecadePastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[23] = new Object[]{"DecadeSingularName", "desaťročie"};
        r0[24] = new Object[]{"DecadePluralName", "desaťročia"};
        r0[25] = new Object[]{"DecadePastSingularName", "desaťročím"};
        r0[26] = new Object[]{"DecadePastPluralName", "desaťročiami"};
        r0[27] = new Object[]{"DecadeFutureSingularName", "desaťročí"};
        r0[28] = new Object[]{"DecadeFuturePluralName", "desaťročia"};
        r0[29] = new Object[]{"HourPattern", "%n %u"};
        r0[30] = new Object[]{"HourFuturePrefix", "o "};
        r0[31] = new Object[]{"HourFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[32] = new Object[]{"HourPastPrefix", "pred"};
        r0[33] = new Object[]{"HourPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[34] = new Object[]{"HourSingularName", "hodina"};
        r0[35] = new Object[]{"HourPluralName", "hodiny"};
        r0[36] = new Object[]{"JustNowPattern", "%u"};
        r0[37] = new Object[]{"JustNowFuturePrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[38] = new Object[]{"JustNowFutureSuffix", "o chvíľu"};
        r0[39] = new Object[]{"JustNowPastPrefix", "pred chvíľou"};
        r0[40] = new Object[]{"JustNowPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[41] = new Object[]{"JustNowSingularName", TtmlNode.ANONYMOUS_REGION_ID};
        r0[42] = new Object[]{"JustNowPluralName", TtmlNode.ANONYMOUS_REGION_ID};
        r0[43] = new Object[]{"MillenniumPattern", "%n %u"};
        r0[44] = new Object[]{"MillenniumFuturePrefix", "o "};
        r0[45] = new Object[]{"MillenniumFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[46] = new Object[]{"MillenniumPastPrefix", "pred "};
        r0[47] = new Object[]{"MillenniumPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[48] = new Object[]{"MillenniumSingularName", "tísícročie"};
        r0[49] = new Object[]{"MillenniumPluralName", "tisícročia"};
        r0[50] = new Object[]{"MillisecondPattern", "%n %u"};
        r0[51] = new Object[]{"MillisecondFuturePrefix", "o "};
        r0[52] = new Object[]{"MillisecondFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[53] = new Object[]{"MillisecondPastPrefix", "pred "};
        r0[54] = new Object[]{"MillisecondPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[55] = new Object[]{"MillisecondSingularName", "milisekunda"};
        r0[56] = new Object[]{"MillisecondPluralName", "milisekundy"};
        r0[57] = new Object[]{"MillisecondPastSingularName", "milisekundou"};
        r0[58] = new Object[]{"MillisecondPastPluralName", "milisekundami"};
        r0[59] = new Object[]{"MillisecondFutureSingularName", "milisekundu"};
        r0[60] = new Object[]{"MillisecondFuturePluralName", "milisekúnd"};
        r0[61] = new Object[]{"MinutePattern", "%n %u"};
        r0[62] = new Object[]{"MinuteFuturePrefix", "o "};
        r0[63] = new Object[]{"MinuteFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[64] = new Object[]{"MinutePastPrefix", "pred "};
        r0[65] = new Object[]{"MinutePastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[66] = new Object[]{"MinuteSingularName", "minúta"};
        r0[67] = new Object[]{"MinutePluralName", "minúty"};
        r0[68] = new Object[]{"MonthPattern", "%n %u"};
        r0[69] = new Object[]{"MonthFuturePrefix", "o "};
        r0[70] = new Object[]{"MonthFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[71] = new Object[]{"MonthPastPrefix", "pred "};
        r0[72] = new Object[]{"MonthPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[73] = new Object[]{"MonthSingularName", "mesiac"};
        r0[74] = new Object[]{"MonthPluralName", "mesiace"};
        r0[75] = new Object[]{"SecondPattern", "%n %u"};
        r0[76] = new Object[]{"SecondFuturePrefix", "o "};
        r0[77] = new Object[]{"SecondFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[78] = new Object[]{"SecondPastPrefix", "pred "};
        r0[79] = new Object[]{"SecondPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[80] = new Object[]{"SecondSingularName", "sekunda"};
        r0[81] = new Object[]{"SecondPluralName", "sekundy"};
        r0[82] = new Object[]{"WeekPattern", "%n %u"};
        r0[83] = new Object[]{"WeekFuturePrefix", "o "};
        r0[84] = new Object[]{"WeekFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[85] = new Object[]{"WeekPastPrefix", "pred "};
        r0[86] = new Object[]{"WeekPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[87] = new Object[]{"WeekSingularName", "týždeň"};
        r0[88] = new Object[]{"WeekPluralName", "týždne"};
        r0[89] = new Object[]{"YearPattern", "%n %u"};
        r0[90] = new Object[]{"YearFuturePrefix", "o "};
        r0[91] = new Object[]{"YearFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[92] = new Object[]{"YearPastPrefix", "pred "};
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
        f8286a = r0;
    }

    /* renamed from: a */
    public C2451d mo3408a(C2457e c2457e) {
        return c2457e instanceof C2467h ? new C2483c("Minute").m12142a("minútu", 1).m12142a("minúty", 4).m12142a("minút", Long.MAX_VALUE).m12143b("minútou", 1).m12143b("minútami", Long.MAX_VALUE).m12141a(this) : c2457e instanceof C2463d ? new C2483c("Hour").m12142a("hodinu", 1).m12142a("hodiny", 4).m12142a("hodín", Long.MAX_VALUE).m12143b("hodinou", 1).m12143b("hodinami", Long.MAX_VALUE).m12141a(this) : c2457e instanceof C2461b ? new C2483c("Day").m12142a("deň", 1).m12142a("dni", 4).m12142a("dní", Long.MAX_VALUE).m12143b("dňom", 1).m12143b("dňami", Long.MAX_VALUE).m12141a(this) : c2457e instanceof C2471l ? new C2483c("Week").m12142a("týždeň", 1).m12142a("týždne", 4).m12142a("týždňov", Long.MAX_VALUE).m12143b("týždňom", 1).m12143b("týždňami", Long.MAX_VALUE).m12141a(this) : c2457e instanceof C2468i ? new C2483c("Month").m12142a("mesiac", 1).m12142a("mesiace", 4).m12142a("mesiacov", Long.MAX_VALUE).m12143b("mesiacom", 1).m12143b("mesiacmi", Long.MAX_VALUE).m12141a(this) : c2457e instanceof C2472m ? new C2483c("Year").m12142a("rok", 1).m12142a("roky", 4).m12142a("rokov", Long.MAX_VALUE).m12143b("rokom", 1).m12143b("rokmi", Long.MAX_VALUE).m12141a(this) : null;
    }

    public Object[][] getContents() {
        return f8286a;
    }
}
