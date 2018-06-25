package com.persianswitch.sdk.payment.model;

import android.content.Context;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.payment.repo.SyncRepo;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

public final class ClientConfig {
    /* renamed from: a */
    private long f7482a = 420;
    /* renamed from: b */
    private long f7483b = 15;
    /* renamed from: c */
    private long f7484c = TimeUnit.SECONDS.convert(1, TimeUnit.DAYS);
    /* renamed from: d */
    private RootConfigTypes f7485d;
    /* renamed from: e */
    private String f7486e;
    /* renamed from: f */
    private boolean f7487f;
    /* renamed from: g */
    private boolean f7488g = false;
    /* renamed from: h */
    private boolean f7489h = false;
    /* renamed from: i */
    private long f7490i = TimeUnit.MINUTES.toMillis(1);
    /* renamed from: j */
    private String f7491j;

    private static class EntityJsonParser implements Jsonable<ClientConfig> {
        private EntityJsonParser() {
        }

        /* renamed from: a */
        public JSONObject m11122a(ClientConfig clientConfig, String str) {
            boolean z = true;
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("tranTimeout")) {
                    clientConfig.f7482a = jSONObject.getLong("tranTimeout");
                }
                if (jSONObject.has("returnTimeout")) {
                    clientConfig.f7483b = jSONObject.getLong("returnTimeout");
                }
                if (jSONObject.has("syncCardMinDelay")) {
                    clientConfig.f7484c = jSONObject.getLong("syncCardMinDelay");
                }
                if (jSONObject.has("root")) {
                    clientConfig.f7485d = RootConfigTypes.m11123a(jSONObject.getInt("root"));
                } else {
                    clientConfig.f7485d = RootConfigTypes.EnableNormal;
                }
                if (jSONObject.has("rootMsg")) {
                    clientConfig.f7486e = jSONObject.getString("rootMsg");
                }
                if (jSONObject.has("verifyMobileNo")) {
                    if (jSONObject.getInt("verifyMobileNo") != 1) {
                        z = false;
                    }
                    clientConfig.f7487f = z;
                }
                if (jSONObject.has("originality") && jSONObject.getString("originality").length() >= 2) {
                    clientConfig.f7488g = "1".equals(jSONObject.getString("originality").substring(0, 1));
                    clientConfig.f7489h = "1".equals(jSONObject.getString("originality").substring(1, 2));
                }
                if (jSONObject.has("inquiryWait")) {
                    clientConfig.f7490i = jSONObject.getLong("inquiryWait") * 1000;
                }
                if (jSONObject.has("unknownTranMsg")) {
                    clientConfig.f7491j = jSONObject.getString("unknownTranMsg");
                }
                return jSONObject;
            } catch (JSONException e) {
                throw new JsonParseException(e.getMessage());
            }
        }
    }

    public enum RootConfigTypes {
        Disable(2),
        EnableWithWarning(1),
        EnableNormal(0);
        
        /* renamed from: d */
        private int f7481d;

        private RootConfigTypes(int i) {
            this.f7481d = i;
        }

        /* renamed from: a */
        public static RootConfigTypes m11123a(int i) {
            for (RootConfigTypes rootConfigTypes : values()) {
                if (rootConfigTypes.f7481d == i) {
                    return rootConfigTypes;
                }
            }
            return EnableNormal;
        }
    }

    private ClientConfig() {
    }

    /* renamed from: a */
    public static ClientConfig m11126a(Context context) {
        ClientConfig clientConfig = new ClientConfig();
        SyncableData a = new SyncRepo(context).m11591a(SyncType.f7533b, LanguageManager.m10669a(context).m10678c());
        if (a != null) {
            try {
                new EntityJsonParser().m11122a(clientConfig, a.m11228f());
            } catch (JsonParseException e) {
                SDKLog.m10661c("ClientConfig", "error in parse client config", new Object[0]);
            }
        } else {
            SDKLog.m10661c("ClientConfig", "client config not found use default value", new Object[0]);
        }
        return clientConfig;
    }

    /* renamed from: a */
    public boolean m11135a() {
        return this.f7488g || this.f7489h;
    }

    /* renamed from: b */
    public long m11136b() {
        return this.f7490i;
    }

    /* renamed from: c */
    public String m11137c() {
        return this.f7491j;
    }

    /* renamed from: d */
    public long m11138d() {
        return this.f7482a;
    }

    /* renamed from: e */
    public long m11139e() {
        return this.f7483b;
    }

    /* renamed from: f */
    public long m11140f() {
        return this.f7484c;
    }

    /* renamed from: g */
    public RootConfigTypes m11141g() {
        return this.f7485d;
    }

    /* renamed from: h */
    public String m11142h() {
        return this.f7486e;
    }

    /* renamed from: i */
    public boolean m11143i() {
        return this.f7487f;
    }
}
