package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zzs;
import com.google.firebase.FirebaseApp;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Hide
public final class zzw {
    private final Context zzaiq;
    private String zzcs;
    private String zzold;
    private int zzole;
    private int zzolf = 0;

    public zzw(Context context) {
        this.zzaiq = context;
    }

    public static String zzb(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) ((digest[0] & 15) + 112);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("FirebaseInstanceId", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    private final synchronized void zzclp() {
        PackageInfo zzog = zzog(this.zzaiq.getPackageName());
        if (zzog != null) {
            this.zzold = Integer.toString(zzog.versionCode);
            this.zzcs = zzog.versionName;
        }
    }

    public static String zzf(FirebaseApp firebaseApp) {
        String gcmSenderId = firebaseApp.getOptions().getGcmSenderId();
        if (gcmSenderId != null) {
            return gcmSenderId;
        }
        gcmSenderId = firebaseApp.getOptions().getApplicationId();
        if (!gcmSenderId.startsWith("1:")) {
            return gcmSenderId;
        }
        String[] split = gcmSenderId.split(":");
        if (split.length < 2) {
            return null;
        }
        gcmSenderId = split[1];
        return gcmSenderId.isEmpty() ? null : gcmSenderId;
    }

    private final PackageInfo zzog(String str) {
        try {
            return this.zzaiq.getPackageManager().getPackageInfo(str, 0);
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 23).append("Failed to find package ").append(valueOf).toString());
            return null;
        }
    }

    public final synchronized int zzcll() {
        int i = 0;
        synchronized (this) {
            if (this.zzolf != 0) {
                i = this.zzolf;
            } else {
                PackageManager packageManager = this.zzaiq.getPackageManager();
                if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", "com.google.android.gms") == -1) {
                    Log.e("FirebaseInstanceId", "Google Play services missing or without correct permission.");
                } else {
                    Intent intent;
                    List queryIntentServices;
                    if (!zzs.isAtLeastO()) {
                        intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                        intent.setPackage("com.google.android.gms");
                        queryIntentServices = packageManager.queryIntentServices(intent, 0);
                        if (queryIntentServices != null && queryIntentServices.size() > 0) {
                            this.zzolf = 1;
                            i = this.zzolf;
                        }
                    }
                    intent = new Intent("com.google.iid.TOKEN_REQUEST");
                    intent.setPackage("com.google.android.gms");
                    queryIntentServices = packageManager.queryBroadcastReceivers(intent, 0);
                    if (queryIntentServices == null || queryIntentServices.size() <= 0) {
                        Log.w("FirebaseInstanceId", "Failed to resolve IID implementation package, falling back");
                        if (zzs.isAtLeastO()) {
                            this.zzolf = 2;
                        } else {
                            this.zzolf = 1;
                        }
                        i = this.zzolf;
                    } else {
                        this.zzolf = 2;
                        i = this.zzolf;
                    }
                }
            }
        }
        return i;
    }

    public final synchronized String zzclm() {
        if (this.zzold == null) {
            zzclp();
        }
        return this.zzold;
    }

    public final synchronized String zzcln() {
        if (this.zzcs == null) {
            zzclp();
        }
        return this.zzcs;
    }

    public final synchronized int zzclo() {
        if (this.zzole == 0) {
            PackageInfo zzog = zzog("com.google.android.gms");
            if (zzog != null) {
                this.zzole = zzog.versionCode;
            }
        }
        return this.zzole;
    }
}
