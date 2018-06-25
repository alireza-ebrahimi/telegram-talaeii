package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.payment.model.req.ClientSyncRequest.EntityJsonParser;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class InquiryMerchantRequest extends AbsRequest {
    private static final String TAG = "InquiryMerchantRequest";
    private ClientSyncRequest mClientSyncRequest;

    public InquiryMerchantRequest() {
        super(OpCode.INQUIRY_MERCHANT);
    }

    public void setClientSyncRequest(ClientSyncRequest clientSyncRequest) {
        this.mClientSyncRequest = clientSyncRequest;
    }

    public JSONObject toExtraData() {
        HashMap<String, Object> extraData = new HashMap();
        extraData.put("mc", "");
        extraData.put("id", "");
        try {
            extraData.put("sy", new EntityJsonParser().toJson(this.mClientSyncRequest));
        } catch (JSONException e) {
            SDKLog.m36e(TAG, "error while parse to json", e, new Object[0]);
        }
        return Json.toJson(extraData);
    }

    public String[] toLegacyExtraData() {
        return new String[0];
    }
}
