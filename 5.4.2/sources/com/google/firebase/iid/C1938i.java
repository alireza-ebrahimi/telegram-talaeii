package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.firebase.C1897b;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;

/* renamed from: com.google.firebase.iid.i */
public final class C1938i {
    /* renamed from: a */
    private final Context f5725a;
    @GuardedBy("this")
    /* renamed from: b */
    private String f5726b;
    @GuardedBy("this")
    /* renamed from: c */
    private String f5727c;
    @GuardedBy("this")
    /* renamed from: d */
    private int f5728d;
    @GuardedBy("this")
    /* renamed from: e */
    private int f5729e = 0;

    public C1938i(Context context) {
        this.f5725a = context;
    }

    /* renamed from: a */
    private final PackageInfo m8850a(String str) {
        try {
            return this.f5725a.getPackageManager().getPackageInfo(str, 0);
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 23).append("Failed to find package ").append(valueOf).toString());
            return null;
        }
    }

    /* renamed from: a */
    public static String m8851a(C1897b c1897b) {
        String c = c1897b.m8696c().m8754c();
        if (c != null) {
            return c;
        }
        c = c1897b.m8696c().m8753b();
        if (!c.startsWith("1:")) {
            return c;
        }
        String[] split = c.split(":");
        if (split.length < 2) {
            return null;
        }
        c = split[1];
        return c.isEmpty() ? null : c;
    }

    /* renamed from: a */
    public static String m8852a(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance(AndroidUtilsLight.DIGEST_ALGORITHM_SHA1).digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) ((digest[0] & 15) + 112);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("FirebaseInstanceId", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    /* renamed from: e */
    private final synchronized void m8853e() {
        PackageInfo a = m8850a(this.f5725a.getPackageName());
        if (a != null) {
            this.f5726b = Integer.toString(a.versionCode);
            this.f5727c = a.versionName;
        }
    }

    /* renamed from: a */
    public final synchronized int m8854a() {
        int i = 0;
        synchronized (this) {
            if (this.f5729e != 0) {
                i = this.f5729e;
            } else {
                PackageManager packageManager = this.f5725a.getPackageManager();
                if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", "com.google.android.gms") == -1) {
                    Log.e("FirebaseInstanceId", "Google Play services missing or without correct permission.");
                } else {
                    Intent intent;
                    List queryIntentServices;
                    if (!PlatformVersion.isAtLeastO()) {
                        intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                        intent.setPackage("com.google.android.gms");
                        queryIntentServices = packageManager.queryIntentServices(intent, 0);
                        if (queryIntentServices != null && queryIntentServices.size() > 0) {
                            this.f5729e = 1;
                            i = this.f5729e;
                        }
                    }
                    intent = new Intent("com.google.iid.TOKEN_REQUEST");
                    intent.setPackage("com.google.android.gms");
                    queryIntentServices = packageManager.queryBroadcastReceivers(intent, 0);
                    if (queryIntentServices == null || queryIntentServices.size() <= 0) {
                        Log.w("FirebaseInstanceId", "Failed to resolve IID implementation package, falling back");
                        if (PlatformVersion.isAtLeastO()) {
                            this.f5729e = 2;
                        } else {
                            this.f5729e = 1;
                        }
                        i = this.f5729e;
                    } else {
                        this.f5729e = 2;
                        i = this.f5729e;
                    }
                }
            }
        }
        return i;
    }

    /* renamed from: b */
    public final synchronized String m8855b() {
        if (this.f5726b == null) {
            m8853e();
        }
        return this.f5726b;
    }

    /* renamed from: c */
    public final synchronized String m8856c() {
        if (this.f5727c == null) {
            m8853e();
        }
        return this.f5727c;
    }

    /* renamed from: d */
    public final synchronized int m8857d() {
        if (this.f5728d == 0) {
            PackageInfo a = m8850a("com.google.android.gms");
            if (a != null) {
                this.f5728d = a.versionCode;
            }
        }
        return this.f5728d;
    }
}
