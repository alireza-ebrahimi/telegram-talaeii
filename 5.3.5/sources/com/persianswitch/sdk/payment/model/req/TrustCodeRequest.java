package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.webservice.OpCode;
import org.json.JSONObject;

public class TrustCodeRequest extends AbsRequest {
    public TrustCodeRequest() {
        super(OpCode.GET_TRUST_CODE);
    }

    public JSONObject toExtraData() {
        return new JSONObject();
    }

    public String[] toLegacyExtraData() {
        return new String[0];
    }
}
