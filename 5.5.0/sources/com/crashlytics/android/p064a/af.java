package com.crashlytics.android.p064a;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.io.IOException;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.C3446C;
import p033b.p034a.p035a.p036a.p037a.p042d.C1164a;

/* renamed from: com.crashlytics.android.a.af */
class af implements C1164a<ad> {
    af() {
    }

    /* renamed from: a */
    public byte[] m6813a(ad adVar) {
        return m6815b(adVar).toString().getBytes(C3446C.UTF8_NAME);
    }

    @TargetApi(9)
    /* renamed from: b */
    public JSONObject m6815b(ad adVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            ae aeVar = adVar.f4030a;
            jSONObject.put("appBundleId", aeVar.f4039a);
            jSONObject.put("executionId", aeVar.f4040b);
            jSONObject.put("installationId", aeVar.f4041c);
            if (TextUtils.isEmpty(aeVar.f4043e)) {
                jSONObject.put("androidId", aeVar.f4042d);
            } else {
                jSONObject.put("advertisingId", aeVar.f4043e);
            }
            jSONObject.put("limitAdTrackingEnabled", aeVar.f4044f);
            jSONObject.put("betaDeviceToken", aeVar.f4045g);
            jSONObject.put("buildId", aeVar.f4046h);
            jSONObject.put("osVersion", aeVar.f4047i);
            jSONObject.put("deviceModel", aeVar.f4048j);
            jSONObject.put("appVersionCode", aeVar.f4049k);
            jSONObject.put("appVersionName", aeVar.f4050l);
            jSONObject.put(Param.TIMESTAMP, adVar.f4031b);
            jSONObject.put(Param.TYPE, adVar.f4032c.toString());
            if (adVar.f4033d != null) {
                jSONObject.put("details", new JSONObject(adVar.f4033d));
            }
            jSONObject.put("customType", adVar.f4034e);
            if (adVar.f4035f != null) {
                jSONObject.put("customAttributes", new JSONObject(adVar.f4035f));
            }
            jSONObject.put("predefinedType", adVar.f4036g);
            if (adVar.f4037h != null) {
                jSONObject.put("predefinedAttributes", new JSONObject(adVar.f4037h));
            }
            return jSONObject;
        } catch (Throwable e) {
            if (VERSION.SDK_INT >= 9) {
                throw new IOException(e.getMessage(), e);
            }
            throw new IOException(e.getMessage());
        }
    }
}
