package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.firebase.FirebaseApp;
import com.thin.downloadmanager.BuildConfig;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FirebaseInstanceId {
    private static Map<String, FirebaseInstanceId> zzimu = new ArrayMap();
    private static final long zzokn = TimeUnit.HOURS.toSeconds(8);
    private static zzaa zzoko;
    private static ScheduledThreadPoolExecutor zzokp;
    private KeyPair zzimx;
    private final FirebaseApp zzmwq;
    private final zzw zzokq;
    private final zzx zzokr;
    private boolean zzoks = false;
    private boolean zzokt;

    private FirebaseInstanceId(FirebaseApp firebaseApp, zzw zzw) {
        if (zzw.zzf(firebaseApp) == null) {
            throw new IllegalStateException("FirebaseInstanceId failed to initialize, FirebaseApp is missing project ID");
        }
        this.zzmwq = firebaseApp;
        this.zzokq = zzw;
        this.zzokr = new zzx(firebaseApp.getApplicationContext(), zzw);
        this.zzokt = zzcli();
        if (zzclk()) {
            zzclb();
        }
    }

    public static FirebaseInstanceId getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @Keep
    public static synchronized FirebaseInstanceId getInstance(@NonNull FirebaseApp firebaseApp) {
        FirebaseInstanceId firebaseInstanceId;
        synchronized (FirebaseInstanceId.class) {
            firebaseInstanceId = (FirebaseInstanceId) zzimu.get(firebaseApp.getOptions().getApplicationId());
            if (firebaseInstanceId == null) {
                if (zzoko == null) {
                    zzoko = new zzaa(firebaseApp.getApplicationContext());
                }
                firebaseInstanceId = new FirebaseInstanceId(firebaseApp, new zzw(firebaseApp.getApplicationContext()));
                zzimu.put(firebaseApp.getOptions().getApplicationId(), firebaseInstanceId);
            }
        }
        return firebaseInstanceId;
    }

    private final synchronized void startSync() {
        if (!this.zzoks) {
            zzcd(0);
        }
    }

    private final synchronized KeyPair zzawp() {
        if (this.zzimx == null) {
            this.zzimx = zzoko.zzrs("");
        }
        if (this.zzimx == null) {
            this.zzimx = zzoko.zzrq("");
        }
        return this.zzimx;
    }

    private final String zzb(String str, String str2, Bundle bundle) throws IOException {
        bundle.putString("scope", str2);
        bundle.putString("sender", str);
        bundle.putString("subtype", str);
        bundle.putString("appid", getId());
        bundle.putString("gmp_app_id", this.zzmwq.getOptions().getApplicationId());
        bundle.putString("gmsv", Integer.toString(this.zzokq.zzclo()));
        bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
        bundle.putString("app_ver", this.zzokq.zzclm());
        bundle.putString("app_ver_name", this.zzokq.zzcln());
        bundle.putString("cliv", "fiid-12211000");
        Bundle zzah = this.zzokr.zzah(bundle);
        if (zzah == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String string = zzah.getString("registration_id");
        if (string == null) {
            string = zzah.getString("unregistered");
            if (string == null) {
                string = zzah.getString("error");
                if ("RST".equals(string)) {
                    zzclg();
                    throw new IOException("INSTANCE_ID_RESET");
                } else if (string != null) {
                    throw new IOException(string);
                } else {
                    String valueOf = String.valueOf(zzah);
                    Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 21).append("Unexpected response: ").append(valueOf).toString(), new Throwable());
                    throw new IOException("SERVICE_NOT_AVAILABLE");
                }
            }
        }
        return string;
    }

    static void zzb(Runnable runnable, long j) {
        synchronized (FirebaseInstanceId.class) {
            if (zzokp == null) {
                zzokp = new ScheduledThreadPoolExecutor(1);
            }
            zzokp.schedule(runnable, j, TimeUnit.SECONDS);
        }
    }

    private final void zzclb() {
        zzab zzclc = zzclc();
        if (zzclc == null || zzclc.zzru(this.zzokq.zzclm()) || zzoko.zzcls() != null) {
            startSync();
        }
    }

    static zzaa zzcle() {
        return zzoko;
    }

    static boolean zzclf() {
        return Log.isLoggable("FirebaseInstanceId", 3) || (VERSION.SDK_INT == 23 && Log.isLoggable("FirebaseInstanceId", 3));
    }

    private final boolean zzcli() {
        Context applicationContext = this.zzmwq.getApplicationContext();
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("com.google.firebase.messaging", 0);
        if (sharedPreferences.contains("auto_init")) {
            return sharedPreferences.getBoolean("auto_init", true);
        }
        try {
            PackageManager packageManager = applicationContext.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128);
                if (!(applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey("firebase_messaging_auto_init_enabled"))) {
                    return applicationInfo.metaData.getBoolean("firebase_messaging_auto_init_enabled");
                }
            }
        } catch (NameNotFoundException e) {
        }
        return zzclj();
    }

    private final boolean zzclj() {
        try {
            Class.forName("com.google.firebase.messaging.FirebaseMessaging");
            return true;
        } catch (ClassNotFoundException e) {
            Context applicationContext = this.zzmwq.getApplicationContext();
            Intent intent = new Intent("com.google.firebase.MESSAGING_EVENT");
            intent.setPackage(applicationContext.getPackageName());
            ResolveInfo resolveService = applicationContext.getPackageManager().resolveService(intent, 0);
            return (resolveService == null || resolveService.serviceInfo == null) ? false : true;
        }
    }

    @WorkerThread
    public void deleteInstanceId() throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Bundle bundle = new Bundle();
        bundle.putString("iid-operation", "delete");
        bundle.putString("delete", BuildConfig.VERSION_NAME);
        zzb("*", "*", bundle);
        zzclg();
    }

    @WorkerThread
    public void deleteToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Bundle bundle = new Bundle();
        bundle.putString("delete", BuildConfig.VERSION_NAME);
        zzb(str, str2, bundle);
        zzoko.zzg("", str, str2);
    }

    final FirebaseApp getApp() {
        return this.zzmwq;
    }

    public long getCreationTime() {
        return zzoko.zzrp("");
    }

    @WorkerThread
    public String getId() {
        zzclb();
        return zzw.zzb(zzawp());
    }

    @Nullable
    public String getToken() {
        zzab zzclc = zzclc();
        if (zzclc == null || zzclc.zzru(this.zzokq.zzclm())) {
            startSync();
        }
        return zzclc != null ? zzclc.zzlnm : null;
    }

    @WorkerThread
    public String getToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        zzab zzq = zzoko.zzq("", str, str2);
        if (zzq != null && !zzq.zzru(this.zzokq.zzclm())) {
            return zzq.zzlnm;
        }
        String zzb = zzb(str, str2, new Bundle());
        if (zzb == null) {
            return zzb;
        }
        zzoko.zza("", str, str2, zzb, this.zzokq.zzclm());
        return zzb;
    }

    final synchronized void zzcd(long j) {
        zzb(new zzac(this, this.zzokq, Math.min(Math.max(30, j << 1), zzokn)), j);
        this.zzoks = true;
    }

    @Nullable
    final zzab zzclc() {
        return zzoko.zzq("", zzw.zzf(this.zzmwq), "*");
    }

    @Hide
    final String zzcld() throws IOException {
        return getToken(zzw.zzf(this.zzmwq), "*");
    }

    final synchronized void zzclg() {
        zzoko.zzawz();
        this.zzimx = null;
        if (zzclk()) {
            startSync();
        }
    }

    final void zzclh() {
        zzoko.zzrr("");
        startSync();
    }

    @Hide
    public final synchronized boolean zzclk() {
        return this.zzokt;
    }

    final synchronized void zzcy(boolean z) {
        this.zzoks = z;
    }

    @Hide
    public final synchronized void zzcz(boolean z) {
        Editor edit = this.zzmwq.getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit();
        edit.putBoolean("auto_init", z);
        edit.apply();
        if (!this.zzokt && z) {
            zzclb();
        }
        this.zzokt = z;
    }

    @Hide
    public final synchronized void zzrl(String str) {
        zzoko.zzrl(str);
        startSync();
    }

    final void zzrm(String str) throws IOException {
        zzab zzclc = zzclc();
        if (zzclc == null || zzclc.zzru(this.zzokq.zzclm())) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String str2 = "gcm.topic";
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str);
        bundle.putString(str2, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        String str3 = zzclc.zzlnm;
        str2 = String.valueOf("/topics/");
        valueOf2 = String.valueOf(str);
        zzb(str3, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), bundle);
    }

    final void zzrn(String str) throws IOException {
        zzab zzclc = zzclc();
        if (zzclc == null || zzclc.zzru(this.zzokq.zzclm())) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String str2 = "gcm.topic";
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str);
        bundle.putString(str2, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        bundle.putString("delete", BuildConfig.VERSION_NAME);
        String str3 = zzclc.zzlnm;
        str2 = String.valueOf("/topics/");
        valueOf2 = String.valueOf(str);
        zzb(str3, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), bundle);
    }
}
