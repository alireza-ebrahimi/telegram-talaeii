package com.persianswitch.sdk.payment.model.res;

import android.content.Context;
import com.google.android.gms.common.internal.ImagesContract;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import org.json.JSONException;
import org.json.JSONObject;

public final class TrustResponse extends AbsResponse {
    /* renamed from: a */
    private String f7641a;
    /* renamed from: b */
    private String f7642b;
    /* renamed from: c */
    private String f7643c;
    /* renamed from: d */
    private String f7644d;
    /* renamed from: e */
    private boolean f7645e;
    /* renamed from: f */
    private boolean f7646f;

    public TrustResponse(Context context, WSResponse wSResponse) {
        super(context, wSResponse);
    }

    /* renamed from: a */
    public String m11381a() {
        return this.f7641a;
    }

    /* renamed from: a */
    void mo3305a(Context context, JSONObject jSONObject) {
        try {
            if (jSONObject.has("ussd")) {
                this.f7641a = jSONObject.getString("ussd");
            }
            if (jSONObject.has("code")) {
                this.f7642b = jSONObject.getString("code");
            }
            if (jSONObject.has(ImagesContract.URL)) {
                this.f7643c = jSONObject.getString(ImagesContract.URL);
            }
            if (jSONObject.has("desc")) {
                this.f7644d = jSONObject.getString("desc");
            }
            if (jSONObject.has("originality") && jSONObject.getString("originality").length() >= 2) {
                this.f7645e = "1".equals(jSONObject.getString("originality").substring(0, 1));
                this.f7646f = "1".equals(jSONObject.getString("originality").substring(1, 2));
            }
        } catch (JSONException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    /* renamed from: b */
    public String m11383b() {
        return this.f7642b;
    }

    /* renamed from: c */
    public String m11384c() {
        return this.f7643c;
    }

    /* renamed from: d */
    public String m11385d() {
        return this.f7644d;
    }

    /* renamed from: e */
    public boolean m11386e() {
        return this.f7645e;
    }

    /* renamed from: f */
    public boolean m11387f() {
        return this.f7646f;
    }
}
