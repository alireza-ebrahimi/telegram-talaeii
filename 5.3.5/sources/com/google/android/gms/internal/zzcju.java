package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.math.BigInteger;
import java.util.Locale;

final class zzcju extends zzcli {
    static final Pair<String, Long> zzjlk = new Pair("", Long.valueOf(0));
    private SharedPreferences zzjll;
    public zzcjy zzjlm;
    public final zzcjx zzjln = new zzcjx(this, "last_upload", 0);
    public final zzcjx zzjlo = new zzcjx(this, "last_upload_attempt", 0);
    public final zzcjx zzjlp = new zzcjx(this, "backoff", 0);
    public final zzcjx zzjlq = new zzcjx(this, "last_delete_stale", 0);
    public final zzcjx zzjlr = new zzcjx(this, "midnight_offset", 0);
    public final zzcjx zzjls = new zzcjx(this, "first_open_time", 0);
    public final zzcjz zzjlt = new zzcjz(this, "app_instance_id", null);
    private String zzjlu;
    private boolean zzjlv;
    private long zzjlw;
    private String zzjlx;
    private long zzjly;
    private final Object zzjlz = new Object();
    public final zzcjx zzjma = new zzcjx(this, "time_before_start", 10000);
    public final zzcjx zzjmb = new zzcjx(this, "session_timeout", 1800000);
    public final zzcjw zzjmc = new zzcjw(this, "start_new_session", true);
    public final zzcjx zzjmd = new zzcjx(this, "last_pause_time", 0);
    public final zzcjx zzjme = new zzcjx(this, "time_active", 0);
    public boolean zzjmf;

    zzcju(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final SharedPreferences zzbbd() {
        zzwj();
        zzyk();
        return this.zzjll;
    }

    @WorkerThread
    final void setMeasurementEnabled(boolean z) {
        zzwj();
        zzayp().zzbba().zzj("Setting measurementEnabled", Boolean.valueOf(z));
        Editor edit = zzbbd().edit();
        edit.putBoolean("measurement_enabled", z);
        edit.apply();
    }

    protected final boolean zzazq() {
        return true;
    }

    @WorkerThread
    protected final void zzbap() {
        this.zzjll = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzjmf = this.zzjll.getBoolean("has_been_opened", false);
        if (!this.zzjmf) {
            Editor edit = this.zzjll.edit();
            edit.putBoolean("has_been_opened", true);
            edit.apply();
        }
        this.zzjlm = new zzcjy(this, "health_monitor", Math.max(0, ((Long) zzciz.zzjip.get()).longValue()));
    }

    @WorkerThread
    final String zzbbe() {
        zzwj();
        return zzbbd().getString("gmp_app_id", null);
    }

    final String zzbbf() {
        String str;
        synchronized (this.zzjlz) {
            if (Math.abs(zzxx().elapsedRealtime() - this.zzjly) < 1000) {
                str = this.zzjlx;
            } else {
                str = null;
            }
        }
        return str;
    }

    @WorkerThread
    final Boolean zzbbg() {
        zzwj();
        return !zzbbd().contains("use_service") ? null : Boolean.valueOf(zzbbd().getBoolean("use_service", false));
    }

    @WorkerThread
    final void zzbbh() {
        boolean z = true;
        zzwj();
        zzayp().zzbba().log("Clearing collection preferences.");
        boolean contains = zzbbd().contains("measurement_enabled");
        if (contains) {
            z = zzbs(true);
        }
        Editor edit = zzbbd().edit();
        edit.clear();
        edit.apply();
        if (contains) {
            setMeasurementEnabled(z);
        }
    }

    @WorkerThread
    protected final String zzbbi() {
        zzwj();
        String string = zzbbd().getString("previous_os_version", null);
        zzayf().zzyk();
        String str = VERSION.RELEASE;
        if (!(TextUtils.isEmpty(str) || str.equals(string))) {
            Editor edit = zzbbd().edit();
            edit.putString("previous_os_version", str);
            edit.apply();
        }
        return string;
    }

    @WorkerThread
    final void zzbr(boolean z) {
        zzwj();
        zzayp().zzbba().zzj("Setting useService", Boolean.valueOf(z));
        Editor edit = zzbbd().edit();
        edit.putBoolean("use_service", z);
        edit.apply();
    }

    @WorkerThread
    final boolean zzbs(boolean z) {
        zzwj();
        return zzbbd().getBoolean("measurement_enabled", z);
    }

    @WorkerThread
    @NonNull
    final Pair<String, Boolean> zzju(String str) {
        zzwj();
        long elapsedRealtime = zzxx().elapsedRealtime();
        if (this.zzjlu != null && elapsedRealtime < this.zzjlw) {
            return new Pair(this.zzjlu, Boolean.valueOf(this.zzjlv));
        }
        this.zzjlw = elapsedRealtime + zzayr().zza(str, zzciz.zzjio);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
            if (advertisingIdInfo != null) {
                this.zzjlu = advertisingIdInfo.getId();
                this.zzjlv = advertisingIdInfo.isLimitAdTrackingEnabled();
            }
            if (this.zzjlu == null) {
                this.zzjlu = "";
            }
        } catch (Throwable th) {
            zzayp().zzbaz().zzj("Unable to get advertising id", th);
            this.zzjlu = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair(this.zzjlu, Boolean.valueOf(this.zzjlv));
    }

    @WorkerThread
    final String zzjv(String str) {
        zzwj();
        String str2 = (String) zzju(str).first;
        if (zzcno.zzeq("MD5") == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzcno.zzeq("MD5").digest(str2.getBytes()))});
    }

    @WorkerThread
    final void zzjw(String str) {
        zzwj();
        Editor edit = zzbbd().edit();
        edit.putString("gmp_app_id", str);
        edit.apply();
    }

    final void zzjx(String str) {
        synchronized (this.zzjlz) {
            this.zzjlx = str;
            this.zzjly = zzxx().elapsedRealtime();
        }
    }
}
