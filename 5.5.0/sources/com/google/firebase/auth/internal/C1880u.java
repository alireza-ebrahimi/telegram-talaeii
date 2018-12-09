package com.google.firebase.auth.internal;

import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.firebase_auth.zzv;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.google.firebase.auth.internal.u */
final class C1880u {
    /* renamed from: a */
    private static final Logger f5541a = new Logger("JSONParser", new String[0]);

    @VisibleForTesting
    /* renamed from: a */
    private static List<Object> m8634a(JSONArray jSONArray) {
        List<Object> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            Object obj = jSONArray.get(i);
            if (obj instanceof JSONArray) {
                obj = C1880u.m8634a((JSONArray) obj);
            } else if (obj instanceof JSONObject) {
                obj = C1880u.m8636a((JSONObject) obj);
            }
            arrayList.add(obj);
        }
        return arrayList;
    }

    /* renamed from: a */
    public static Map<String, Object> m8635a(String str) {
        Preconditions.checkNotEmpty(str);
        String[] split = str.split("\\.");
        if (split.length < 2) {
            Logger logger = f5541a;
            String str2 = "Invalid idToken ";
            String valueOf = String.valueOf(str);
            logger.m8457e(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), new Object[0]);
            return Collections.EMPTY_MAP;
        }
        try {
            Map<String, Object> b = C1880u.m8637b(new String(Base64Utils.decodeUrlSafeNoPadding(split[1]), C3446C.UTF8_NAME));
            return b == null ? Collections.EMPTY_MAP : b;
        } catch (Throwable e) {
            f5541a.m8456e("Unable to decode token", e, new Object[0]);
            return Collections.EMPTY_MAP;
        }
    }

    @VisibleForTesting
    /* renamed from: a */
    private static Map<String, Object> m8636a(JSONObject jSONObject) {
        Map<String, Object> c0464a = new C0464a();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            Object obj = jSONObject.get(str);
            if (obj instanceof JSONArray) {
                obj = C1880u.m8634a((JSONArray) obj);
            } else if (obj instanceof JSONObject) {
                obj = C1880u.m8636a((JSONObject) obj);
            }
            c0464a.put(str, obj);
        }
        return c0464a;
    }

    /* renamed from: b */
    public static Map<String, Object> m8637b(String str) {
        Map<String, Object> map = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject != JSONObject.NULL) {
                    map = C1880u.m8636a(jSONObject);
                }
            } catch (Throwable e) {
                Log.d("JSONParser", "Failed to parse JSONObject into Map.");
                throw new zzv(e);
            }
        }
        return map;
    }
}
