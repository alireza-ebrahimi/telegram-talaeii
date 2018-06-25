package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.webservice.OpCode;
import org.json.JSONObject;

public final class TrustRequest extends AbsRequest {
    TrustRequest(OpCode opCode) {
        super(opCode);
    }

    public JSONObject toExtraData() {
        return new JSONObject();
    }

    public String[] toLegacyExtraData() {
        return new String[0];
    }
}
