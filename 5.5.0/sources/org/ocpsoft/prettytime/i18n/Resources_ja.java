package org.ocpsoft.prettytime.i18n;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.ocpsoft.prettytime.C2451d;
import org.ocpsoft.prettytime.C2453a;
import org.ocpsoft.prettytime.C2457e;
import org.ocpsoft.prettytime.p147b.C2459d;
import org.ocpsoft.prettytime.p148c.C2462c;
import org.ocpsoft.prettytime.p148c.C2465f;

public class Resources_ja extends ListResourceBundle implements C2459d {
    /* renamed from: a */
    private static final Object[][] f8268a;
    /* renamed from: b */
    private volatile ConcurrentMap<C2457e, C2451d> f8269b = new ConcurrentHashMap();

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_ja$a */
    private static class C2478a implements C2451d {
        /* renamed from: a */
        private final ResourceBundle f8255a;
        /* renamed from: b */
        private String f8256b = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: c */
        private String f8257c = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: d */
        private String f8258d = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: e */
        private String f8259e = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: f */
        private String f8260f = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: g */
        private String f8261g = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: h */
        private String f8262h = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: i */
        private String f8263i = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: j */
        private String f8264j = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: k */
        private String f8265k = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: l */
        private String f8266l = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: m */
        private int f8267m = 50;

        public C2478a(ResourceBundle resourceBundle, C2457e c2457e) {
            this.f8255a = resourceBundle;
            m12114a(resourceBundle.getString(m12105a(c2457e) + "Pattern"));
            m12116b(resourceBundle.getString(m12105a(c2457e) + "FuturePrefix"));
            m12117c(resourceBundle.getString(m12105a(c2457e) + "FutureSuffix"));
            m12118d(resourceBundle.getString(m12105a(c2457e) + "PastPrefix"));
            m12119e(resourceBundle.getString(m12105a(c2457e) + "PastSuffix"));
            m12120f(resourceBundle.getString(m12105a(c2457e) + "SingularName"));
            m12121g(resourceBundle.getString(m12105a(c2457e) + "PluralName"));
            try {
                m12123i(resourceBundle.getString(m12105a(c2457e) + "FuturePluralName"));
            } catch (Exception e) {
            }
            try {
                m12122h(resourceBundle.getString(m12105a(c2457e) + "FutureSingularName"));
            } catch (Exception e2) {
            }
            try {
                m12125k(resourceBundle.getString(m12105a(c2457e) + "PastPluralName"));
            } catch (Exception e3) {
            }
            try {
                m12124j(resourceBundle.getString(m12105a(c2457e) + "PastSingularName"));
            } catch (Exception e4) {
            }
        }

        /* renamed from: a */
        private String m12104a(String str, String str2, long j) {
            return m12111a(j).replaceAll("%s", str).replaceAll("%n", String.valueOf(j)).replaceAll("%u", str2);
        }

        /* renamed from: a */
        private String m12105a(C2457e c2457e) {
            return c2457e.getClass().getSimpleName();
        }

        /* renamed from: b */
        private String m12106b(C2453a c2453a) {
            return c2453a.mo3398a() < 0 ? "-" : TtmlNode.ANONYMOUS_REGION_ID;
        }

        /* renamed from: c */
        private String m12107c(C2453a c2453a) {
            return (!c2453a.mo3402d() || this.f8258d == null || this.f8258d.length() <= 0) ? (!c2453a.mo3401c() || this.f8260f == null || this.f8260f.length() <= 0) ? this.f8256b : this.f8260f : this.f8258d;
        }

        /* renamed from: c */
        private String m12108c(C2453a c2453a, boolean z) {
            String b = m12106b(c2453a);
            String b2 = m12115b(c2453a, z);
            long a = m12110a(c2453a, z);
            if (c2453a.mo3400b() instanceof C2462c) {
                a *= 10;
            }
            if (c2453a.mo3400b() instanceof C2465f) {
                a *= 1000;
            }
            return m12104a(b, b2, a);
        }

        /* renamed from: d */
        private String m12109d(C2453a c2453a) {
            return (!c2453a.mo3402d() || this.f8259e == null || this.f8258d.length() <= 0) ? (!c2453a.mo3401c() || this.f8261g == null || this.f8260f.length() <= 0) ? this.f8257c : this.f8261g : this.f8259e;
        }

        /* renamed from: a */
        protected long m12110a(C2453a c2453a, boolean z) {
            return Math.abs(z ? c2453a.mo3399a(this.f8267m) : c2453a.mo3398a());
        }

        /* renamed from: a */
        protected String m12111a(long j) {
            return this.f8262h;
        }

        /* renamed from: a */
        public String mo3396a(C2453a c2453a) {
            return m12108c(c2453a, true);
        }

