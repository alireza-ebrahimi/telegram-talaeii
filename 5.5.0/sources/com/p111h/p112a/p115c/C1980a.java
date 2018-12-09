package com.p111h.p112a.p115c;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import com.p111h.p112a.p114b.C1977a;
import com.p111h.p112a.p117e.C1993b;
import com.p111h.p112a.p117e.C1995d;
import org.json.JSONObject;

/* renamed from: com.h.a.c.a */
public class C1980a {
    /* renamed from: a */
    public static C1977a m8973a(JSONObject jSONObject) {
        return new C1977a(null, Integer.valueOf(jSONObject.getInt("exp_month")), Integer.valueOf(jSONObject.getInt("exp_year")), null, C1993b.m9012b(jSONObject, "name"), C1993b.m9012b(jSONObject, "address_line1"), C1993b.m9012b(jSONObject, "address_line2"), C1993b.m9012b(jSONObject, "address_city"), C1993b.m9012b(jSONObject, "address_state"), C1993b.m9012b(jSONObject, "address_zip"), C1993b.m9012b(jSONObject, "address_country"), C1995d.m9018d(C1993b.m9012b(jSONObject, "brand")), C1993b.m9012b(jSONObject, "last4"), C1993b.m9012b(jSONObject, "fingerprint"), C1995d.m9019e(C1993b.m9012b(jSONObject, "funding")), C1993b.m9012b(jSONObject, "country"), C1993b.m9012b(jSONObject, C1797b.CURRENCY));
    }
}
