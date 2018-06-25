package com.google.firebase.iid;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.google.firebase.iid.s */
final class C1947s {
    /* renamed from: b */
    private static final long f5753b = TimeUnit.DAYS.toMillis(7);
    /* renamed from: a */
    final String f5754a;
    /* renamed from: c */
    private final String f5755c;
    /* renamed from: d */
    private final long f5756d;

    private C1947s(String str, String str2, long j) {
        this.f5754a = str;
        this.f5755c = str2;
        this.f5756d = j;
    }

    /* renamed from: a */
    static C1947s m8885a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!str.startsWith("{")) {
            return new C1947s(str, null, 0);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new C1947s(jSONObject.getString("token"), jSONObject.getString("appVersion"), jSONObject.getLong(Param.TIMESTAMP));
        } catch (JSONException e) {
            String valueOf = String.valueOf(e);
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 23).append("Failed to parse token: ").append(valueOf).toString());
            return null;
        }
    }

    /* renamed from: a */
    static String m8886a(String str, String str2, long j) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("token", str);
            jSONObject.put("appVersion", str2);
            jSONObject.put(Param.TIMESTAMP, j);
            return jSONObject.toString();
        } catch (JSONException e) {
            String valueOf = String.valueOf(e);
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 24).append("Failed to encode token: ").append(valueOf).toString());
            return null;
        }
    }

    /* renamed from: b */
    final boolean m8887b(String str) {
        return System.currentTimeMillis() > this.f5756d + f5753b || !str.equals(this.f5755c);
    }
}
