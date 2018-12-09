package com.p111h.p112a.p115c;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.p111h.p112a.p114b.C1977a;
import com.p111h.p112a.p114b.C1978b;
import com.p111h.p112a.p117e.C1993b;
import com.p111h.p112a.p117e.C1995d;
import java.util.Date;
import org.json.JSONObject;

/* renamed from: com.h.a.c.g */
public class C1990g {
    /* renamed from: a */
    public static C1978b m9003a(String str) {
        JSONObject jSONObject = new JSONObject(str);
        String a = C1993b.m9011a(jSONObject, TtmlNode.ATTR_ID);
        Long valueOf = Long.valueOf(jSONObject.getLong("created"));
        Boolean valueOf2 = Boolean.valueOf(jSONObject.getBoolean("livemode"));
        String f = C1995d.m9020f(C1993b.m9011a(jSONObject, Param.TYPE));
        Boolean valueOf3 = Boolean.valueOf(jSONObject.getBoolean("used"));
        C1977a a2 = C1980a.m8973a(jSONObject.getJSONObject("card"));
        return new C1978b(a, valueOf2.booleanValue(), new Date(valueOf.longValue() * 1000), valueOf3, a2, f);
    }
}
