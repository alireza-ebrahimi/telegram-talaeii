package org.ocpsoft.prettytime.impl;

import java.util.Locale;
import java.util.ResourceBundle;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.LocaleAware;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.format.SimpleTimeFormat;

public class ResourcesTimeFormat extends SimpleTimeFormat implements TimeFormat, LocaleAware<ResourcesTimeFormat> {
    private ResourceBundle bundle;
    private TimeFormat override;
    private final ResourcesTimeUnit unit;

    public ResourcesTimeFormat(ResourcesTimeUnit unit) {
        this.unit = unit;
    }

    public ResourcesTimeFormat setLocale(Locale locale) {
        this.bundle = ResourceBundle.getBundle(this.unit.getResourceBundleName(), locale);
        if (this.bundle instanceof TimeFormatProvider) {
            TimeFormat format = ((TimeFormatProvider) this.bundle).getFormatFor(this.unit);
            if (format != null) {
                this.override = format;
            }
        } else {
            this.override = null;
        }
        if (this.override == null) {
            setPattern(this.bundle.getString(this.unit.getResourceKeyPrefix() + "Pattern"));
            setFuturePrefix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FuturePrefix"));
            setFutureSuffix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FutureSuffix"));
            setPastPrefix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastPrefix"));
            setPastSuffix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastSuffix"));
            setSingularName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "SingularName"));
            setPluralName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PluralName"));
            try {
                setFuturePluralName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FuturePluralName"));
            } catch (Exception e) {
            }
            try {
                setFutureSingularName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FutureSingularName"));
            } catch (Exception e2) {
            }
            try {
                setPastPluralName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastPluralName"));
            } catch (Exception e3) {
            }
            try {
                setPastSingularName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastSingularName"));
            } catch (Exception e4) {
            }
        }
        return this;
    }

    public String decorate(Duration duration, String time) {
        return this.override == null ? super.decorate(duration, time) : this.override.decorate(duration, time);
    }

    public String decorateUnrounded(Duration duration, String time) {
        return this.override == null ? super.decorateUnrounded(duration, time) : this.override.decorateUnrounded(duration, time);
    }

    public String format(Duration duration) {
        return this.override == null ? super.format(duration) : this.override.format(duration);
    }

    public String formatUnrounded(Duration duration) {
        return this.override == null ? super.formatUnrounded(duration) : this.override.formatUnrounded(duration);
    }
}
