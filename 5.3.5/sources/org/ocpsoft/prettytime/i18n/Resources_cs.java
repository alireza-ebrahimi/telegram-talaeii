package org.ocpsoft.prettytime.i18n;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.format.SimpleTimeFormat;
import org.ocpsoft.prettytime.impl.TimeFormatProvider;
import org.ocpsoft.prettytime.units.Day;
import org.ocpsoft.prettytime.units.Hour;
import org.ocpsoft.prettytime.units.Minute;
import org.ocpsoft.prettytime.units.Month;
import org.ocpsoft.prettytime.units.Week;
import org.ocpsoft.prettytime.units.Year;

public class Resources_cs extends ListResourceBundle implements TimeFormatProvider {
    private static final Object[][] OBJECTS;

    private static class CsName implements Comparable<CsName> {
        private final boolean isFuture;
        private final Long threshold;
        private final String value;

        public CsName(boolean isFuture, String value, Long threshold) {
            this.isFuture = isFuture;
            this.value = value;
            this.threshold = threshold;
        }

        public boolean isFuture() {
            return this.isFuture;
        }

        public String get() {
            return this.value;
        }

        public long getThreshold() {
            return this.threshold.longValue();
        }

        public int compareTo(CsName o) {
            return this.threshold.compareTo(Long.valueOf(o.getThreshold()));
        }
    }

    private static class CsTimeFormat extends SimpleTimeFormat implements TimeFormat {
        private final List<CsName> futureNames = new ArrayList();
        private final List<CsName> pastNames = new ArrayList();

        public CsTimeFormat(String resourceKeyPrefix, ResourceBundle bundle, Collection<CsName> names) {
            setPattern(bundle.getString(resourceKeyPrefix + "Pattern"));
            setFuturePrefix(bundle.getString(resourceKeyPrefix + "FuturePrefix"));
            setFutureSuffix(bundle.getString(resourceKeyPrefix + "FutureSuffix"));
            setPastPrefix(bundle.getString(resourceKeyPrefix + "PastPrefix"));
            setPastSuffix(bundle.getString(resourceKeyPrefix + "PastSuffix"));
            setSingularName(bundle.getString(resourceKeyPrefix + "SingularName"));
            setPluralName(bundle.getString(resourceKeyPrefix + "PluralName"));
            try {
                setFuturePluralName(bundle.getString(resourceKeyPrefix + "FuturePluralName"));
            } catch (Exception e) {
            }
            try {
                setFutureSingularName(bundle.getString(resourceKeyPrefix + "FutureSingularName"));
            } catch (Exception e2) {
            }
            try {
                setPastPluralName(bundle.getString(resourceKeyPrefix + "PastPluralName"));
            } catch (Exception e3) {
            }
            try {
                setPastSingularName(bundle.getString(resourceKeyPrefix + "PastSingularName"));
            } catch (Exception e4) {
            }
            for (CsName name : names) {
                if (name.isFuture()) {
                    this.futureNames.add(name);
                } else {
                    this.pastNames.add(name);
                }
            }
            Collections.sort(this.futureNames);
            Collections.sort(this.pastNames);
        }

        protected String getGramaticallyCorrectName(Duration d, boolean round) {
            long quantity = Math.abs(getQuantity(d, round));
            if (d.isInFuture()) {
                return getGramaticallyCorrectName(quantity, this.futureNames);
            }
            return getGramaticallyCorrectName(quantity, this.pastNames);
        }

        private String getGramaticallyCorrectName(long quantity, List<CsName> names) {
            for (CsName name : names) {
                if (name.getThreshold() >= quantity) {
                    return name.get();
                }
            }
            throw new IllegalStateException("Invalid resource bundle configuration");
        }
    }

    private static class CsTimeFormatBuilder {
        private List<CsName> names = new ArrayList();
        private String resourceKeyPrefix;

        CsTimeFormatBuilder(String resourceKeyPrefix) {
            this.resourceKeyPrefix = resourceKeyPrefix;
        }

        CsTimeFormatBuilder addFutureName(String name, long limit) {
            return addName(true, name, limit);
        }

        CsTimeFormatBuilder addPastName(String name, long limit) {
            return addName(false, name, limit);
        }

        private CsTimeFormatBuilder addName(boolean isFuture, String name, long limit) {
            if (name == null) {
                throw new IllegalArgumentException();
            }
            this.names.add(new CsName(isFuture, name, Long.valueOf(limit)));
            return this;
        }

        CsTimeFormat build(ResourceBundle bundle) {
            return new CsTimeFormat(this.resourceKeyPrefix, bundle, this.names);
        }
    }

