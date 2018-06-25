package org.ocpsoft.prettytime.i18n;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.format.SimpleTimeFormat;
import org.ocpsoft.prettytime.impl.TimeFormatProvider;
import org.ocpsoft.prettytime.units.Day;

public class Resources_fi extends ListResourceBundle implements TimeFormatProvider {
    private static Object[][] CONTENTS = null;
    private static final int tolerance = 50;
    private volatile ConcurrentMap<TimeUnit, TimeFormat> formatMap = new ConcurrentHashMap();

    private static class FiTimeFormat extends SimpleTimeFormat {
        private final ResourceBundle bundle;
        private String futureName = "";
        private String futurePluralName = "";
        private String pastName = "";
        private String pastPluralName = "";
        private String pluralPattern = "";

        public FiTimeFormat(ResourceBundle rb, TimeUnit unit) {
            this.bundle = rb;
            if (this.bundle.containsKey(getUnitName(unit) + "PastSingularName")) {
                setPastName(this.bundle.getString(getUnitName(unit) + "PastSingularName")).setFutureName(this.bundle.getString(getUnitName(unit) + "FutureSingularName")).setPastPluralName(this.bundle.getString(getUnitName(unit) + "PastSingularName")).setFuturePluralName(this.bundle.getString(getUnitName(unit) + "FutureSingularName")).setPluralPattern(this.bundle.getString(getUnitName(unit) + "Pattern"));
                if (this.bundle.containsKey(getUnitName(unit) + "PastPluralName")) {
                    setPastPluralName(this.bundle.getString(getUnitName(unit) + "PastPluralName"));
                }
                if (this.bundle.containsKey(getUnitName(unit) + "FuturePluralName")) {
                    setFuturePluralName(this.bundle.getString(getUnitName(unit) + "FuturePluralName"));
                }
                if (this.bundle.containsKey(getUnitName(unit) + "PluralPattern")) {
                    setPluralPattern(this.bundle.getString(getUnitName(unit) + "PluralPattern"));
                }
                setPattern(this.bundle.getString(getUnitName(unit) + "Pattern")).setPastSuffix(this.bundle.getString(getUnitName(unit) + "PastSuffix")).setFutureSuffix(this.bundle.getString(getUnitName(unit) + "FutureSuffix")).setFuturePrefix("").setPastPrefix("").setSingularName("").setPluralName("");
            }
        }

        public String getPastName() {
            return this.pastName;
        }

        public String getFutureName() {
            return this.futureName;
        }

        public String getPastPluralName() {
            return this.pastPluralName;
        }

        public String getFuturePluralName() {
            return this.futurePluralName;
        }

        public String getPluralPattern() {
            return this.pluralPattern;
        }

        public FiTimeFormat setPastName(String pastName) {
            this.pastName = pastName;
            return this;
        }

        public FiTimeFormat setFutureName(String futureName) {
            this.futureName = futureName;
            return this;
        }

        public FiTimeFormat setPastPluralName(String pastName) {
            this.pastPluralName = pastName;
            return this;
        }

        public FiTimeFormat setFuturePluralName(String futureName) {
            this.futurePluralName = futureName;
            return this;
        }

        public FiTimeFormat setPluralPattern(String pattern) {
            this.pluralPattern = pattern;
            return this;
        }

        protected String getGramaticallyCorrectName(Duration d, boolean round) {
            String result = d.isInPast() ? getPastName() : getFutureName();
            if (Math.abs(getQuantity(d, round)) == 0 || Math.abs(getQuantity(d, round)) > 1) {
                return d.isInPast() ? getPastPluralName() : getFuturePluralName();
            } else {
                return result;
            }
        }

        protected String getPattern(long quantity) {
            if (Math.abs(quantity) == 1) {
                return getPattern();
            }
            return getPluralPattern();
        }

        public String decorate(Duration duration, String time) {
            String result = "";
            if ((duration.getUnit() instanceof Day) && Math.abs(duration.getQuantityRounded(50)) == 1) {
                return time;
            }
            return super.decorate(duration, time);
        }

        private String getUnitName(TimeUnit unit) {
            return unit.getClass().getSimpleName();
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
        CONTENTS = r0;
    }

    public TimeFormat getFormatFor(TimeUnit t) {
        if (!this.formatMap.containsKey(t)) {
            this.formatMap.putIfAbsent(t, new FiTimeFormat(this, t));
        }
        return (TimeFormat) this.formatMap.get(t);
    }

    protected Object[][] getContents() {
        return CONTENTS;
    }
}
