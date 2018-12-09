package com.persianswitch.sdk.base.webservice.data;

import android.content.Context;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.StatusCode;
import org.json.JSONArray;
import org.json.JSONObject;

public class WSResponse {
    /* renamed from: a */
    protected long f7266a;
    /* renamed from: b */
    protected int f7267b;
    /* renamed from: c */
    protected int f7268c;
    /* renamed from: d */
    protected int f7269d;
    /* renamed from: e */
    protected String f7270e;
    /* renamed from: f */
    protected String f7271f;
    /* renamed from: g */
    protected String f7272g;
    /* renamed from: h */
    protected int f7273h;
    /* renamed from: i */
    protected JSONObject f7274i;
    /* renamed from: j */
    protected String[] f7275j;
    /* renamed from: k */
    protected int f7276k;
    /* renamed from: l */
    protected JSONObject f7277l;

    static class JsonParser implements Jsonable<WSResponse> {
        JsonParser() {
        }

        /* renamed from: a */
        public JSONObject m10937a(WSResponse wSResponse, String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("hi")) {
                    wSResponse.f7276k = jSONObject.getInt("hi");
                }
                if (jSONObject.has("tr")) {
                    wSResponse.f7266a = jSONObject.getLong("tr");
                }
                if (jSONObject.has("st")) {
                    wSResponse.f7267b = jSONObject.getInt("st");
                }
                if (jSONObject.has("op")) {
                    wSResponse.f7268c = jSONObject.getInt("op");
                }
                if (jSONObject.has("sc")) {
                    wSResponse.f7269d = jSONObject.getInt("sc");
                }
                if (jSONObject.has("ds")) {
                    wSResponse.f7270e = jSONObject.getString("ds");
                }
                if (jSONObject.has("sm")) {
                    wSResponse.f7271f = jSONObject.getString("sm");
                }
                if (jSONObject.has("ad")) {
                    wSResponse.f7272g = jSONObject.getString("ad");
                }
                if (jSONObject.has("pi")) {
                    wSResponse.f7273h = jSONObject.getInt("pi");
                }
                if (jSONObject.has("hd")) {
                    wSResponse.f7277l = jSONObject.getJSONObject("hd");
                }
                if (jSONObject.has("ed")) {
                    JSONArray jSONArray = jSONObject.getJSONArray("ed");
                    if (jSONArray != null) {
                        wSResponse.f7275j = new String[jSONArray.length()];
                        for (int i = 0; i < jSONArray.length(); i++) {
                            wSResponse.f7275j[i] = jSONArray.getString(i);
                        }
                    }
                }
                if (jSONObject.has("ej")) {
                    wSResponse.f7274i = jSONObject.getJSONObject("ej");
                }
                return jSONObject;
            } catch (Exception e) {
                throw new JsonParseException(e.getMessage());
            }
        }
    }

    /* renamed from: a */
    public static WSResponse m10938a(String str) {
        try {
            WSResponse wSResponse = new WSResponse();
            new JsonParser().m10937a(wSResponse, str);
            return wSResponse;
        } catch (JsonParseException e) {
            return null;
        }
    }

    /* renamed from: a */
    public int m10939a() {
        return this.f7269d;
    }

    /* renamed from: a */
    public String m10940a(Context context) {
        return !StringUtils.m10803a(this.f7270e) ? this.f7270e : StatusCode.m10850a(context, m10941b());
    }

    /* renamed from: b */
    public int m10941b() {
        return this.f7267b;
    }

    /* renamed from: c */
    public StatusCode m10942c() {
        return StatusCode.m10848a(m10941b());
    }

    /* renamed from: d */
    public String m10943d() {
        return this.f7270e;
    }

    /* renamed from: e */
    public int m10944e() {
        return this.f7273h;
    }

    /* renamed from: f */
    public JSONObject m10945f() {
        return this.f7277l;
    }

    /* renamed from: g */
    public JSONObject m10946g() {
        return this.f7274i;
    }

    /* renamed from: h */
    public String[] m10947h() {
        return this.f7275j;
    }

    /* renamed from: i */
    public String m10948i() {
        return this.f7271f;
    }
}