        /* renamed from: a */
        public String mo3397a(C2453a c2453a, String str) {
            StringBuilder stringBuilder = new StringBuilder();
            if (c2453a.mo3401c()) {
                stringBuilder.append(this.f8265k).append(str).append(this.f8266l);
            } else {
                stringBuilder.append(this.f8263i).append(str).append(this.f8264j);
            }
            return stringBuilder.toString().replaceAll("\\s+", " ").trim();
        }

        /* renamed from: a */
        public C2478a m12114a(String str) {
            this.f8262h = str;
            return this;
        }

        /* renamed from: b */
        protected String m12115b(C2453a c2453a, boolean z) {
            return (Math.abs(m12110a(c2453a, z)) == 0 || Math.abs(m12110a(c2453a, z)) > 1) ? m12109d(c2453a) : m12107c(c2453a);
        }

        /* renamed from: b */
        public C2478a m12116b(String str) {
            this.f8263i = str.trim();
            return this;
        }

        /* renamed from: c */
        public C2478a m12117c(String str) {
            this.f8264j = str.trim();
            return this;
        }

        /* renamed from: d */
        public C2478a m12118d(String str) {
            this.f8265k = str.trim();
            return this;
        }

        /* renamed from: e */
        public C2478a m12119e(String str) {
            this.f8266l = str.trim();
            return this;
        }

        /* renamed from: f */
        public C2478a m12120f(String str) {
            this.f8256b = str;
            return this;
        }

        /* renamed from: g */
        public C2478a m12121g(String str) {
            this.f8257c = str;
            return this;
        }

        /* renamed from: h */
        public C2478a m12122h(String str) {
            this.f8258d = str;
            return this;
        }

        /* renamed from: i */
        public C2478a m12123i(String str) {
            this.f8259e = str;
            return this;
        }

        /* renamed from: j */
        public C2478a m12124j(String str) {
            this.f8260f = str;
            return this;
        }

        /* renamed from: k */
        public C2478a m12125k(String str) {
            this.f8261g = str;
            return this;
        }

        public String toString() {
            return "JaTimeFormat [pattern=" + this.f8262h + ", futurePrefix=" + this.f8263i + ", futureSuffix=" + this.f8264j + ", pastPrefix=" + this.f8265k + ", pastSuffix=" + this.f8266l + ", roundingTolerance=" + this.f8267m + "]";
        }
    }

