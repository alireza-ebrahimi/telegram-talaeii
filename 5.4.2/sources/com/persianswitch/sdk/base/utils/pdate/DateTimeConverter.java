package com.persianswitch.sdk.base.utils.pdate;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTimeConverter {
    /* renamed from: a */
    private static DateTimeConverter f7120a;
    /* renamed from: b */
    private Roozh f7121b = new Roozh();
    /* renamed from: c */
    private Calendar f7122c = GregorianCalendar.getInstance();

    /* renamed from: a */
    public static synchronized DateTimeConverter m10786a() {
        DateTimeConverter dateTimeConverter;
        synchronized (DateTimeConverter.class) {
            if (f7120a == null) {
                f7120a = new DateTimeConverter();
            }
            dateTimeConverter = f7120a;
        }
        return dateTimeConverter;
    }

    /* renamed from: a */
    public synchronized DateTime m10787a(DateTime dateTime) {
        this.f7121b.m10793a(dateTime.m10779a(), dateTime.m10781b(), dateTime.m10782c());
        return DateTime.m10777a(this.f7121b.m10795c(), this.f7121b.m10794b(), this.f7121b.m10792a(), dateTime.m10783d(), dateTime.m10784e(), dateTime.m10785f()).m10780a(DateFormat.PERSIAN);
    }
}
