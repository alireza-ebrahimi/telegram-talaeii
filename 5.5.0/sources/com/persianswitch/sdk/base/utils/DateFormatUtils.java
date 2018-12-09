package com.persianswitch.sdk.base.utils;

import com.persianswitch.sdk.base.utils.pdate.CalendarConstants;
import com.persianswitch.sdk.base.utils.pdate.DateTime;
import com.persianswitch.sdk.base.utils.pdate.DateTimeConverter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateFormatUtils {
    /* renamed from: a */
    public static String m10759a(Date date, boolean z) {
        if (date == null) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        DateTime a = z ? DateTimeConverter.m10786a().m10787a(DateTime.m10778a(date)) : DateTime.m10778a(date);
        int a2 = a.m10779a();
        int b = a.m10781b();
        int c = a.m10782c();
        if (z) {
            String str = CalendarConstants.f7107a[b - 1];
        } else {
            new SimpleDateFormat("MMMM", Locale.US).format(date);
        }
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(date.getTime());
        int i = instance.get(11);
        int i2 = instance.get(12);
        return String.format(Locale.US, "%04d/%02d/%02d %02d:%02d", new Object[]{Integer.valueOf(a2), Integer.valueOf(b), Integer.valueOf(c), Integer.valueOf(i), Integer.valueOf(i2)});
    }
}
