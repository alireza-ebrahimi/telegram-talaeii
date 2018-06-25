package org.ocpsoft.prettytime.format;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;

public class SimpleTimeFormat implements TimeFormat {
    private static final String NEGATIVE = "-";
    public static final String QUANTITY = "%n";
    public static final String SIGN = "%s";
    public static final String UNIT = "%u";
    private String futurePluralName = "";
    private String futurePrefix = "";
    private String futureSingularName = "";
    private String futureSuffix = "";
    private String pastPluralName = "";
    private String pastPrefix = "";
    private String pastSingularName = "";
    private String pastSuffix = "";
    private String pattern = "";
    private String pluralName = "";
    private int roundingTolerance = 50;
    private String singularName = "";

    public String format(Duration duration) {
        return format(duration, true);
    }

    public String formatUnrounded(Duration duration) {
        return format(duration, false);
    }

    public String decorate(Duration duration, String time) {
        StringBuilder result = new StringBuilder();
        if (duration.isInPast()) {
            result.append(this.pastPrefix).append(" ").append(time).append(" ").append(this.pastSuffix);
        } else {
            result.append(this.futurePrefix).append(" ").append(time).append(" ").append(this.futureSuffix);
        }
        return result.toString().replaceAll("\\s+", " ").trim();
    }

    public String decorateUnrounded(Duration duration, String time) {
        return decorate(duration, time);
    }

    private String format(Duration duration, boolean round) {
        return applyPattern(getSign(duration), getGramaticallyCorrectName(duration, round), getQuantity(duration, round));
    }

    private String applyPattern(String sign, String unit, long quantity) {
        return getPattern(quantity).replaceAll("%s", sign).replaceAll("%n", String.valueOf(quantity)).replaceAll("%u", unit);
    }

    protected String getPattern(long quantity) {
        return this.pattern;
    }

    public String getPattern() {
        return this.pattern;
    }

    protected long getQuantity(Duration duration, boolean round) {
        return Math.abs(round ? duration.getQuantityRounded(this.roundingTolerance) : duration.getQuantity());
    }

    protected String getGramaticallyCorrectName(Duration d, boolean round) {
        String result = getSingularName(d);
        if (Math.abs(getQuantity(d, round)) == 0 || Math.abs(getQuantity(d, round)) > 1) {
            return getPluralName(d);
        }
        return result;
    }

    private String getSign(Duration d) {
        if (d.getQuantity() < 0) {
            return NEGATIVE;
        }
        return "";
    }

    private String getSingularName(Duration duration) {
        if (duration.isInFuture() && this.futureSingularName != null && this.futureSingularName.length() > 0) {
            return this.futureSingularName;
        }
        if (!duration.isInPast() || this.pastSingularName == null || this.pastSingularName.length() <= 0) {
            return this.singularName;
        }
        return this.pastSingularName;
    }

    private String getPluralName(Duration duration) {
        if (duration.isInFuture() && this.futurePluralName != null && this.futureSingularName.length() > 0) {
            return this.futurePluralName;
        }
        if (!duration.isInPast() || this.pastPluralName == null || this.pastSingularName.length() <= 0) {
            return this.pluralName;
        }
        return this.pastPluralName;
    }

    public SimpleTimeFormat setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public SimpleTimeFormat setFuturePrefix(String futurePrefix) {
        this.futurePrefix = futurePrefix.trim();
        return this;
    }

    public SimpleTimeFormat setFutureSuffix(String futureSuffix) {
        this.futureSuffix = futureSuffix.trim();
        return this;
    }

    public SimpleTimeFormat setPastPrefix(String pastPrefix) {
        this.pastPrefix = pastPrefix.trim();
        return this;
    }

    public SimpleTimeFormat setPastSuffix(String pastSuffix) {
        this.pastSuffix = pastSuffix.trim();
        return this;
    }

    public SimpleTimeFormat setRoundingTolerance(int roundingTolerance) {
        this.roundingTolerance = roundingTolerance;
        return this;
    }

    public SimpleTimeFormat setSingularName(String name) {
        this.singularName = name;
        return this;
    }

    public SimpleTimeFormat setPluralName(String pluralName) {
        this.pluralName = pluralName;
        return this;
    }

    public SimpleTimeFormat setFutureSingularName(String futureSingularName) {
        this.futureSingularName = futureSingularName;
        return this;
    }

    public SimpleTimeFormat setFuturePluralName(String futurePluralName) {
        this.futurePluralName = futurePluralName;
        return this;
    }

    public SimpleTimeFormat setPastSingularName(String pastSingularName) {
        this.pastSingularName = pastSingularName;
        return this;
    }

    public SimpleTimeFormat setPastPluralName(String pastPluralName) {
        this.pastPluralName = pastPluralName;
        return this;
    }

    public String toString() {
        return "SimpleTimeFormat [pattern=" + this.pattern + ", futurePrefix=" + this.futurePrefix + ", futureSuffix=" + this.futureSuffix + ", pastPrefix=" + this.pastPrefix + ", pastSuffix=" + this.pastSuffix + ", roundingTolerance=" + this.roundingTolerance + "]";
    }
}
