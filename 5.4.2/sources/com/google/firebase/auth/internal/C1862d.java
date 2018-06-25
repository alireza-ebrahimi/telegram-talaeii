package com.google.firebase.auth.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.dynamite.ProviderConstants;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.internal.firebase_auth.zzv;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.firebase.C1897b;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.google.firebase.auth.internal.d */
public final class C1862d {
    /* renamed from: a */
    private Context f5515a;
    /* renamed from: b */
    private String f5516b;
    /* renamed from: c */
    private SharedPreferences f5517c = this.f5515a.getSharedPreferences(String.format("com.google.firebase.auth.api.Store.%s", new Object[]{this.f5516b}), 0);
    /* renamed from: d */
    private Logger f5518d = new Logger("StorageHelpers", new String[0]);

    public C1862d(Context context, String str) {
        Preconditions.checkNotNull(context);
        this.f5516b = Preconditions.checkNotEmpty(str);
        this.f5515a = context.getApplicationContext();
    }

    /* renamed from: a */
    private final zzl m8608a(JSONObject jSONObject) {
        Throwable e;
        try {
            Object string = jSONObject.getString("cachedTokenState");
            String string2 = jSONObject.getString("applicationName");
            boolean z = jSONObject.getBoolean("anonymous");
            String str = "2";
            String string3 = jSONObject.getString(ProviderConstants.API_COLNAME_FEATURE_VERSION);
            if (string3 != null) {
                str = string3;
            }
            JSONArray jSONArray = jSONObject.getJSONArray("userInfos");
            int length = jSONArray.length();
            List arrayList = new ArrayList(length);
            for (int i = 0; i < length; i++) {
                arrayList.add(zzh.m8638a(jSONArray.getString(i)));
            }
            FirebaseUser zzl = new zzl(C1897b.m8679a(string2), arrayList);
            if (!TextUtils.isEmpty(string)) {
                zzl.mo3029a(zzao.zzs(string));
            }
            if (!z) {
                zzl.mo3033e();
            }
            zzl.m8647a(str);
            if (!jSONObject.has("userMetadata")) {
                return zzl;
            }
            zzn a = zzn.m8662a(jSONObject.getJSONObject("userMetadata"));
            if (a == null) {
                return zzl;
            }
            zzl.m8650a(a);
            return zzl;
        } catch (JSONException e2) {
            e = e2;
            this.f5518d.wtf(e);
            return null;
        } catch (ArrayIndexOutOfBoundsException e3) {
            e = e3;
            this.f5518d.wtf(e);
            return null;
        } catch (IllegalArgumentException e4) {
            e = e4;
            this.f5518d.wtf(e);
            return null;
        } catch (zzv e5) {
            e = e5;
            this.f5518d.wtf(e);
            return null;
        }
    }

    /* renamed from: c */
    private final String m8609c(FirebaseUser firebaseUser) {
        JSONObject jSONObject = new JSONObject();
        if (!zzl.class.isAssignableFrom(firebaseUser.getClass())) {
            return null;
        }
        zzl zzl = (zzl) firebaseUser;
        try {
            jSONObject.put("cachedTokenState", zzl.mo3036h());
            jSONObject.put("applicationName", zzl.mo3034f().m8695b());
            jSONObject.put(Param.TYPE, "com.google.firebase.auth.internal.DefaultFirebaseUser");
            if (zzl.m8661l() != null) {
                JSONArray jSONArray = new JSONArray();
                List l = zzl.m8661l();
                for (int i = 0; i < l.size(); i++) {
                    jSONArray.put(((zzh) l.get(i)).m8644f());
                }
                jSONObject.put("userInfos", jSONArray);
            }
            jSONObject.put("anonymous", zzl.mo3030b());
            jSONObject.put(ProviderConstants.API_COLNAME_FEATURE_VERSION, "2");
            if (zzl.mo3038j() != null) {
                jSONObject.put("userMetadata", ((zzn) zzl.mo3038j()).m8665c());
            }
            return jSONObject.toString();
        } catch (Throwable e) {
            this.f5518d.wtf("Failed to turn object into JSON", e, new Object[0]);
            throw new zzv(e);
        }
    }

    /* renamed from: a */
    public final FirebaseUser m8610a() {
        FirebaseUser firebaseUser = null;
        Object string = this.f5517c.getString("com.google.firebase.auth.FIREBASE_USER", null);
        if (!TextUtils.isEmpty(string)) {
            try {
                JSONObject jSONObject = new JSONObject(string);
                if (jSONObject.has(Param.TYPE)) {
                    if ("com.google.firebase.auth.internal.DefaultFirebaseUser".equalsIgnoreCase(jSONObject.optString(Param.TYPE))) {
                        firebaseUser = m8608a(jSONObject);
                    }
                }
            } catch (Exception e) {
            }
        }
        return firebaseUser;
    }

    /* renamed from: a */
    public final void m8611a(FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(firebaseUser);
        Object c = m8609c(firebaseUser);
        if (!TextUtils.isEmpty(c)) {
            this.f5517c.edit().putString("com.google.firebase.auth.FIREBASE_USER", c).apply();
        }
    }

    /* renamed from: a */
    public final void m8612a(FirebaseUser firebaseUser, zzao zzao) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzao);
        this.f5517c.edit().putString(String.format("com.google.firebase.auth.GET_TOKEN_RESPONSE.%s", new Object[]{firebaseUser.mo3028a()}), zzao.toJson()).apply();
    }

    /* renamed from: a */
    public final void m8613a(String str) {
        this.f5517c.edit().remove(str).apply();
    }

    /* renamed from: b */
    public final zzao m8614b(FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(firebaseUser);
        String string = this.f5517c.getString(String.format("com.google.firebase.auth.GET_TOKEN_RESPONSE.%s", new Object[]{firebaseUser.mo3028a()}), null);
        return string != null ? zzao.zzs(string) : null;
    }
}