    static {
        r0 = new Object[103][];
        r0[0] = new Object[]{"CenturyPattern", "%n %u"};
        r0[1] = new Object[]{"CenturyFuturePrefix", "za "};
        r0[2] = new Object[]{"CenturyFutureSuffix", ""};
        r0[3] = new Object[]{"CenturyPastPrefix", "před "};
        r0[4] = new Object[]{"CenturyPastSuffix", ""};
        r0[5] = new Object[]{"CenturySingularName", "století"};
        r0[6] = new Object[]{"CenturyPluralName", "století"};
        r0[7] = new Object[]{"CenturyPastSingularName", "stoletím"};
        r0[8] = new Object[]{"CenturyPastPluralName", "stoletími"};
        r0[9] = new Object[]{"CenturyFutureSingularName", "století"};
        r0[10] = new Object[]{"CenturyFuturePluralName", "století"};
        r0[11] = new Object[]{"DayPattern", "%n %u"};
        r0[12] = new Object[]{"DayFuturePrefix", "za "};
        r0[13] = new Object[]{"DayFutureSuffix", ""};
        r0[14] = new Object[]{"DayPastPrefix", "před "};
        r0[15] = new Object[]{"DayPastSuffix", ""};
        r0[16] = new Object[]{"DaySingularName", "den"};
        r0[17] = new Object[]{"DayPluralName", "dny"};
        r0[18] = new Object[]{"DecadePattern", "%n %u"};
        r0[19] = new Object[]{"DecadeFuturePrefix", "za "};
        r0[20] = new Object[]{"DecadeFutureSuffix", ""};
        r0[21] = new Object[]{"DecadePastPrefix", "před "};
        r0[22] = new Object[]{"DecadePastSuffix", ""};
        r0[23] = new Object[]{"DecadeSingularName", "desetiletí"};
        r0[24] = new Object[]{"DecadePluralName", "desetiletí"};
        r0[25] = new Object[]{"DecadePastSingularName", "desetiletím"};
        r0[26] = new Object[]{"DecadePastPluralName", "desetiletími"};
        r0[27] = new Object[]{"DecadeFutureSingularName", "desetiletí"};
        r0[28] = new Object[]{"DecadeFuturePluralName", "desetiletí"};
        r0[29] = new Object[]{"HourPattern", "%n %u"};
        r0[30] = new Object[]{"HourFuturePrefix", "za "};
        r0[31] = new Object[]{"HourFutureSuffix", ""};
        r0[32] = new Object[]{"HourPastPrefix", "před"};
        r0[33] = new Object[]{"HourPastSuffix", ""};
        r0[34] = new Object[]{"HourSingularName", "hodina"};
        r0[35] = new Object[]{"HourPluralName", "hodiny"};
        r0[36] = new Object[]{"JustNowPattern", "%u"};
        r0[37] = new Object[]{"JustNowFuturePrefix", ""};
        r0[38] = new Object[]{"JustNowFutureSuffix", "za chvíli"};
        r0[39] = new Object[]{"JustNowPastPrefix", "před chvílí"};
        r0[40] = new Object[]{"JustNowPastSuffix", ""};
        r0[41] = new Object[]{"JustNowSingularName", ""};
        r0[42] = new Object[]{"JustNowPluralName", ""};
        r0[43] = new Object[]{"MillenniumPattern", "%n %u"};
        r0[44] = new Object[]{"MillenniumFuturePrefix", "za "};
        r0[45] = new Object[]{"MillenniumFutureSuffix", ""};
        r0[46] = new Object[]{"MillenniumPastPrefix", "před "};
        r0[47] = new Object[]{"MillenniumPastSuffix", ""};
        r0[48] = new Object[]{"MillenniumSingularName", "tisíciletí"};
        r0[49] = new Object[]{"MillenniumPluralName", "tisíciletí"};
        r0[50] = new Object[]{"MillisecondPattern", "%n %u"};
        r0[51] = new Object[]{"MillisecondFuturePrefix", "za "};
        r0[52] = new Object[]{"MillisecondFutureSuffix", ""};
        r0[53] = new Object[]{"MillisecondPastPrefix", "před "};
        r0[54] = new Object[]{"MillisecondPastSuffix", ""};
        r0[55] = new Object[]{"MillisecondSingularName", "milisekunda"};
        r0[56] = new Object[]{"MillisecondPluralName", "milisekundy"};
        r0[57] = new Object[]{"MillisecondPastSingularName", "milisekundou"};
        r0[58] = new Object[]{"MillisecondPastPluralName", "milisekundami"};
        r0[59] = new Object[]{"MillisecondFutureSingularName", "milisekundu"};
        r0[60] = new Object[]{"MillisecondFuturePluralName", "milisekund"};
        r0[61] = new Object[]{"MinutePattern", "%n %u"};
        r0[62] = new Object[]{"MinuteFuturePrefix", "za "};
        r0[63] = new Object[]{"MinuteFutureSuffix", ""};
        r0[64] = new Object[]{"MinutePastPrefix", "před "};
        r0[65] = new Object[]{"MinutePastSuffix", ""};
        r0[66] = new Object[]{"MinuteSingularName", "minuta"};
        r0[67] = new Object[]{"MinutePluralName", "minuty"};
        r0[68] = new Object[]{"MonthPattern", "%n %u"};
        r0[69] = new Object[]{"MonthFuturePrefix", "za "};
        r0[70] = new Object[]{"MonthFutureSuffix", ""};
        r0[71] = new Object[]{"MonthPastPrefix", "před "};
        r0[72] = new Object[]{"MonthPastSuffix", ""};
        r0[73] = new Object[]{"MonthSingularName", "měsíc"};
        r0[74] = new Object[]{"MonthPluralName", "měsíce"};
        r0[75] = new Object[]{"SecondPattern", "%n %u"};
        r0[76] = new Object[]{"SecondFuturePrefix", "za "};
        r0[77] = new Object[]{"SecondFutureSuffix", ""};
        r0[78] = new Object[]{"SecondPastPrefix", "před "};
        r0[79] = new Object[]{"SecondPastSuffix", ""};
        r0[80] = new Object[]{"SecondSingularName", "sekunda"};
        r0[81] = new Object[]{"SecondPluralName", "sekundy"};
        r0[82] = new Object[]{"WeekPattern", "%n %u"};
        r0[83] = new Object[]{"WeekFuturePrefix", "za "};
        r0[84] = new Object[]{"WeekFutureSuffix", ""};
        r0[85] = new Object[]{"WeekPastPrefix", "před "};
        r0[86] = new Object[]{"WeekPastSuffix", ""};
        r0[87] = new Object[]{"WeekSingularName", "týden"};
        r0[88] = new Object[]{"WeekPluralName", "týdny"};
        r0[89] = new Object[]{"YearPattern", "%n %u"};
        r0[90] = new Object[]{"YearFuturePrefix", "za "};
        r0[91] = new Object[]{"YearFutureSuffix", ""};
        r0[92] = new Object[]{"YearPastPrefix", "před "};
        r0[93] = new Object[]{"YearPastSuffix", ""};
        r0[94] = new Object[]{"YearSingularName", "rok"};
        r0[95] = new Object[]{"YearPluralName", "roky"};
        r0[96] = new Object[]{"AbstractTimeUnitPattern", ""};
        r0[97] = new Object[]{"AbstractTimeUnitFuturePrefix", ""};
        r0[98] = new Object[]{"AbstractTimeUnitFutureSuffix", ""};
        r0[99] = new Object[]{"AbstractTimeUnitPastPrefix", ""};
        r0[100] = new Object[]{"AbstractTimeUnitPastSuffix", ""};
        r0[101] = new Object[]{"AbstractTimeUnitSingularName", ""};
        r0[102] = new Object[]{"AbstractTimeUnitPluralName", ""};
        OBJECTS = r0;
    }

