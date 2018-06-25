package com.crashlytics.android.p065b;

import com.google.android.gms.common.internal.ImagesContract;
import org.json.JSONObject;

/* renamed from: com.crashlytics.android.b.g */
class C1374g {
    C1374g() {
    }

    /* renamed from: a */
    public C1373f m6953a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        return new C1373f(jSONObject.optString(ImagesContract.URL, null), jSONObject.optString("version_string", null), jSONObject.optString("display_version", null), jSONObject.optString("build_version", null), jSONObject.optString("identifier", null), jSONObject.optString("instance_identifier", null));
    }
}
