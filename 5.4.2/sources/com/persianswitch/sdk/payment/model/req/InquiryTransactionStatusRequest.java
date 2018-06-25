package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.OpCode;
import org.json.JSONObject;

public class InquiryTransactionStatusRequest extends AbsRequest {
    /* renamed from: a */
    private String f7594a;
    /* renamed from: b */
    private String f7595b;
    /* renamed from: c */
    private String f7596c;

    public InquiryTransactionStatusRequest() {
        super(OpCode.INQUIRY_TRANSACTION);
    }

    /* renamed from: d */
    public JSONObject mo3301d() {
        return new JSONObject();
    }

    /* renamed from: e */
    public void m11309e(String str) {
        this.f7594a = str;
    }

    /* renamed from: e */
    public String[] mo3302e() {
        return new String[]{StringUtils.m10800a(this.f7594a), StringUtils.m10800a(this.f7595b), StringUtils.m10800a(this.f7596c), TtmlNode.ANONYMOUS_REGION_ID};
    }

    /* renamed from: f */
    public void m11311f(String str) {
        this.f7595b = str;
    }

    /* renamed from: g */
    public void m11312g(String str) {
        this.f7596c = str;
    }
}