    public Object[][] getContents() {
        return OBJECTS;
    }

    public TimeFormat getFormatFor(TimeUnit t) {
        if (t instanceof Minute) {
            return new CsTimeFormatBuilder("Minute").addFutureName("minutu", 1).addFutureName("minuty", 4).addFutureName("minut", Long.MAX_VALUE).addPastName("minutou", 1).addPastName("minutami", Long.MAX_VALUE).build(this);
        }
        if (t instanceof Hour) {
            return new CsTimeFormatBuilder("Hour").addFutureName("hodinu", 1).addFutureName("hodiny", 4).addFutureName("hodin", Long.MAX_VALUE).addPastName("hodinou", 1).addPastName("hodinami", Long.MAX_VALUE).build(this);
        }
        if (t instanceof Day) {
            return new CsTimeFormatBuilder("Day").addFutureName("den", 1).addFutureName("dny", 4).addFutureName("dní", Long.MAX_VALUE).addPastName("dnem", 1).addPastName("dny", Long.MAX_VALUE).build(this);
        }
        if (t instanceof Week) {
            return new CsTimeFormatBuilder("Week").addFutureName("týden", 1).addFutureName("týdny", 4).addFutureName("týdnů", Long.MAX_VALUE).addPastName("týdnem", 1).addPastName("týdny", Long.MAX_VALUE).build(this);
        }
        if (t instanceof Month) {
            return new CsTimeFormatBuilder("Month").addFutureName("měsíc", 1).addFutureName("měsíce", 4).addFutureName("měsíců", Long.MAX_VALUE).addPastName("měsícem", 1).addPastName("měsíci", Long.MAX_VALUE).build(this);
        }
        if (t instanceof Year) {
            return new CsTimeFormatBuilder("Year").addFutureName("rok", 1).addFutureName("roky", 4).addFutureName("let", Long.MAX_VALUE).addPastName("rokem", 1).addPastName("roky", Long.MAX_VALUE).build(this);
        }
        return null;
    }
}
