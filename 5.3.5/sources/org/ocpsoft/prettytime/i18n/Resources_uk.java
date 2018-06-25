package org.ocpsoft.prettytime.i18n;

import java.lang.reflect.Array;
import java.util.ListResourceBundle;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.TimeFormatProvider;
import org.ocpsoft.prettytime.units.Century;
import org.ocpsoft.prettytime.units.Day;
import org.ocpsoft.prettytime.units.Decade;
import org.ocpsoft.prettytime.units.Hour;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Millennium;
import org.ocpsoft.prettytime.units.Millisecond;
import org.ocpsoft.prettytime.units.Minute;
import org.ocpsoft.prettytime.units.Month;
import org.ocpsoft.prettytime.units.Second;
import org.ocpsoft.prettytime.units.Week;
import org.ocpsoft.prettytime.units.Year;

public class Resources_uk extends ListResourceBundle implements TimeFormatProvider {
    private static final Object[][] OBJECTS = ((Object[][]) Array.newInstance(Object.class, new int[]{0, 0}));
    private static final int slavicPluralForms = 3;
    private static final int tolerance = 50;

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_uk$1 */
    class C10081 implements TimeFormat {
        C10081() {
        }

        public String format(Duration duration) {
            return performFormat(duration);
        }

        public String formatUnrounded(Duration duration) {
            return performFormat(duration);
        }

        private String performFormat(Duration duration) {
            if (duration.isInFuture()) {
                return "зараз";
            }
            if (duration.isInPast()) {
                return "щойно";
            }
            return null;
        }

        public String decorate(Duration duration, String time) {
            return time;
        }

        public String decorateUnrounded(Duration duration, String time) {
            return time;
        }
    }

    private static class TimeFormatAided implements TimeFormat {
        private final String[] pluarls;

        public TimeFormatAided(String... plurals) {
            if (plurals.length != 3) {
                throw new IllegalArgumentException("Wrong plural forms number for slavic language!");
            }
            this.pluarls = plurals;
        }

        public String format(Duration duration) {
            long quantity = duration.getQuantityRounded(50);
            StringBuilder result = new StringBuilder();
            result.append(quantity);
            return result.toString();
        }

        public String formatUnrounded(Duration duration) {
            long quantity = duration.getQuantity();
            StringBuilder result = new StringBuilder();
            result.append(quantity);
            return result.toString();
        }

        public String decorate(Duration duration, String time) {
            return performDecoration(duration.isInPast(), duration.isInFuture(), duration.getQuantityRounded(50), time);
        }

        public String decorateUnrounded(Duration duration, String time) {
            return performDecoration(duration.isInPast(), duration.isInFuture(), duration.getQuantity(), time);
        }

        private String performDecoration(boolean past, boolean future, long n, String time) {
            int pluralIdx = (n % 10 != 1 || n % 100 == 11) ? (n % 10 < 2 || n % 10 > 4 || (n % 100 >= 10 && n % 100 < 20)) ? 2 : 1 : 0;
            if (pluralIdx > 3) {
                throw new IllegalStateException("Wrong plural index was calculated somehow for slavic language");
            }
            StringBuilder result = new StringBuilder();
            if (future) {
                result.append("через ");
            }
            result.append(time);
            result.append(' ');
            result.append(this.pluarls[pluralIdx]);
            if (past) {
                result.append(" тому");
            }
            return result.toString();
        }
    }

    public Object[][] getContents() {
        return OBJECTS;
    }

    public TimeFormat getFormatFor(TimeUnit t) {
        if (t instanceof JustNow) {
            return new C10081();
        }
        if (t instanceof Century) {
            return new TimeFormatAided("століття", "століття", "столітть");
        } else if (t instanceof Day) {
            return new TimeFormatAided("день", "дні", "днів");
        } else if (t instanceof Decade) {
            return new TimeFormatAided("десятиліття", "десятиліття", "десятиліть");
        } else if (t instanceof Hour) {
            return new TimeFormatAided("годину", "години", "годин");
        } else if (t instanceof Millennium) {
            return new TimeFormatAided("тисячоліття", "тисячоліття", "тисячоліть");
        } else if (t instanceof Millisecond) {
            return new TimeFormatAided("мілісекунду", "мілісекунди", "мілісекунд");
        } else if (t instanceof Minute) {
            return new TimeFormatAided("хвилину", "хвилини", "хвилин");
        } else if (t instanceof Month) {
            return new TimeFormatAided("місяць", "місяці", "місяців");
        } else if (t instanceof Second) {
            return new TimeFormatAided("секунду", "секунди", "секунд");
        } else if (t instanceof Week) {
            return new TimeFormatAided("тиждень", "тижні", "тижнів");
        } else if (!(t instanceof Year)) {
            return null;
        } else {
            return new TimeFormatAided("рік", "роки", "років");
        }
    }
}
