package org.ocpsoft.prettytime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.ocpsoft.prettytime.impl.DurationImpl;
import org.ocpsoft.prettytime.impl.ResourcesTimeFormat;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;
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
import org.ocpsoft.prettytime.units.TimeUnitComparator;
import org.ocpsoft.prettytime.units.Week;
import org.ocpsoft.prettytime.units.Year;

public class PrettyTime {
    private volatile List<TimeUnit> cachedUnits;
    private volatile Locale locale;
    private volatile Date reference;
    private volatile Map<TimeUnit, TimeFormat> units;

    public PrettyTime() {
        this.locale = Locale.getDefault();
        this.units = new LinkedHashMap();
        initTimeUnits();
    }

    public PrettyTime(Date reference) {
        this();
        setReference(reference);
    }

    public PrettyTime(Locale locale) {
        this.locale = Locale.getDefault();
        this.units = new LinkedHashMap();
        setLocale(locale);
        initTimeUnits();
    }

    public PrettyTime(Date reference, Locale locale) {
        this(locale);
        setReference(reference);
    }

    public Duration approximateDuration(Date then) {
        if (then == null) {
            then = now();
        }
        Date ref = this.reference;
        if (ref == null) {
            ref = now();
        }
        return calculateDuration(then.getTime() - ref.getTime());
    }

    public List<Duration> calculatePreciseDuration(Date then) {
        if (then == null) {
            then = now();
        }
        if (this.reference == null) {
            this.reference = now();
        }
        List<Duration> result = new ArrayList();
        Duration duration = calculateDuration(then.getTime() - this.reference.getTime());
        result.add(duration);
        while (0 != duration.getDelta()) {
            duration = calculateDuration(duration.getDelta());
            if (result.size() > 0 && ((Duration) result.get(result.size() - 1)).getUnit().equals(duration.getUnit())) {
                break;
            } else if (duration.getUnit().isPrecise()) {
                result.add(duration);
            }
        }
        return result;
    }

    public String format(Date then) {
        if (then == null) {
            then = now();
        }
        return format(approximateDuration(then));
    }

    public String format(Calendar then) {
        if (then == null) {
            return format(now());
        }
        return format(then.getTime());
    }

    public String format(Duration duration) {
        if (duration == null) {
            return format(now());
        }
        TimeFormat format = getFormat(duration.getUnit());
        return format.decorate(duration, format.format(duration));
    }

    public String format(List<Duration> durations) {
        if (durations == null || durations.isEmpty()) {
            return format(now());
        }
        StringBuilder result = new StringBuilder();
        Duration duration = null;
        TimeFormat format = null;
        for (int i = 0; i < durations.size(); i++) {
            duration = (Duration) durations.get(i);
            format = getFormat(duration.getUnit());
            if (i < durations.size() - 1) {
                result.append(format.formatUnrounded(duration)).append(" ");
            } else {
                result.append(format.format(duration));
            }
        }
        return format.decorateUnrounded(duration, result.toString());
    }

    public String formatUnrounded(Date then) {
        if (then == null) {
            then = now();
        }
        return formatUnrounded(approximateDuration(then));
    }

    public String formatUnrounded(Calendar then) {
        if (then == null) {
            return formatUnrounded(now());
        }
        return formatUnrounded(then.getTime());
    }

    public String formatUnrounded(Duration duration) {
        if (duration == null) {
            return formatUnrounded(now());
        }
        TimeFormat format = getFormat(duration.getUnit());
        return format.decorateUnrounded(duration, format.formatUnrounded(duration));
    }

    public String formatUnrounded(List<Duration> durations) {
        if (durations == null || durations.isEmpty()) {
            return format(now());
        }
        StringBuilder result = new StringBuilder();
        Duration duration = null;
        TimeFormat format = null;
        for (int i = 0; i < durations.size(); i++) {
            duration = (Duration) durations.get(i);
            format = getFormat(duration.getUnit());
            result.append(format.formatUnrounded(duration));
            if (i < durations.size() - 1) {
                result.append(" ");
            }
        }
        return format.decorateUnrounded(duration, result.toString());
    }

    public String formatDuration(Date then) {
        return formatDuration(approximateDuration(then));
    }

    public String formatDuration(Calendar then) {
        if (then == null) {
            return formatDuration(now());
        }
        return formatDuration(approximateDuration(then.getTime()));
    }

    public String formatDuration(Duration duration) {
        if (duration == null) {
            return format(now());
        }
        return getFormat(duration.getUnit()).format(duration);
    }

    public String formatDuration(List<Duration> durations) {
        if (durations == null || durations.isEmpty()) {
            return format(now());
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < durations.size(); i++) {
            Duration duration = (Duration) durations.get(i);
            TimeFormat format = getFormat(duration.getUnit());
            if (i < durations.size() - 1) {
                result.append(format.formatUnrounded(duration)).append(" ");
            } else {
                result.append(format.format(duration));
            }
        }
        return result.toString();
    }

    public String formatDurationUnrounded(Date then) {
        return formatDurationUnrounded(approximateDuration(then));
    }

    public String formatDurationUnrounded(Calendar then) {
        if (then == null) {
            return formatDuration(now());
        }
        return formatDurationUnrounded(approximateDuration(then.getTime()));
    }

