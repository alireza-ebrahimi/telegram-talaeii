package org.ocpsoft.prettytime.i18n;

import java.util.ListResourceBundle;

public class Resources_fr extends ListResourceBundle {
    private static final Object[][] OBJECTS;

    static {
        r0 = new Object[91][];
        r0[0] = new Object[]{"CenturyPattern", "%n %u"};
        r0[1] = new Object[]{"CenturyFuturePrefix", "dans "};
        r0[2] = new Object[]{"CenturyFutureSuffix", ""};
        r0[3] = new Object[]{"CenturyPastPrefix", "il y a "};
        r0[4] = new Object[]{"CenturyPastSuffix", ""};
        r0[5] = new Object[]{"CenturySingularName", "siècle"};
        r0[6] = new Object[]{"CenturyPluralName", "siècles"};
        r0[7] = new Object[]{"DayPattern", "%n %u"};
        r0[8] = new Object[]{"DayFuturePrefix", "dans "};
        r0[9] = new Object[]{"DayFutureSuffix", ""};
        r0[10] = new Object[]{"DayPastPrefix", "il y a "};
        r0[11] = new Object[]{"DayPastSuffix", ""};
        r0[12] = new Object[]{"DaySingularName", "jour"};
        r0[13] = new Object[]{"DayPluralName", "jours"};
        r0[14] = new Object[]{"DecadePattern", "%n %u"};
        r0[15] = new Object[]{"DecadeFuturePrefix", "dans "};
        r0[16] = new Object[]{"DecadeFutureSuffix", ""};
        r0[17] = new Object[]{"DecadePastPrefix", "il y a "};
        r0[18] = new Object[]{"DecadePastSuffix", ""};
        r0[19] = new Object[]{"DecadeSingularName", "décennie"};
        r0[20] = new Object[]{"DecadePluralName", "décennies"};
        r0[21] = new Object[]{"HourPattern", "%n %u"};
        r0[22] = new Object[]{"HourFuturePrefix", "dans "};
        r0[23] = new Object[]{"HourFutureSuffix", ""};
        r0[24] = new Object[]{"HourPastPrefix", "il y a "};
        r0[25] = new Object[]{"HourPastSuffix", ""};
        r0[26] = new Object[]{"HourSingularName", "heure"};
        r0[27] = new Object[]{"HourPluralName", "heures"};
        r0[28] = new Object[]{"JustNowPattern", "%u"};
        r0[29] = new Object[]{"JustNowFuturePrefix", ""};
        r0[30] = new Object[]{"JustNowFutureSuffix", "à l'instant"};
        r0[31] = new Object[]{"JustNowPastPrefix", "à l'instant"};
        r0[32] = new Object[]{"JustNowPastSuffix", ""};
        r0[33] = new Object[]{"JustNowSingularName", ""};
        r0[34] = new Object[]{"JustNowPluralName", ""};
        r0[35] = new Object[]{"MillenniumPattern", "%n %u"};
        r0[36] = new Object[]{"MillenniumFuturePrefix", "dans "};
        r0[37] = new Object[]{"MillenniumFutureSuffix", ""};
        r0[38] = new Object[]{"MillenniumPastPrefix", "il y a "};
        r0[39] = new Object[]{"MillenniumPastSuffix", ""};
        r0[40] = new Object[]{"MillenniumSingularName", "millénaire"};
        r0[41] = new Object[]{"MillenniumPluralName", "millénaires"};
        r0[42] = new Object[]{"MillisecondPattern", "%n %u"};
        r0[43] = new Object[]{"MillisecondFuturePrefix", "dans "};
        r0[44] = new Object[]{"MillisecondFutureSuffix", ""};
        r0[45] = new Object[]{"MillisecondPastPrefix", "il y a "};
        r0[46] = new Object[]{"MillisecondPastSuffix", ""};
        r0[47] = new Object[]{"MillisecondSingularName", "milliseconde"};
        r0[48] = new Object[]{"MillisecondPluralName", "millisecondes"};
        r0[49] = new Object[]{"MinutePattern", "%n %u"};
        r0[50] = new Object[]{"MinuteFuturePrefix", "dans "};
        r0[51] = new Object[]{"MinuteFutureSuffix", ""};
        r0[52] = new Object[]{"MinutePastPrefix", "il y a "};
        r0[53] = new Object[]{"MinutePastSuffix", ""};
        r0[54] = new Object[]{"MinuteSingularName", "minute"};
        r0[55] = new Object[]{"MinutePluralName", "minutes"};
        r0[56] = new Object[]{"MonthPattern", "%n %u"};
        r0[57] = new Object[]{"MonthFuturePrefix", "dans "};
        r0[58] = new Object[]{"MonthFutureSuffix", ""};
        r0[59] = new Object[]{"MonthPastPrefix", "il y a "};
        r0[60] = new Object[]{"MonthPastSuffix", ""};
        r0[61] = new Object[]{"MonthSingularName", "mois"};
        r0[62] = new Object[]{"MonthPluralName", "mois"};
        r0[63] = new Object[]{"SecondPattern", "%n %u"};
        r0[64] = new Object[]{"SecondFuturePrefix", "dans "};
        r0[65] = new Object[]{"SecondFutureSuffix", ""};
        r0[66] = new Object[]{"SecondPastPrefix", "il y a "};
        r0[67] = new Object[]{"SecondPastSuffix", ""};
        r0[68] = new Object[]{"SecondSingularName", "seconde"};
        r0[69] = new Object[]{"SecondPluralName", "secondes"};
        r0[70] = new Object[]{"WeekPattern", "%n %u"};
        r0[71] = new Object[]{"WeekFuturePrefix", "dans "};
        r0[72] = new Object[]{"WeekFutureSuffix", ""};
        r0[73] = new Object[]{"WeekPastPrefix", "il y a "};
        r0[74] = new Object[]{"WeekPastSuffix", ""};
        r0[75] = new Object[]{"WeekSingularName", "semaine"};
        r0[76] = new Object[]{"WeekPluralName", "semaines"};
        r0[77] = new Object[]{"YearPattern", "%n %u"};
        r0[78] = new Object[]{"YearFuturePrefix", "dans "};
        r0[79] = new Object[]{"YearFutureSuffix", ""};
        r0[80] = new Object[]{"YearPastPrefix", "il y a "};
        r0[81] = new Object[]{"YearPastSuffix", ""};
        r0[82] = new Object[]{"YearSingularName", "an"};
        r0[83] = new Object[]{"YearPluralName", "ans"};
        r0[84] = new Object[]{"AbstractTimeUnitPattern", ""};
        r0[85] = new Object[]{"AbstractTimeUnitFuturePrefix", ""};
        r0[86] = new Object[]{"AbstractTimeUnitFutureSuffix", ""};
        r0[87] = new Object[]{"AbstractTimeUnitPastPrefix", ""};
        r0[88] = new Object[]{"AbstractTimeUnitPastSuffix", ""};
        r0[89] = new Object[]{"AbstractTimeUnitSingularName", ""};
        r0[90] = new Object[]{"AbstractTimeUnitPluralName", ""};
        OBJECTS = r0;
    }

    protected Object[][] getContents() {
        return OBJECTS;
    }
}
