package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.payment.model.req.ClientSyncRequest.EntityJsonParser;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class InquiryMerchantRequest extends AbsRequest {
    /* renamed from: a */
    private ClientSyncRequest f7593a;

    public InquiryMerchantRequest() {
        super(OpCode.INQUIRY_MERCHANT);
    }

    /* renamed from: a */
    public void m11305a(ClientSyncRequest clientSyncRequest) {
        this.f7593a = clientSyncRequest;
    }

    /* renamed from: d */
    public JSONObject mo3301d() {
        Map hashMap = new HashMap();
        hashMap.put("mc", TtmlNode.ANONYMOUS_REGION_ID);
        hashMap.put(TtmlNode.ATTR_ID, TtmlNode.ANONYMOUS_REGION_ID);
        try {
            hashMap.put("sy", new EntityJsonParser().m11300a(this.f7593a));
        } catch (Throwable e) {
            SDKLog.m10659b("InquiryMerchantRequest", "error while parse to json", e, new Object[0]);
        }
        return Json.m10761a(hashMap);
    }

    /* renamed from: e */
    public String[] mo3302e() {
        return new String[0];
    }
}