    public String formatDurationUnrounded(Duration duration) {
        if (duration == null) {
            return format(now());
        }
        return getFormat(duration.getUnit()).formatUnrounded(duration);
    }

    public String formatDurationUnrounded(List<Duration> durations) {
        if (durations == null || durations.isEmpty()) {
            return format(now());
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < durations.size(); i++) {
            Duration duration = (Duration) durations.get(i);
            result.append(getFormat(duration.getUnit()).formatUnrounded(duration));
            if (i < durations.size() - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    public TimeFormat getFormat(TimeUnit unit) {
        if (unit == null || this.units.get(unit) == null) {
            return null;
        }
        return (TimeFormat) this.units.get(unit);
    }

    public Date getReference() {
        return this.reference;
    }

    public PrettyTime setReference(Date timestamp) {
        this.reference = timestamp;
        return this;
    }

    public List<TimeUnit> getUnits() {
        if (this.cachedUnits == null) {
            List<TimeUnit> result = new ArrayList(this.units.keySet());
            Collections.sort(result, new TimeUnitComparator());
            this.cachedUnits = Collections.unmodifiableList(result);
        }
        return this.cachedUnits;
    }

    public <UNIT extends TimeUnit> UNIT getUnit(Class<UNIT> unitType) {
        if (unitType == null) {
            return null;
        }
        for (TimeUnit unit : this.units.keySet()) {
            if (unitType.isAssignableFrom(unit.getClass())) {
                return unit;
            }
        }
        return null;
    }

    public PrettyTime registerUnit(TimeUnit unit, TimeFormat format) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit to register must not be null.");
        } else if (format == null) {
            throw new IllegalArgumentException("Format to register must not be null.");
        } else {
            this.cachedUnits = null;
            this.units.put(unit, format);
            if (unit instanceof LocaleAware) {
                ((LocaleAware) unit).setLocale(this.locale);
            }
            if (format instanceof LocaleAware) {
                ((LocaleAware) format).setLocale(this.locale);
            }
            return this;
        }
    }

    public <UNIT extends TimeUnit> TimeFormat removeUnit(Class<UNIT> unitType) {
        if (unitType == null) {
            return null;
        }
        for (TimeUnit unit : this.units.keySet()) {
            if (unitType.isAssignableFrom(unit.getClass())) {
                this.cachedUnits = null;
                return (TimeFormat) this.units.remove(unit);
            }
        }
        return null;
    }

    public TimeFormat removeUnit(TimeUnit unit) {
        if (unit == null) {
            return null;
        }
        this.cachedUnits = null;
        return (TimeFormat) this.units.remove(unit);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public PrettyTime setLocale(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        this.locale = locale;
        for (TimeUnit unit : this.units.keySet()) {
            if (unit instanceof LocaleAware) {
                ((LocaleAware) unit).setLocale(locale);
            }
        }
        for (TimeFormat format : this.units.values()) {
            if (format instanceof LocaleAware) {
                ((LocaleAware) format).setLocale(locale);
            }
        }
        return this;
    }

    public String toString() {
        return "PrettyTime [reference=" + this.reference + ", locale=" + this.locale + "]";
    }

    public List<TimeUnit> clearUnits() {
        List<TimeUnit> result = getUnits();
        this.cachedUnits = null;
        this.units.clear();
        return result;
    }

    private Date now() {
        return new Date();
    }

    private void initTimeUnits() {
        addUnit(new JustNow());
        addUnit(new Millisecond());
        addUnit(new Second());
        addUnit(new Minute());
        addUnit(new Hour());
        addUnit(new Day());
        addUnit(new Week());
        addUnit(new Month());
        addUnit(new Year());
        addUnit(new Decade());
        addUnit(new Century());
        addUnit(new Millennium());
    }

    private void addUnit(ResourcesTimeUnit unit) {
        registerUnit(unit, new ResourcesTimeFormat(unit));
    }

    private Duration calculateDuration(long difference) {
        long absoluteDifference = Math.abs(difference);
        List<TimeUnit> localUnits = getUnits();
        DurationImpl result = new DurationImpl();
        int i = 0;
        while (i < localUnits.size()) {
            TimeUnit unit = (TimeUnit) localUnits.get(i);
            long millisPerUnit = Math.abs(unit.getMillisPerUnit());
            long quantity = Math.abs(unit.getMaxQuantity());
            boolean isLastUnit = i == localUnits.size() + -1;
            if (0 == quantity && !isLastUnit) {
                quantity = ((TimeUnit) localUnits.get(i + 1)).getMillisPerUnit() / unit.getMillisPerUnit();
            }
            if (millisPerUnit * quantity > absoluteDifference || isLastUnit) {
                result.setUnit(unit);
                if (millisPerUnit > absoluteDifference) {
                    result.setQuantity(getSign(difference));
                    result.setDelta(0);
                } else {
                    result.setQuantity(difference / millisPerUnit);
                    result.setDelta(difference - (result.getQuantity() * millisPerUnit));
                }
                return result;
            }
            i++;
        }
        return result;
    }

    private long getSign(long difference) {
        if (0 > difference) {
            return -1;
        }
        return 1;
    }
}