    static {
        r0 = new Object[91][];
        r0[0] = new Object[]{"CenturyPattern", "%n%u"};
        r0[1] = new Object[]{"CenturyFuturePrefix", "今から"};
        r0[2] = new Object[]{"CenturyFutureSuffix", "後"};
        r0[3] = new Object[]{"CenturyPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[4] = new Object[]{"CenturyPastSuffix", "前"};
        r0[5] = new Object[]{"CenturySingularName", "世紀"};
        r0[6] = new Object[]{"CenturyPluralName", "世紀"};
        r0[7] = new Object[]{"DayPattern", "%n%u"};
        r0[8] = new Object[]{"DayFuturePrefix", "今から"};
        r0[9] = new Object[]{"DayFutureSuffix", "後"};
        r0[10] = new Object[]{"DayPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[11] = new Object[]{"DayPastSuffix", "前"};
        r0[12] = new Object[]{"DaySingularName", "日"};
        r0[13] = new Object[]{"DayPluralName", "日"};
        r0[14] = new Object[]{"DecadePattern", "%n%u"};
        r0[15] = new Object[]{"DecadeFuturePrefix", "今から"};
        r0[16] = new Object[]{"DecadeFutureSuffix", "後"};
        r0[17] = new Object[]{"DecadePastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[18] = new Object[]{"DecadePastSuffix", "前"};
        r0[19] = new Object[]{"DecadeSingularName", "年"};
        r0[20] = new Object[]{"DecadePluralName", "年"};
        r0[21] = new Object[]{"HourPattern", "%n%u"};
        r0[22] = new Object[]{"HourFuturePrefix", "今から"};
        r0[23] = new Object[]{"HourFutureSuffix", "後"};
        r0[24] = new Object[]{"HourPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[25] = new Object[]{"HourPastSuffix", "前"};
        r0[26] = new Object[]{"HourSingularName", "時間"};
        r0[27] = new Object[]{"HourPluralName", "時間"};
        r0[28] = new Object[]{"JustNowPattern", "%u"};
        r0[29] = new Object[]{"JustNowFuturePrefix", "今から"};
        r0[30] = new Object[]{"JustNowFutureSuffix", "すぐ"};
        r0[31] = new Object[]{"JustNowPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[32] = new Object[]{"JustNowPastSuffix", "たった今"};
        r0[33] = new Object[]{"JustNowSingularName", TtmlNode.ANONYMOUS_REGION_ID};
        r0[34] = new Object[]{"JustNowPluralName", TtmlNode.ANONYMOUS_REGION_ID};
        r0[35] = new Object[]{"MillenniumPattern", "%n%u"};
        r0[36] = new Object[]{"MillenniumFuturePrefix", "今から"};
        r0[37] = new Object[]{"MillenniumFutureSuffix", "後"};
        r0[38] = new Object[]{"MillenniumPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[39] = new Object[]{"MillenniumPastSuffix", "前"};
        r0[40] = new Object[]{"MillenniumSingularName", "年"};
        r0[41] = new Object[]{"MillenniumPluralName", "年"};
        r0[42] = new Object[]{"MillisecondPattern", "%n%u"};
        r0[43] = new Object[]{"MillisecondFuturePrefix", "今から"};
        r0[44] = new Object[]{"MillisecondFutureSuffix", "後"};
        r0[45] = new Object[]{"MillisecondPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[46] = new Object[]{"MillisecondPastSuffix", "前"};
        r0[47] = new Object[]{"MillisecondSingularName", "ミリ秒"};
        r0[48] = new Object[]{"MillisecondPluralName", "ミリ秒"};
        r0[49] = new Object[]{"MinutePattern", "%n%u"};
        r0[50] = new Object[]{"MinuteFuturePrefix", "今から"};
        r0[51] = new Object[]{"MinuteFutureSuffix", "後"};
        r0[52] = new Object[]{"MinutePastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[53] = new Object[]{"MinutePastSuffix", "前"};
        r0[54] = new Object[]{"MinuteSingularName", "分"};
        r0[55] = new Object[]{"MinutePluralName", "分"};
        r0[56] = new Object[]{"MonthPattern", "%n%u"};
        r0[57] = new Object[]{"MonthFuturePrefix", "今から"};
        r0[58] = new Object[]{"MonthFutureSuffix", "後"};
        r0[59] = new Object[]{"MonthPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[60] = new Object[]{"MonthPastSuffix", "前"};
        r0[61] = new Object[]{"MonthSingularName", "ヶ月"};
        r0[62] = new Object[]{"MonthPluralName", "ヶ月"};
        r0[63] = new Object[]{"SecondPattern", "%n%u"};
        r0[64] = new Object[]{"SecondFuturePrefix", "今から"};
        r0[65] = new Object[]{"SecondFutureSuffix", "後"};
        r0[66] = new Object[]{"SecondPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[67] = new Object[]{"SecondPastSuffix", "前"};
        r0[68] = new Object[]{"SecondSingularName", "秒"};
        r0[69] = new Object[]{"SecondPluralName", "秒"};
        r0[70] = new Object[]{"WeekPattern", "%n%u"};
        r0[71] = new Object[]{"WeekFuturePrefix", "今から"};
        r0[72] = new Object[]{"WeekFutureSuffix", "後"};
        r0[73] = new Object[]{"WeekPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[74] = new Object[]{"WeekPastSuffix", "前"};
        r0[75] = new Object[]{"WeekSingularName", "週間"};
        r0[76] = new Object[]{"WeekPluralName", "週間"};
        r0[77] = new Object[]{"YearPattern", "%n%u"};
        r0[78] = new Object[]{"YearFuturePrefix", "今から"};
        r0[79] = new Object[]{"YearFutureSuffix", "後"};
        r0[80] = new Object[]{"YearPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[81] = new Object[]{"YearPastSuffix", "前"};
        r0[82] = new Object[]{"YearSingularName", "年"};
        r0[83] = new Object[]{"YearPluralName", "年"};
        r0[84] = new Object[]{"AbstractTimeUnitPattern", TtmlNode.ANONYMOUS_REGION_ID};
        r0[85] = new Object[]{"AbstractTimeUnitFuturePrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[86] = new Object[]{"AbstractTimeUnitFutureSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[87] = new Object[]{"AbstractTimeUnitPastPrefix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[88] = new Object[]{"AbstractTimeUnitPastSuffix", TtmlNode.ANONYMOUS_REGION_ID};
        r0[89] = new Object[]{"AbstractTimeUnitSingularName", TtmlNode.ANONYMOUS_REGION_ID};
        r0[90] = new Object[]{"AbstractTimeUnitPluralName", TtmlNode.ANONYMOUS_REGION_ID};
        f8268a = r0;
    }

    /* renamed from: a */
    public C2451d mo3408a(C2457e c2457e) {
        if (!this.f8269b.containsKey(c2457e)) {
            this.f8269b.putIfAbsent(c2457e, new C2478a(this, c2457e));
        }
        return (C2451d) this.f8269b.get(c2457e);
    }

    public Object[][] getContents() {
        return f8268a;
    }
}
