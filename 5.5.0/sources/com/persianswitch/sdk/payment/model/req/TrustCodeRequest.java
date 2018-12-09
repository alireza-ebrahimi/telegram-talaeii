package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.webservice.OpCode;
import org.json.JSONObject;

public class TrustCodeRequest extends AbsRequest {
    public TrustCodeRequest() {
        super(OpCode.GET_TRUST_CODE);
    }

    /* renamed from: d */
    public JSONObject mo3301d() {
        return new JSONObject();
    }

    /* renamed from: e */
    public String[] mo3302e() {
        return new String[0];
    }
}
