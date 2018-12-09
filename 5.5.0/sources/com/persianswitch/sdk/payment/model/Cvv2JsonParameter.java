package com.persianswitch.sdk.payment.model;

import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class Cvv2JsonParameter {
    /* renamed from: a */
    public String f7492a;
    /* renamed from: b */
    public String f7493b;
    /* renamed from: c */
    public String f7494c;
    /* renamed from: d */
    public String f7495d;
    /* renamed from: e */
    public String f7496e;
    /* renamed from: f */
    public String f7497f;
    /* renamed from: g */
    public String f7498g;
    /* renamed from: h */
    public String f7499h;

    public static class JsonParser implements Jsonable<Cvv2JsonParameter> {
        /* renamed from: a */
        public JSONObject m11144a(Cvv2JsonParameter cvv2JsonParameter) {
            Map hashMap = new HashMap(10);
            if (cvv2JsonParameter.f7492a != null) {
                hashMap.put("hid", cvv2JsonParameter.f7492a);
            }
            if (cvv2JsonParameter.f7493b != null) {
                hashMap.put("ccv", cvv2JsonParameter.f7493b);
            }
            if (cvv2JsonParameter.f7494c != null) {
                hashMap.put("op", cvv2JsonParameter.f7494c);
            }
            if (cvv2JsonParameter.f7495d != null) {
                hashMap.put("am", cvv2JsonParameter.f7495d);
            }
            if (cvv2JsonParameter.f7496e != null) {
                hashMap.put("bnk", cvv2JsonParameter.f7496e);
            }
            if (cvv2JsonParameter.f7497f != null) {
                hashMap.put("mrc", cvv2JsonParameter.f7497f);
            }
            if (cvv2JsonParameter.f7498g != null) {
                hashMap.put("src", cvv2JsonParameter.f7498g);
            }
            if (cvv2JsonParameter.f7499h != null) {
                hashMap.put("mno", cvv2JsonParameter.f7499h);
            }
            return Json.m10761a(hashMap);
        }
    }

    /* renamed from: a */
    public String m11145a() {
        try {
            return new JsonParser().m11144a(this).toString();
        } catch (JsonWriteException e) {
            return "{}";
        }
    }
}
