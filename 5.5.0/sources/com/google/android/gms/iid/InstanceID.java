package com.google.android.gms.iid;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.p022f.C0464a;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.AndroidUtilsLight;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Deprecated
public class InstanceID {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_MISSING_INSTANCEID_SERVICE = "MISSING_INSTANCEID_SERVICE";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String ERROR_TIMEOUT = "TIMEOUT";
    private static Map<String, InstanceID> zzbq = new C0464a();
    private static long zzbr = TimeUnit.DAYS.toSeconds(7);
    private static zzak zzbs;
    private static zzaf zzbt;
    private static String zzbu;
    private String zzbv = TtmlNode.ANONYMOUS_REGION_ID;
    private Context zzk;

    private InstanceID(Context context, String str) {
        this.zzk = context.getApplicationContext();
        this.zzbv = str;
    }

    @Deprecated
    public static InstanceID getInstance(Context context) {
        return getInstance(context, null);
    }

    @KeepForSdk
    public static synchronized InstanceID getInstance(Context context, Bundle bundle) {
        InstanceID instanceID;
        synchronized (InstanceID.class) {
            String string = bundle == null ? TtmlNode.ANONYMOUS_REGION_ID : bundle.getString("subtype");
            String str = string == null ? TtmlNode.ANONYMOUS_REGION_ID : string;
            Context applicationContext = context.getApplicationContext();
            if (zzbs == null) {
                String packageName = applicationContext.getPackageName();
                Log.w("InstanceID", new StringBuilder(String.valueOf(packageName).length() + 73).append("Instance ID SDK is deprecated, ").append(packageName).append(" should update to use Firebase Instance ID").toString());
                zzbs = new zzak(applicationContext);
                zzbt = new zzaf(applicationContext);
            }
            zzbu = Integer.toString(zzg(applicationContext));
            instanceID = (InstanceID) zzbq.get(str);
            if (instanceID == null) {
                instanceID = new InstanceID(applicationContext, str);
                zzbq.put(str, instanceID);
            }
        }
        return instanceID;
    }

    private final KeyPair getKeyPair() {
        return zzbs.zzj(this.zzbv).getKeyPair();
    }

    static String zzd(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance(AndroidUtilsLight.DIGEST_ALGORITHM_SHA1).digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) ((digest[0] & 15) + 112);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("InstanceID", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    static int zzg(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 38).append("Never happens: can't find own package ").append(valueOf).toString());
            return i;
        }
    }

    static String zzh(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 38).append("Never happens: can't find own package ").append(valueOf).toString());
            return null;
        }
    }

    public static zzak zzn() {
        return zzbs;
    }

    @Deprecated
    public void deleteInstanceID() {
        zzd("*", "*", null);
        zzm();
    }

    @Deprecated
    public void deleteToken(String str, String str2) {
        zzd(str, str2, null);
    }

    @Deprecated
    public long getCreationTime() {
        return zzbs.zzj(this.zzbv).getCreationTime();
    }

    @Deprecated
    public String getId() {
        return zzd(getKeyPair());
    }

    @KeepForSdk
    public String getSubtype() {
        return this.zzbv;
    }

    @Deprecated
    public String getToken(String str, String str2) {
        return getToken(str, str2, null);
    }

    @Deprecated
    public String getToken(String str, String str2, Bundle bundle) {
        Object obj = 1;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        String str3 = null;
        String str4 = zzbs.get("appVersion");
        if (str4 != null && str4.equals(zzbu)) {
            str4 = zzbs.get("lastToken");
            if (str4 != null) {
                if ((System.currentTimeMillis() / 1000) - Long.valueOf(Long.parseLong(str4)).longValue() <= zzbr) {
                    obj = null;
                }
            }
        }
        if (obj == null) {
            str3 = zzbs.zze(this.zzbv, str, str2);
        }
        if (str3 == null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            str3 = zze(str, str2, bundle);
            if (str3 != null) {
                zzbs.zzd(this.zzbv, str, str2, str3, zzbu);
            }
        }
        return str3;
    }

    public final void zzd(String str, String str2, Bundle bundle) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        zzbs.zzf(this.zzbv, str, str2);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("sender", str);
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("subscription", str);
        bundle.putString("delete", "1");
        bundle.putString("X-delete", "1");
        bundle.putString("subtype", TtmlNode.ANONYMOUS_REGION_ID.equals(this.zzbv) ? str : this.zzbv);
        String str3 = "X-subtype";
        if (!TtmlNode.ANONYMOUS_REGION_ID.equals(this.zzbv)) {
            str = this.zzbv;
        }
        bundle.putString(str3, str);
        zzaf.zzi(zzbt.zzd(bundle, getKeyPair()));
    }

    public final String zze(String str, String str2, Bundle bundle) {
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("sender", str);
        String str3 = TtmlNode.ANONYMOUS_REGION_ID.equals(this.zzbv) ? str : this.zzbv;
        if (!bundle.containsKey("legacy.register")) {
            bundle.putString("subscription", str);
            bundle.putString("subtype", str3);
            bundle.putString("X-subscription", str);
            bundle.putString("X-subtype", str3);
        }
        str3 = zzaf.zzi(zzbt.zzd(bundle, getKeyPair()));
        if (!"RST".equals(str3) && !str3.startsWith("RST|")) {
            return str3;
        }
        InstanceIDListenerService.zzd(this.zzk, zzbs);
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    final void zzm() {
        zzbs.zzk(this.zzbv);
    }
}
