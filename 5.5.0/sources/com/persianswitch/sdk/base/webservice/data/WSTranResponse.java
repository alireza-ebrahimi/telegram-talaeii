package com.persianswitch.sdk.base.webservice.data;

import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import org.json.JSONObject;

public class WSTranResponse extends WSResponse {
    /* renamed from: m */
    protected String f7287m;
    /* renamed from: n */
    private String f7288n;
    /* renamed from: o */
    private String f7289o;

    public enum ExpirationStatus {
        UNKNOWN("0"),
        SAVED("1"),
        UNSAVED("2"),
        REMOVE_CAUSE_CHANGED("3");
        
        /* renamed from: e */
        private final String f7286e;

        private ExpirationStatus(String str) {
            this.f7286e = str;
        }

        /* renamed from: a */
        public static ExpirationStatus m10959a(String str) {
            Object obj = -1;
            switch (str.hashCode()) {
                case 48:
                    if (str.equals("0")) {
                        obj = 3;
                        break;
                    }
                    break;
                case 49:
                    if (str.equals("1")) {
                        obj = null;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        obj = 2;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    return SAVED;
                case 1:
                    return UNSAVED;
                case 2:
                    return REMOVE_CAUSE_CHANGED;
                default:
                    return UNKNOWN;
            }
        }
    }

    private static final class JsonParser implements Jsonable<WSTranResponse> {
        private JsonParser() {
        }

        /* renamed from: a */
        public JSONObject m10960a(WSTranResponse wSTranResponse, String str) {
            try {
                JSONObject a = new JsonParser().m10937a(wSTranResponse, str);
                if (a.has("rn")) {
                    wSTranResponse.f7287m = a.getString("rn");
                }
                if (a.has("cd")) {
                    wSTranResponse.f7288n = a.getString("cd");
                }
                if (a.has("tb")) {
                    wSTranResponse.f7289o = a.getString("tb");
                }
                return a;
            } catch (Exception e) {
                throw new JsonParseException(e.getMessage());
            }
        }
    }

    /* renamed from: b */
    public static WSTranResponse m10962b(String str) {
        try {
            WSTranResponse wSTranResponse = new WSTranResponse();
            new JsonParser().m10960a(wSTranResponse, str);
            return wSTranResponse;
        } catch (JsonParseException e) {
            return null;
        }
    }

    /* renamed from: j */
    public String m10964j() {
        return this.f7287m;
    }

    /* renamed from: k */
    public String m10965k() {
        return this.f7288n;
    }
}
