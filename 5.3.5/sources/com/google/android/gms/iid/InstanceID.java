package com.google.android.gms.iid;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.thin.downloadmanager.BuildConfig;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class InstanceID {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_MISSING_INSTANCEID_SERVICE = "MISSING_INSTANCEID_SERVICE";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String ERROR_TIMEOUT = "TIMEOUT";
    private static Map<String, InstanceID> zzimu = new ArrayMap();
    private static zzaf zzimv;
    private static zzaa zzimw;
    private static String zzina;
    private Context mContext;
    private KeyPair zzimx;
    private String zzimy = "";
    private long zzimz;

    @Hide
    private InstanceID(Context context, String str) {
        this.mContext = context.getApplicationContext();
        this.zzimy = str;
    }

    public static InstanceID getInstance(Context context) {
        return getInstance(context, null);
    }

    @Hide
    @KeepForSdk
    public static synchronized InstanceID getInstance(Context context, Bundle bundle) {
        InstanceID instanceID;
        synchronized (InstanceID.class) {
            String string = bundle == null ? "" : bundle.getString("subtype");
            String str = string == null ? "" : string;
            Context applicationContext = context.getApplicationContext();
            if (zzimv == null) {
                zzimv = new zzaf(applicationContext);
                zzimw = new zzaa(applicationContext);
            }
            zzina = Integer.toString(zzdo(applicationContext));
            instanceID = (InstanceID) zzimu.get(str);
            if (instanceID == null) {
                instanceID = new InstanceID(applicationContext, str);
                zzimu.put(str, instanceID);
            }
        }
        return instanceID;
    }

    static String zza(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) ((digest[0] & 15) + 112);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("InstanceID", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    private final KeyPair zzawp() {
        if (this.zzimx == null) {
            this.zzimx = zzimv.zzii(this.zzimy);
        }
        if (this.zzimx == null) {
            this.zzimz = System.currentTimeMillis();
            this.zzimx = zzimv.zzc(this.zzimy, this.zzimz);
        }
        return this.zzimx;
    }

    @Hide
    public static zzaf zzawr() {
        return zzimv;
    }

    static int zzdo(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 38).append("Never happens: can't find own package ").append(valueOf).toString());
            return i;
        }
    }

    static String zzdp(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 38).append("Never happens: can't find own package ").append(valueOf).toString());
            return null;
        }
    }

    static String zzp(byte[] bArr) {
        return Base64.encodeToString(bArr, 11);
    }

    public void deleteInstanceID() throws IOException {
        zza("*", "*", null);
        zzawq();
    }

    public void deleteToken(String str, String str2) throws IOException {
        zza(str, str2, null);
    }

    public long getCreationTime() {
        if (this.zzimz == 0) {
            String str = zzimv.get(this.zzimy, "cre");
            if (str != null) {
                this.zzimz = Long.parseLong(str);
            }
        }
        return this.zzimz;
    }

    public String getId() {
        return zza(zzawp());
    }

    @Hide
    @KeepForSdk
    public String getSubtype() {
        return this.zzimy;
    }

    public String getToken(String str, String str2) throws IOException {
        return getToken(str, str2, null);
    }

    public String getToken(String str, String str2, Bundle bundle) throws IOException {
        Object obj = null;
        Object obj2 = 1;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Object obj3;
        String str3 = zzimv.get("appVersion");
        if (str3 == null || !str3.equals(zzina)) {
            obj3 = 1;
        } else {
            str3 = zzimv.get("lastToken");
            int i;
            if (str3 == null) {
                i = 1;
            } else {
                if ((System.currentTimeMillis() / 1000) - Long.valueOf(Long.parseLong(str3)).longValue() > 604800) {
                    i = 1;
                } else {
                    obj3 = null;
                }
            }
        }
        String zzf = obj3 != null ? null : zzimv.zzf(this.zzimy, str, str2);
        if (zzf == null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            if (bundle.getString("ttl") != null) {
                obj2 = null;
            }
            if (!"jwt".equals(bundle.getString(Param.TYPE))) {
                obj = obj2;
            }
            zzf = zzb(str, str2, bundle);
            if (!(zzf == null || r1 == null)) {
                zzimv.zza(this.zzimy, str, str2, zzf, zzina);
            }
        }
        return zzf;
    }

    @Hide
    public final void zza(String str, String str2, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        zzimv.zzg(this.zzimy, str, str2);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("sender", str);
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("subscription", str);
        bundle.putString("delete", BuildConfig.VERSION_NAME);
        bundle.putString("X-delete", BuildConfig.VERSION_NAME);
        bundle.putString("subtype", "".equals(this.zzimy) ? str : this.zzimy);
        String str3 = "X-subtype";
        if (!"".equals(this.zzimy)) {
            str = this.zzimy;
        }
        bundle.putString(str3, str);
        zzaa.zzy(zzimw.zza(bundle, zzawp()));
    }

    @Hide
    public final void zzawq() {
        this.zzimz = 0;
        zzimv.zzih(String.valueOf(this.zzimy).concat("|"));
        this.zzimx = null;
    }

    @Hide
    public final String zzb(String str, String str2, Bundle bundle) throws IOException {
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("sender", str);
        String str3 = "".equals(this.zzimy) ? str : this.zzimy;
        if (!bundle.containsKey("legacy.register")) {
            bundle.putString("subscription", str);
            bundle.putString("subtype", str3);
            bundle.putString("X-subscription", str);
            bundle.putString("X-subtype", str3);
        }
        str3 = zzaa.zzy(zzimw.zza(bundle, zzawp()));
        if (!"RST".equals(str3) && !str3.startsWith("RST|")) {
            return str3;
        }
        InstanceIDListenerService.zza(this.mContext, zzimv);
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }
}
