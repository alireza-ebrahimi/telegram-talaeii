package org.telegram.customization.p151g;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.ir.talaeii.R;
import org.telegram.customization.Model.RegisterModel;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import utils.p178a.C3791b;
import utils.volley.Response$ErrorListener;
import utils.volley.Response$Listener;
import utils.volley.toolbox.StringRequest;

/* renamed from: org.telegram.customization.g.b */
public class C2758b extends StringRequest {
    /* renamed from: a */
    Context f9100a;

    public C2758b(int i, String str, Response$Listener<String> response$Listener, Response$ErrorListener response$ErrorListener, Context context, String str2) {
        super(i, C2758b.m12807a(C3791b.m13967e(str2) + str, context), response$Listener, response$ErrorListener);
        this.f9100a = context;
    }

    public C2758b(int i, String str, Response$Listener<String> response$Listener, Response$ErrorListener response$ErrorListener, Context context, boolean z, String str2) {
        super(i, C2758b.m12807a((z ? C3791b.m13967e(str2) : TtmlNode.ANONYMOUS_REGION_ID) + str, context), response$Listener, response$ErrorListener);
        this.f9100a = context;
    }

    /* renamed from: a */
    static String m12807a(String str, Context context) {
        Uri parse = Uri.parse(str);
        String authority = parse.getAuthority();
        String path = parse.getPath();
        String scheme = parse.getScheme();
        Set<String> queryParameterNames = parse.getQueryParameterNames();
        Builder builder = new Builder();
        builder.scheme(scheme);
        builder.authority(authority);
        builder.path(path);
        for (String authority2 : queryParameterNames) {
            if (!(C2758b.m12809a(authority2) || C2758b.m12811b(authority2))) {
                builder.appendQueryParameter(authority2, parse.getQueryParameter(authority2));
            }
        }
        builder.appendQueryParameter("slt", TtmlNode.ANONYMOUS_REGION_ID + System.currentTimeMillis());
        builder.appendQueryParameter("appId", String.valueOf(context.getResources().getInteger(R.integer.APP_ID)));
        return builder.build().toString();
    }

    /* renamed from: a */
    public static Map<String, String> m12808a(Context context) {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("Authorization", "Custom QWxhZGRpbjpPcGVuU2VzYW1l");
        hashMap.put("X-SLS-VersionCode", String.valueOf(BuildConfig.VERSION_CODE));
        hashMap.put("X-SLS-AppId", String.valueOf(context.getResources().getInteger(R.integer.APP_ID)));
        hashMap.put("X-SLS-GPRS", String.valueOf(ConnectionsManager.isConnectedMobile(context)));
        hashMap.put("X-SLS-Carrier", C2822f.m13157b(context));
        hashMap.put("X-SLS-Extra", RegisterModel.getRegisterModelData());
        hashMap.put("X-SLS-PKG", BuildConfig.APPLICATION_ID);
        hashMap.put("X-SLS-Cy", "1");
        try {
            hashMap.put("X-SLS-UID", String.valueOf(UserConfig.getClientUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    /* renamed from: a */
    public static boolean m12809a(String str) {
        boolean z = false;
        if (str != null) {
            try {
                z = str.startsWith("slt");
            } catch (Exception e) {
            }
        }
        return z;
    }

    /* renamed from: b */
    public static Map<String, String> m12810b(Context context) {
        return new HashMap();
    }

    /* renamed from: b */
    public static boolean m12811b(String str) {
        boolean z = false;
        if (str != null) {
            try {
                z = str.startsWith("appId");
            } catch (Exception e) {
            }
        }
        return z;
    }

    public String getBodyContentType() {
        return "application/json";
    }

    public Map<String, String> getHeaders() {
        Map<String, String> hashMap = new HashMap();
        if (BuildConfig.FLAVOR.contentEquals("mowjgram")) {
            hashMap.put("Authorization", "Custom AiWKaDpp4rQzcGVkU2VzgJ4m");
        } else if (BuildConfig.FLAVOR.contentEquals("vip")) {
            hashMap.put("Authorization", "Custom rifl3sLp445wcGXk29VWg2m");
        } else if (BuildConfig.FLAVOR.contentEquals("gram")) {
            hashMap.put("Authorization", "Custom V1fk1qbm405tcGlk59VWf2m");
        } else {
            hashMap.put("Authorization", "Custom QWxhZGRpbjpPcGVuU2VzYW1l");
        }
        hashMap.put("X-SLS-VersionCode", String.valueOf(BuildConfig.VERSION_CODE));
        hashMap.put("X-SLS-AppId", String.valueOf(this.f9100a.getResources().getInteger(R.integer.APP_ID)));
        hashMap.put("X-SLS-GPRS", String.valueOf(ConnectionsManager.isConnectedMobile(this.f9100a)));
        hashMap.put("X-SLS-Carrier", C2822f.m13157b(this.f9100a));
        hashMap.put("X-SLS-PKG", BuildConfig.APPLICATION_ID);
        hashMap.put("X-SLS-Cy", "1");
        try {
            hashMap.put("X-SLS-UID", String.valueOf(UserConfig.getClientUserId()));
            if (UserConfig.getCurrentUser() != null) {
                hashMap.put("X-SLS-UN", String.valueOf(UserConfig.getCurrentUser().username));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
