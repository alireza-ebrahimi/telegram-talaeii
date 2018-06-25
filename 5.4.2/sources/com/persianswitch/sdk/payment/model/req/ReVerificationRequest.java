package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.utils.RootUtils;
import com.persianswitch.sdk.base.webservice.OpCode;
import org.json.JSONObject;

public class ReVerificationRequest extends RegisterRequest {
    public ReVerificationRequest() {
        super(OpCode.RE_VERIFICATION_APPLICATION);
    }

    /* renamed from: d */
    public JSONObject mo3301d() {
        return super.mo3301d();
    }

    /* renamed from: e */
    public String[] mo3302e() {
        String[] strArr = new String[11];
        strArr[0] = "3;";
        strArr[1] = TtmlNode.ANONYMOUS_REGION_ID;
        strArr[2] = m11333i() + TtmlNode.ANONYMOUS_REGION_ID;
        strArr[3] = "1";
        strArr[4] = m11335j();
        strArr[5] = m11338k() ? "2" : "1";
        strArr[6] = m11339l();
        strArr[7] = m11341m();
        strArr[8] = m11343n();
        strArr[9] = m11344o();
        strArr[10] = RootUtils.m10764a() ? "1" : "0";
        return strArr;
    }
}
