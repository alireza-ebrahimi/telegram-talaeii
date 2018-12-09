package com.persianswitch.sdk.base.webservice.data;

import android.content.Context;
import android.util.Base64;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.security.DeviceInfo;
import com.persianswitch.sdk.base.utils.CertificateUtils;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import com.persianswitch.sdk.payment.SDKConfig;
import org.json.JSONObject;

public class WSTranRequest extends WSRequest {
    /* renamed from: a */
    private String f7278a = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: b */
    private String f7279b = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: c */
    private long f7280c = 0;

    private static final class JsonParser implements Jsonable<WSTranRequest> {
        private JsonParser() {
        }

        /* renamed from: a */
        public JSONObject m10949a(WSTranRequest wSTranRequest) {
            try {
                JSONObject a = new JsonParser().m10898a(wSTranRequest);
                if (wSTranRequest.f7278a != null) {
                    a.put("an", wSTranRequest.f7278a);
                }
                if (wSTranRequest.f7279b != null) {
                    a.put("nn", wSTranRequest.f7279b);
                }
                a.put("ao", wSTranRequest.f7280c);
                return a;
            } catch (Exception e) {
                throw new JsonWriteException(e.getMessage());
            }
        }
    }

    private WSTranRequest() {
    }

    /* renamed from: a */
    public static WSTranRequest m10950a(Context context, Config config, int i, long j) {
        WSTranRequest wSTranRequest = new WSTranRequest();
        wSTranRequest.m10922b(BaseSetting.m10461c(context));
        wSTranRequest.m10917a(BaseSetting.m10468f(context));
        wSTranRequest.m10926c(DeviceInfo.m10721a(context, config));
        wSTranRequest.m10928d(BaseSetting.m10471h(context));
        wSTranRequest.m10915a(i);
        wSTranRequest.m10921b(BaseSetting.m10470g(context));
        wSTranRequest.m10923b(Base64.encodeToString(DeviceInfo.m10724b(context).getBytes(), 2));
        wSTranRequest.m10932f(SDKConfig.m11029a(context));
        wSTranRequest.m10934g(CertificateUtils.m10757a(context));
        wSTranRequest.f7280c = j;
        return wSTranRequest;
    }

    /* renamed from: c */
    public void m10954c(long j) {
        this.f7280c = j;
    }

    /* renamed from: h */
    public String mo3283h() {
        try {
            return new JsonParser().m10949a(this).toString();
        } catch (Exception e) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: h */
    public void m10956h(String str) {
        this.f7278a = str;
    }

    /* renamed from: i */
    protected String mo3284i() {
        return "sdk/w01";
    }

    /* renamed from: i */
    public void m10958i(String str) {
        this.f7279b = str;
    }
}
