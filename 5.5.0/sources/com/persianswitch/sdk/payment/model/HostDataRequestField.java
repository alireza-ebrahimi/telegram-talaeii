package com.persianswitch.sdk.payment.model;

import android.content.Context;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import com.persianswitch.sdk.payment.model.req.AbsRequest;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class HostDataRequestField {
    /* renamed from: a */
    private final String f7500a;
    /* renamed from: b */
    private final String f7501b;
    /* renamed from: c */
    private final String f7502c;
    /* renamed from: d */
    private final String f7503d;

    private static class EntityJsonParser implements Jsonable<HostDataRequestField> {
        private EntityJsonParser() {
        }

        /* renamed from: a */
        public JSONObject m11146a(HostDataRequestField hostDataRequestField) {
            try {
                Map hashMap = new HashMap();
                hashMap.put("hreq", hostDataRequestField.f7500a);
                hashMap.put("hsign", hostDataRequestField.f7501b);
                hashMap.put("ver", hostDataRequestField.f7502c);
                hashMap.put("hver", hostDataRequestField.f7503d);
                return Json.m10761a(hashMap);
            } catch (Exception e) {
                throw new JsonWriteException(e.getMessage());
            }
        }
    }

    public HostDataRequestField(String str, String str2, String str3, String str4) {
        this.f7500a = str;
        this.f7501b = str2;
        this.f7502c = str3;
        this.f7503d = str4;
    }

    /* renamed from: a */
    public static HostDataRequestField m11147a(Context context) {
        return new HostDataRequestField(null, null, null, AbsRequest.m11282b(context));
    }

    /* renamed from: a */
    public JSONObject m11152a() {
        try {
            return new EntityJsonParser().m11146a(this);
        } catch (JsonWriteException e) {
            SDKLog.m10661c("HostDataRequestField", "error while parse json for host data request", new Object[0]);
            return new JSONObject();
        }
    }
}
