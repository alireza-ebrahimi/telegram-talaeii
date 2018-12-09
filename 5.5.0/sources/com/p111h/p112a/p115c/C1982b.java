package com.p111h.p112a.p115c;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.p111h.p112a.p117e.C1993b;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.h.a.c.b */
class C1982b {

    /* renamed from: com.h.a.c.b$a */
    static class C1981a {
        /* renamed from: a */
        public String f5847a;
        /* renamed from: b */
        public String f5848b;
        /* renamed from: c */
        public String f5849c;
        /* renamed from: d */
        public String f5850d;
        /* renamed from: e */
        public String f5851e;
        /* renamed from: f */
        public String f5852f;

        C1981a() {
        }
    }

    /* renamed from: a */
    static C1981a m8974a(String str) {
        C1981a c1981a = new C1981a();
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("error");
            c1981a.f5852f = C1993b.m9012b(jSONObject, "charge");
            c1981a.f5849c = C1993b.m9012b(jSONObject, "code");
            c1981a.f5851e = C1993b.m9012b(jSONObject, "decline_code");
            c1981a.f5848b = C1993b.m9012b(jSONObject, "message");
            c1981a.f5850d = C1993b.m9012b(jSONObject, "param");
            c1981a.f5847a = C1993b.m9012b(jSONObject, Param.TYPE);
        } catch (JSONException e) {
            c1981a.f5848b = "An improperly formatted error response was found.";
        }
        return c1981a;
    }
}
