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

public class Resources_ru extends ListResourceBundle implements TimeFormatProvider {
    private static final Object[][] OBJECTS = ((Object[][]) Array.newInstance(Object.class, new int[]{0, 0}));
    private static final int russianPluralForms = 3;
    private static final int tolerance = 50;

    /* renamed from: org.ocpsoft.prettytime.i18n.Resources_ru$1 */
    class C10071 implements TimeFormat {
        C10071() {
        }

        public String format(Duration duration) {
            return performFormat(duration);
        }

        public String formatUnrounded(Duration duration) {
            return performFormat(duration);
        }

        private String performFormat(Duration duration) {
            if (duration.isInFuture()) {
                return "сейчас";
            }
            if (duration.isInPast()) {
                return "только что";
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
                throw new IllegalArgumentException("Wrong plural forms number for russian language!");
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
                throw new IllegalStateException("Wrong plural index was calculated somehow for russian language");
            }
            StringBuilder result = new StringBuilder();
            if (future) {
                result.append("через ");
            }
            result.append(time);
            result.append(' ');
            result.append(this.pluarls[pluralIdx]);
            if (past) {
                result.append(" назад");
            }
            return result.toString();
        }
    }

    public Object[][] getContents() {
        return OBJECTS;
    }

    public TimeFormat getFormatFor(TimeUnit t) {
        if (t instanceof JustNow) {
            return new C10071();
        }
        if (t instanceof Century) {
            return new TimeFormatAided("век", "века", "веков");
        } else if (t instanceof Day) {
            return new TimeFormatAided("день", "дня", "дней");
        } else if (t instanceof Decade) {
            return new TimeFormatAided("десятилетие", "десятилетия", "десятилетий");
        } else if (t instanceof Hour) {
            return new TimeFormatAided("час", "часа", "часов");
        } else if (t instanceof Millennium) {
            return new TimeFormatAided("тысячелетие", "тысячелетия", "тысячелетий");
        } else if (t instanceof Millisecond) {
            return new TimeFormatAided("миллисекунду", "миллисекунды", "миллисекунд");
        } else if (t instanceof Minute) {
            return new TimeFormatAided("минуту", "минуты", "минут");
        } else if (t instanceof Month) {
            return new TimeFormatAided("месяц", "месяца", "месяцев");
        } else if (t instanceof Second) {
            return new TimeFormatAided("секунду", "секунды", "секунд");
        } else if (t instanceof Week) {
            return new TimeFormatAided("неделю", "недели", "недель");
        } else if (!(t instanceof Year)) {
            return null;
        } else {
            return new TimeFormatAided("год", "года", "лет");
        }
    }
}
