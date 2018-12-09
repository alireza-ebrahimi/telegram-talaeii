package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.webservice.OpCode;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class PaymentRequest extends AbsRequest {
    /* renamed from: a */
    private String f7597a;
    /* renamed from: b */
    private String f7598b;
    /* renamed from: c */
    private String f7599c;
    /* renamed from: d */
    private String f7600d;
    /* renamed from: e */
    private String f7601e;

    public PaymentRequest() {
        super(OpCode.PAY_TRANSACTION);
    }

    /* renamed from: d */
    public JSONObject mo3301d() {
        Map hashMap = new HashMap();
        hashMap.put("sd", this.f7597a);
        hashMap.put("mc", this.f7598b);
        hashMap.put(TtmlNode.ATTR_ID, TtmlNode.ANONYMOUS_REGION_ID);
        hashMap.put("pi", this.f7599c);
        hashMap.put("dm", this.f7600d);
        hashMap.put("tk", this.f7601e);
        return Json.m10761a(hashMap);
    }

    /* renamed from: d */
    public void mo3303d(String str) {
        this.f7597a = str;
    }

    /* renamed from: e */
    public void m11315e(String str) {
        this.f7598b = str;
    }

    /* renamed from: e */
    public String[] mo3302e() {
        return new String[0];
    }

    /* renamed from: f */
    public void m11317f(String str) {
        this.f7599c = str;
    }

    /* renamed from: g */
    public void m11318g(String str) {
        this.f7600d = str;
    }

    /* renamed from: h */
    public void m11319h(String str) {
        this.f7601e = str;
    }
}
