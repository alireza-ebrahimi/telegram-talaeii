package org.ocpsoft.prettytime;

import java.util.Locale;

public interface LocaleAware<TYPE> {
    TYPE setLocale(Locale locale);
}
