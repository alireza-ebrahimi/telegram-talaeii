package com.p111h.p112a.p117e;

import org.json.JSONObject;

/* renamed from: com.h.a.e.b */
public class C1993b {
    /* renamed from: a */
    static String m9010a(String str) {
        return ("null".equals(str) || TtmlNode.ANONYMOUS_REGION_ID.equals(str)) ? null : str;
    }

    /* renamed from: a */
    public static String m9011a(JSONObject jSONObject, String str) {
        return C1993b.m9010a(jSONObject.getString(str));
    }

    /* renamed from: b */
    public static String m9012b(JSONObject jSONObject, String str) {
        return C1993b.m9010a(jSONObject.optString(str));
    }
}
