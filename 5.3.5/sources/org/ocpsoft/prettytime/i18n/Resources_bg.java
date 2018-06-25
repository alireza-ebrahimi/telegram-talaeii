package org.ocpsoft.prettytime.i18n;

import java.util.ListResourceBundle;

public class Resources_bg extends ListResourceBundle {
    private static final Object[][] OBJECTS;

    static {
        r0 = new Object[91][];
        r0[0] = new Object[]{"CenturyPattern", "%n %u"};
        r0[1] = new Object[]{"CenturyFuturePrefix", "след "};
        r0[2] = new Object[]{"CenturyFutureSuffix", ""};
        r0[3] = new Object[]{"CenturyPastPrefix", "преди "};
        r0[4] = new Object[]{"CenturyPastSuffix", ""};
        r0[5] = new Object[]{"CenturySingularName", "век"};
        r0[6] = new Object[]{"CenturyPluralName", "века"};
        r0[7] = new Object[]{"DayPattern", "%n %u"};
        r0[8] = new Object[]{"DayFuturePrefix", "след "};
        r0[9] = new Object[]{"DayFutureSuffix", ""};
        r0[10] = new Object[]{"DayPastPrefix", "преди "};
        r0[11] = new Object[]{"DayPastSuffix", ""};
        r0[12] = new Object[]{"DaySingularName", "ден"};
        r0[13] = new Object[]{"DayPluralName", "дни"};
        r0[14] = new Object[]{"DecadePattern", "%n %u"};
        r0[15] = new Object[]{"DecadeFuturePrefix", "след "};
        r0[16] = new Object[]{"DecadeFutureSuffix", ""};
        r0[17] = new Object[]{"DecadePastPrefix", "преди "};
        r0[18] = new Object[]{"DecadePastSuffix", ""};
        r0[19] = new Object[]{"DecadeSingularName", "десетилетие"};
        r0[20] = new Object[]{"DecadePluralName", "десетилетия"};
        r0[21] = new Object[]{"HourPattern", "%n %u"};
        r0[22] = new Object[]{"HourFuturePrefix", "след "};
        r0[23] = new Object[]{"HourFutureSuffix", ""};
        r0[24] = new Object[]{"HourPastPrefix", "преди "};
        r0[25] = new Object[]{"HourPastSuffix", ""};
        r0[26] = new Object[]{"HourSingularName", "час"};
        r0[27] = new Object[]{"HourPluralName", "часа"};
        r0[28] = new Object[]{"JustNowPattern", "%u"};
        r0[29] = new Object[]{"JustNowFuturePrefix", ""};
        r0[30] = new Object[]{"JustNowFutureSuffix", "в момента"};
        r0[31] = new Object[]{"JustNowPastPrefix", "току що"};
        r0[32] = new Object[]{"JustNowPastSuffix", ""};
        r0[33] = new Object[]{"JustNowSingularName", ""};
        r0[34] = new Object[]{"JustNowPluralName", ""};
        r0[35] = new Object[]{"MillenniumPattern", "%n %u"};
        r0[36] = new Object[]{"MillenniumFuturePrefix", "след "};
        r0[37] = new Object[]{"MillenniumFutureSuffix", ""};
        r0[38] = new Object[]{"MillenniumPastPrefix", "преди "};
        r0[39] = new Object[]{"MillenniumPastSuffix", ""};
        r0[40] = new Object[]{"MillenniumSingularName", "хилядолетие"};
        r0[41] = new Object[]{"MillenniumPluralName", "хилядолетия"};
        r0[42] = new Object[]{"MillisecondPattern", "%n %u"};
        r0[43] = new Object[]{"MillisecondFuturePrefix", "след "};
        r0[44] = new Object[]{"MillisecondFutureSuffix", ""};
        r0[45] = new Object[]{"MillisecondPastPrefix", "преди "};
        r0[46] = new Object[]{"MillisecondPastSuffix", ""};
        r0[47] = new Object[]{"MillisecondSingularName", "милисекунда"};
        r0[48] = new Object[]{"MillisecondPluralName", "милисекунди"};
        r0[49] = new Object[]{"MinutePattern", "%n %u"};
        r0[50] = new Object[]{"MinuteFuturePrefix", "след "};
        r0[51] = new Object[]{"MinuteFutureSuffix", ""};
        r0[52] = new Object[]{"MinutePastPrefix", "преди "};
        r0[53] = new Object[]{"MinutePastSuffix", ""};
        r0[54] = new Object[]{"MinuteSingularName", "минута"};
        r0[55] = new Object[]{"MinutePluralName", "минути"};
        r0[56] = new Object[]{"MonthPattern", "%n %u"};
        r0[57] = new Object[]{"MonthFuturePrefix", "след "};
        r0[58] = new Object[]{"MonthFutureSuffix", ""};
        r0[59] = new Object[]{"MonthPastPrefix", "преди "};
        r0[60] = new Object[]{"MonthPastSuffix", ""};
        r0[61] = new Object[]{"MonthSingularName", "месец"};
        r0[62] = new Object[]{"MonthPluralName", "месеца"};
        r0[63] = new Object[]{"SecondPattern", "%n %u"};
        r0[64] = new Object[]{"SecondFuturePrefix", "след "};
        r0[65] = new Object[]{"SecondFutureSuffix", ""};
        r0[66] = new Object[]{"SecondPastPrefix", "преди "};
        r0[67] = new Object[]{"SecondPastSuffix", ""};
        r0[68] = new Object[]{"SecondSingularName", "секунда"};
        r0[69] = new Object[]{"SecondPluralName", "секунди"};
        r0[70] = new Object[]{"WeekPattern", "%n %u"};
        r0[71] = new Object[]{"WeekFuturePrefix", "след "};
        r0[72] = new Object[]{"WeekFutureSuffix", ""};
        r0[73] = new Object[]{"WeekPastPrefix", "преди "};
        r0[74] = new Object[]{"WeekPastSuffix", ""};
        r0[75] = new Object[]{"WeekSingularName", "седмица"};
        r0[76] = new Object[]{"WeekPluralName", "седмици"};
        r0[77] = new Object[]{"YearPattern", "%n %u"};
        r0[78] = new Object[]{"YearFuturePrefix", "след "};
        r0[79] = new Object[]{"YearFutureSuffix", ""};
        r0[80] = new Object[]{"YearPastPrefix", "преди "};
        r0[81] = new Object[]{"YearPastSuffix", ""};
        r0[82] = new Object[]{"YearSingularName", "година"};
        r0[83] = new Object[]{"YearPluralName", "години"};
        r0[84] = new Object[]{"AbstractTimeUnitPattern", ""};
        r0[85] = new Object[]{"AbstractTimeUnitFuturePrefix", ""};
        r0[86] = new Object[]{"AbstractTimeUnitFutureSuffix", ""};
        r0[87] = new Object[]{"AbstractTimeUnitPastPrefix", ""};
        r0[88] = new Object[]{"AbstractTimeUnitPastSuffix", ""};
        r0[89] = new Object[]{"AbstractTimeUnitSingularName", ""};
        r0[90] = new Object[]{"AbstractTimeUnitPluralName", ""};
        OBJECTS = r0;
    }

    public Object[][] getContents() {
        return OBJECTS;
    }
}
