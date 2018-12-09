package org.telegram.messenger.time;

import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public interface DateParser {
    Locale getLocale();

    String getPattern();

    TimeZone getTimeZone();

    Date parse(String str);

    Date parse(String str, ParsePosition parsePosition);

    Object parseObject(String str);

    Object parseObject(String str, ParsePosition parsePosition);
}
