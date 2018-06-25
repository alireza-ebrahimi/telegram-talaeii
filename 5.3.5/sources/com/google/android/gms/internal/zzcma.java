package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement.zza;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class zzcma extends zzcli {
    protected zzcmd zzjqm;
    private volatile zzclz zzjqn;
    private zzclz zzjqo;
    private long zzjqp;
    private final Map<Activity, zzcmd> zzjqq = new ArrayMap();
    private final CopyOnWriteArrayList<zza> zzjqr = new CopyOnWriteArrayList();
    private boolean zzjqs;
    private zzclz zzjqt;
    private String zzjqu;

    public zzcma(zzckj zzckj) {
        super(zzckj);
    }

    @MainThread
    private final void zza(Activity activity, zzcmd zzcmd, boolean z) {
        boolean hasNext;
        boolean z2 = true;
        zzclz zzclz = this.zzjqn != null ? this.zzjqn : (this.zzjqo == null || Math.abs(zzxx().elapsedRealtime() - this.zzjqp) >= 1000) ? null : this.zzjqo;
        zzclz = zzclz != null ? new zzclz(zzclz) : null;
        this.zzjqs = true;
        try {
            Iterator it = this.zzjqr.iterator();
            while (true) {
                hasNext = it.hasNext();
                if (!hasNext) {
                    break;
                }
                try {
                    z2 &= ((zza) it.next()).zza(zzclz, zzcmd);
                } catch (Exception e) {
                    zzayp().zzbau().zzj("onScreenChangeCallback threw exception", e);
                }
            }
            hasNext = z2;
        } catch (Exception e2) {
            Exception exception = e2;
            hasNext = z2;
            z2 = zzayp().zzbau();
            z2.zzj("onScreenChangeCallback loop threw exception", exception);
        } finally {
            this.zzjqs = false;
        }
        zzclz = this.zzjqn == null ? this.zzjqo : this.zzjqn;
        if (hasNext) {
            if (zzcmd.zzjqk == null) {
                zzcmd.zzjqk = zzkg(activity.getClass().getCanonicalName());
            }
            zzclz zzcmd2 = new zzcmd(zzcmd);
            this.zzjqo = this.zzjqn;
            this.zzjqp = zzxx().elapsedRealtime();
            this.zzjqn = zzcmd2;
            zzayo().zzh(new zzcmb(this, z, zzclz, zzcmd2));
        }
    }

    public static void zza(zzclz zzclz, Bundle bundle, boolean z) {
        if (bundle != null && zzclz != null && (!bundle.containsKey("_sc") || z)) {
            if (zzclz.zzjqj != null) {
                bundle.putString("_sn", zzclz.zzjqj);
            } else {
                bundle.remove("_sn");
            }
            bundle.putString("_sc", zzclz.zzjqk);
            bundle.putLong("_si", zzclz.zzjql);
        } else if (bundle != null && zzclz == null && z) {
            bundle.remove("_sn");
            bundle.remove("_sc");
            bundle.remove("_si");
        }
    }

    @WorkerThread
    private final void zza(@NonNull zzcmd zzcmd) {
        zzayb().zzaj(zzxx().elapsedRealtime());
        if (zzayn().zzbx(zzcmd.zzjra)) {
            zzcmd.zzjra = false;
        }
    }

    private static String zzkg(String str) {
        String[] split = str.split("\\.");
        String str2 = split.length > 0 ? split[split.length - 1] : "";
        return str2.length() > 100 ? str2.substring(0, 100) : str2;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @MainThread
    public final void onActivityDestroyed(Activity activity) {
        this.zzjqq.remove(activity);
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        zzcmd zzr = zzr(activity);
        this.zzjqo = this.zzjqn;
        this.zzjqp = zzxx().elapsedRealtime();
        this.zzjqn = null;
        zzayo().zzh(new zzcmc(this, zzr));
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        zza(activity, zzr(activity), false);
        zzclh zzayb = zzayb();
        zzayb.zzayo().zzh(new zzcid(zzayb, zzayb.zzxx().elapsedRealtime()));
    }

    @MainThread
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        if (bundle != null) {
            zzcmd zzcmd = (zzcmd) this.zzjqq.get(activity);
            if (zzcmd != null) {
                Bundle bundle2 = new Bundle();
                bundle2.putLong("id", zzcmd.zzjql);
                bundle2.putString("name", zzcmd.zzjqj);
                bundle2.putString("referrer_name", zzcmd.zzjqk);
                bundle.putBundle("com.google.firebase.analytics.screen_service", bundle2);
            }
        }
    }

    @MainThread
    public final void registerOnScreenChangeCallback(@NonNull zza zza) {
        if (zza == null) {
            zzayp().zzbaw().log("Attempting to register null OnScreenChangeCallback");
            return;
        }
        this.zzjqr.remove(zza);
        this.zzjqr.add(zza);
    }

    @MainThread
    public final void setCurrentScreen(@NonNull Activity activity, @Nullable @Size(max = 36, min = 1) String str, @Nullable @Size(max = 36, min = 1) String str2) {
        zzayo();
        if (!zzcke.zzas()) {
            zzayp().zzbaw().log("setCurrentScreen must be called from the main thread");
        } else if (this.zzjqs) {
            zzayp().zzbaw().log("Cannot call setCurrentScreen from onScreenChangeCallback");
        } else if (this.zzjqn == null) {
            zzayp().zzbaw().log("setCurrentScreen cannot be called while no activity active");
        } else if (this.zzjqq.get(activity) == null) {
            zzayp().zzbaw().log("setCurrentScreen must be called with an activity in the activity lifecycle");
        } else {
            if (str2 == null) {
                str2 = zzkg(activity.getClass().getCanonicalName());
            }
            boolean equals = this.zzjqn.zzjqk.equals(str2);
            boolean zzas = zzcno.zzas(this.zzjqn.zzjqj, str);
            if (equals && zzas) {
                zzayp().zzbax().log("setCurrentScreen cannot be called with the same class and name");
            } else if (str != null && (str.length() <= 0 || str.length() > 100)) {
                zzayp().zzbaw().zzj("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            } else if (str2 == null || (str2.length() > 0 && str2.length() <= 100)) {
                Object obj;
                zzcjl zzbba = zzayp().zzbba();
                String str3 = "Setting current screen to name, class";
                if (str == null) {
                    obj = "null";
                } else {
                    String str4 = str;
                }
                zzbba.zze(str3, obj, str2);
                zzcmd zzcmd = new zzcmd(str, str2, zzayl().zzbcq());
                this.zzjqq.put(activity, zzcmd);
                zza(activity, zzcmd, true);
            } else {
                zzayp().zzbaw().zzj("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            }
        }
    }

    @MainThread
    public final void unregisterOnScreenChangeCallback(@NonNull zza zza) {
        this.zzjqr.remove(zza);
    }

    @WorkerThread
    public final void zza(String str, zzclz zzclz) {
        zzwj();
        synchronized (this) {
            if (this.zzjqu == null || this.zzjqu.equals(str) || zzclz != null) {
                this.zzjqu = str;
                this.zzjqt = zzclz;
            }
        }
    }

    public final /* bridge */ /* synthetic */ void zzaxz() {
        super.zzaxz();
    }

    public final /* bridge */ /* synthetic */ void zzaya() {
        super.zzaya();
    }

    public final /* bridge */ /* synthetic */ zzcia zzayb() {
        return super.zzayb();
    }

    public final /* bridge */ /* synthetic */ zzcih zzayc() {
        return super.zzayc();
    }

    public final /* bridge */ /* synthetic */ zzclk zzayd() {
        return super.zzayd();
    }

    public final /* bridge */ /* synthetic */ zzcje zzaye() {
        return super.zzaye();
    }

    public final /* bridge */ /* synthetic */ zzcir zzayf() {
        return super.zzayf();
    }

    public final /* bridge */ /* synthetic */ zzcme zzayg() {
        return super.zzayg();
    }

    public final /* bridge */ /* synthetic */ zzcma zzayh() {
        return super.zzayh();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzayi() {
        return super.zzayi();
    }

    public final /* bridge */ /* synthetic */ zzcil zzayj() {
        return super.zzayj();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzayk() {
        return super.zzayk();
    }

    public final /* bridge */ /* synthetic */ zzcno zzayl() {
        return super.zzayl();
    }

    public final /* bridge */ /* synthetic */ zzckd zzaym() {
        return super.zzaym();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzayn() {
        return super.zzayn();
    }

    public final /* bridge */ /* synthetic */ zzcke zzayo() {
        return super.zzayo();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzayp() {
        return super.zzayp();
    }

    public final /* bridge */ /* synthetic */ zzcju zzayq() {
        return super.zzayq();
    }

    public final /* bridge */ /* synthetic */ zzcik zzayr() {
        return super.zzayr();
    }

    protected final boolean zzazq() {
        return false;
    }

    @WorkerThread
    public final zzcmd zzbcg() {
        zzyk();
        zzwj();
        return this.zzjqm;
    }

    public final zzclz zzbch() {
        zzclz zzclz = this.zzjqn;
        return zzclz == null ? null : new zzclz(zzclz);
    }

    @MainThread
    final zzcmd zzr(@NonNull Activity activity) {
        zzbq.checkNotNull(activity);
        zzcmd zzcmd = (zzcmd) this.zzjqq.get(activity);
        if (zzcmd != null) {
            return zzcmd;
        }
        zzcmd = new zzcmd(null, zzkg(activity.getClass().getCanonicalName()), zzayl().zzbcq());
        this.zzjqq.put(activity, zzcmd);
        return zzcmd;
    }

    public final /* bridge */ /* synthetic */ void zzwj() {
        super.zzwj();
    }

    public final /* bridge */ /* synthetic */ zze zzxx() {
        return super.zzxx();
    }
}
