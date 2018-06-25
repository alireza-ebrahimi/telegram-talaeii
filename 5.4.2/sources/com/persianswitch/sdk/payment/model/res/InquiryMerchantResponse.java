package com.persianswitch.sdk.payment.model.res;

import android.content.Context;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.model.CVV2Status;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.repo.CardRepo;
import org.json.JSONObject;

public class InquiryMerchantResponse extends AbsResponse {
    /* renamed from: a */
    private String f7626a;
    /* renamed from: b */
    private CVV2Status f7627b;
    /* renamed from: c */
    private UserCard f7628c;
    /* renamed from: d */
    private String f7629d;
    /* renamed from: e */
    private String f7630e;
    /* renamed from: f */
    private String f7631f;
    /* renamed from: g */
    private String f7632g;
    /* renamed from: h */
    private String f7633h;
    /* renamed from: i */
    private String f7634i;
    /* renamed from: j */
    private String f7635j;
    /* renamed from: k */
    private String f7636k;
    /* renamed from: l */
    private String f7637l;
    /* renamed from: m */
    private String f7638m;
    /* renamed from: n */
    private String f7639n;
    /* renamed from: o */
    private JSONObject f7640o;

    public InquiryMerchantResponse(Context context, WSResponse wSResponse) {
        super(context, wSResponse);
    }

    /* renamed from: a */
    public String m11366a() {
        return this.f7626a;
    }

    /* renamed from: a */
    void mo3305a(Context context, JSONObject jSONObject) {
        try {
            if (jSONObject.has("sd")) {
                this.f7626a = jSONObject.getString("sd");
            }
            if (jSONObject.has("cv")) {
                this.f7627b = CVV2Status.m11120a(jSONObject.getInt("cv"));
            }
            if (jSONObject.has("mc")) {
                this.f7629d = jSONObject.getInt("mc") + TtmlNode.ANONYMOUS_REGION_ID;
            }
            if (jSONObject.has("mn")) {
                this.f7630e = jSONObject.getString("mn");
            }
            if (jSONObject.has("lg")) {
                this.f7631f = jSONObject.getString("lg");
            }
            if (jSONObject.has("ds")) {
                this.f7632g = jSONObject.getString("ds");
            }
            if (jSONObject.has("ps")) {
                this.f7635j = jSONObject.getInt("ps") + TtmlNode.ANONYMOUS_REGION_ID;
            }
            if (jSONObject.has("pi")) {
                this.f7636k = jSONObject.getString("pi");
            }
            if (jSONObject.has("dm")) {
                this.f7637l = jSONObject.getString("dm");
            }
            if (jSONObject.has("as")) {
                this.f7633h = jSONObject.getInt("as") + TtmlNode.ANONYMOUS_REGION_ID;
            }
            if (jSONObject.has("am")) {
                this.f7634i = jSONObject.getLong("am") + TtmlNode.ANONYMOUS_REGION_ID;
            }
            if (jSONObject.has("tk")) {
                this.f7638m = jSONObject.getString("tk");
            }
            if (jSONObject.has("ia")) {
                this.f7639n = jSONObject.getInt("ia") + TtmlNode.ANONYMOUS_REGION_ID;
            }
            if (jSONObject.has("sy")) {
                this.f7640o = jSONObject.getJSONObject("sy");
            }
            if (jSONObject.has("ca")) {
                Long d = StringUtils.m10808d(jSONObject.getString("ca"));
                if (d != null && d.longValue() > 0) {
                    this.f7628c = (UserCard) new CardRepo(context).m10610a((Object) d);
                }
            }
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    /* renamed from: b */
    public CVV2Status m11368b() {
        return this.f7627b;
    }

    /* renamed from: c */
    public UserCard m11369c() {
        return this.f7628c;
    }

    /* renamed from: d */
    public String m11370d() {
        return this.f7629d;
    }

    /* renamed from: e */
    public String m11371e() {
        return this.f7630e;
    }

    /* renamed from: f */
    public String m11372f() {
        return this.f7631f;
    }

    /* renamed from: g */
    public String m11373g() {
        return this.f7632g;
    }

    /* renamed from: h */
    public String m11374h() {
        return this.f7633h;
    }

    /* renamed from: i */
    public String m11375i() {
        return this.f7634i;
    }

    /* renamed from: j */
    public String m11376j() {
        return this.f7636k;
    }

    /* renamed from: k */
    public String m11377k() {
        return this.f7637l;
    }

    /* renamed from: l */
    public String m11378l() {
        return this.f7638m;
    }

    /* renamed from: m */
    public String m11379m() {
        return this.f7639n;
    }

    /* renamed from: n */
    public JSONObject m11380n() {
        return this.f7640o;
    }
}
