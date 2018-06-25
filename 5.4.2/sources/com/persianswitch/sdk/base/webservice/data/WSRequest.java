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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class WSRequest {
    /* renamed from: a */
    private final String f7251a = "SHA-1";
    /* renamed from: b */
    private long f7252b;
    /* renamed from: c */
    private long f7253c;
    /* renamed from: d */
    private String f7254d;
    /* renamed from: e */
    private String f7255e;
    /* renamed from: f */
    private String f7256f;
    /* renamed from: g */
    private String f7257g;
    /* renamed from: h */
    private int f7258h;
    /* renamed from: i */
    private String f7259i;
    /* renamed from: j */
    private String f7260j;
    /* renamed from: k */
    private String f7261k;
    /* renamed from: l */
    private int f7262l;
    /* renamed from: m */
    private JSONObject f7263m;
    @Deprecated
    /* renamed from: n */
    private String[] f7264n = new String[0];
    /* renamed from: o */
    private JSONObject f7265o = new JSONObject();

    static class JsonParser implements Jsonable<WSRequest> {
        JsonParser() {
        }

        /* renamed from: a */
        public JSONObject m10898a(WSRequest wSRequest) {
            try {
                Map hashMap = new HashMap();
                hashMap.put("se", TtmlNode.ANONYMOUS_REGION_ID);
                hashMap.put("ap", Long.valueOf(wSRequest.f7253c));
                if (wSRequest.f7254d != null) {
                    hashMap.put("at", wSRequest.f7254d);
                }
                hashMap.put("op", Integer.valueOf(wSRequest.f7258h));
                hashMap.put("tr", Long.valueOf(wSRequest.f7252b));
                if (wSRequest.f7256f != null) {
                    hashMap.put("av", wSRequest.f7256f);
                }
                if (wSRequest.f7259i != null) {
                    hashMap.put("te", wSRequest.f7259i);
                }
                if (wSRequest.f7257g != null) {
                    hashMap.put("mo", wSRequest.f7257g);
                }
                hashMap.put("hi", Integer.valueOf(wSRequest.f7262l));
                if (wSRequest.f7255e != null) {
                    hashMap.put("de", wSRequest.f7255e);
                }
                hashMap.put("pn", wSRequest.m10927d());
                hashMap.put("kd", wSRequest.m10929e());
                hashMap.put("ka", wSRequest.m10931f());
                if (wSRequest.f7263m != null) {
                    hashMap.put("hd", wSRequest.f7263m);
                }
                if (wSRequest.f7264n != null) {
                    hashMap.put("ed", new JSONArray(Arrays.asList(wSRequest.f7264n)));
                }
                if (wSRequest.f7265o != null) {
                    hashMap.put("ej", wSRequest.f7265o);
                }
                return new JSONObject(hashMap);
            } catch (Exception e) {
                throw new JsonWriteException(e.getMessage());
            }
        }
    }

    /* renamed from: a */
    public static WSRequest m10900a(Context context, Config config, int i) {
        WSRequest wSRequest = new WSRequest();
        wSRequest.m10922b(BaseSetting.m10461c(context));
        wSRequest.m10917a(BaseSetting.m10468f(context));
        wSRequest.m10926c(DeviceInfo.m10721a(context, config));
        wSRequest.m10928d(BaseSetting.m10471h(context));
        wSRequest.m10915a(i);
        wSRequest.m10921b(BaseSetting.m10470g(context));
        wSRequest.m10923b(Base64.encodeToString(DeviceInfo.m10724b(context).getBytes(), 2));
        wSRequest.m10932f(SDKConfig.m11029a(context));
        wSRequest.m10934g(CertificateUtils.m10757a(context));
        return wSRequest;
    }

    /* renamed from: a */
    public long m10912a() {
        return this.f7252b;
    }

    /* renamed from: a */
    public String m10913a(Config config) {
        return String.format(Locale.US, "%s/%s/%d/%d", new Object[]{config.mo3291a(), m10914a("1", "6", true), Long.valueOf(m10920b()), Integer.valueOf(m10933g())});
    }

    /* renamed from: a */
    public String m10914a(String str, String str2, boolean z) {
        int parseInt = Integer.parseInt(str2);
        if (z) {
            parseInt += 10000;
        }
        return String.format(Locale.US, "%s/%s/%d", new Object[]{mo3284i(), str, Integer.valueOf(parseInt)});
    }

    /* renamed from: a */
    public void m10915a(int i) {
        this.f7258h = i;
    }

    /* renamed from: a */
    public void m10916a(long j) {
        this.f7252b = j;
    }

    /* renamed from: a */
    public void m10917a(String str) {
        this.f7254d = str;
    }

    /* renamed from: a */
    public void m10918a(JSONObject jSONObject) {
        this.f7263m = jSONObject;
    }

    @Deprecated
    /* renamed from: a */
    public void m10919a(String[] strArr) {
        this.f7264n = strArr;
    }

    /* renamed from: b */
    public long m10920b() {
        return this.f7253c;
    }

    /* renamed from: b */
    public void m10921b(int i) {
        this.f7262l = i;
    }

    /* renamed from: b */
    public void m10922b(long j) {
        this.f7253c = j;
    }

    /* renamed from: b */
    public void m10923b(String str) {
        this.f7255e = str;
    }

    /* renamed from: b */
    public void m10924b(JSONObject jSONObject) {
        this.f7265o = jSONObject;
    }

    /* renamed from: c */
    public int m10925c() {
        return this.f7258h;
    }

    /* renamed from: c */
    public void m10926c(String str) {
        this.f7256f = str;
    }

    /* renamed from: d */
    public String m10927d() {
        return this.f7260j;
    }

    /* renamed from: d */
    public void m10928d(String str) {
        this.f7257g = str;
    }

    /* renamed from: e */
    public String m10929e() {
        return this.f7261k;
    }

    /* renamed from: e */
    public void m10930e(String str) {
        this.f7259i = str;
    }

    /* renamed from: f */
    public String m10931f() {
        return "SHA-1";
    }

    /* renamed from: f */
    public void m10932f(String str) {
        this.f7260j = str;
    }

    /* renamed from: g */
    public int m10933g() {
        return this.f7262l;
    }

    /* renamed from: g */
    public void m10934g(String str) {
        this.f7261k = str;
    }

    /* renamed from: h */
    public String mo3283h() {
        try {
            return new JsonParser().m10898a(this).toString();
        } catch (Exception e) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: i */
    protected String mo3284i() {
        return "sdk/w01";
    }
}
