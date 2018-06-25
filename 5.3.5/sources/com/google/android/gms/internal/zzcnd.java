package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.util.zze;
import org.apache.commons.lang3.time.DateUtils;

public final class zzcnd extends zzcli {
    private Handler handler;
    private long zzjsb = zzxx().elapsedRealtime();
    private final zzcip zzjsc = new zzcne(this, this.zzjev);
    private final zzcip zzjsd = new zzcnf(this, this.zzjev);

    zzcnd(zzckj zzckj) {
        super(zzckj);
    }

    private final void zzbcn() {
        synchronized (this) {
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
        }
    }

    @WorkerThread
    private final void zzbco() {
        zzwj();
        zzbx(false);
        zzayb().zzaj(zzxx().elapsedRealtime());
    }

    @WorkerThread
    private final void zzbe(long j) {
        zzwj();
        zzbcn();
        this.zzjsc.cancel();
        this.zzjsd.cancel();
        zzayp().zzbba().zzj("Activity resumed, time", Long.valueOf(j));
        this.zzjsb = j;
        if (zzxx().currentTimeMillis() - zzayq().zzjmb.get() > zzayq().zzjmd.get()) {
            zzayq().zzjmc.set(true);
            zzayq().zzjme.set(0);
        }
        if (zzayq().zzjmc.get()) {
            this.zzjsc.zzs(Math.max(0, zzayq().zzjma.get() - zzayq().zzjme.get()));
        } else {
            this.zzjsd.zzs(Math.max(0, DateUtils.MILLIS_PER_HOUR - zzayq().zzjme.get()));
        }
    }

    @WorkerThread
    private final void zzbf(long j) {
        zzwj();
        zzbcn();
        this.zzjsc.cancel();
        this.zzjsd.cancel();
        zzayp().zzbba().zzj("Activity paused, time", Long.valueOf(j));
        if (this.zzjsb != 0) {
            zzayq().zzjme.set(zzayq().zzjme.get() + (j - this.zzjsb));
        }
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
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
    public final boolean zzbx(boolean z) {
        zzwj();
        zzyk();
        long elapsedRealtime = zzxx().elapsedRealtime();
        zzayq().zzjmd.set(zzxx().currentTimeMillis());
        long j = elapsedRealtime - this.zzjsb;
        if (z || j >= 1000) {
            zzayq().zzjme.set(j);
            zzayp().zzbba().zzj("Recording user engagement, ms", Long.valueOf(j));
            Bundle bundle = new Bundle();
            bundle.putLong("_et", j);
            zzcma.zza(zzayh().zzbcg(), bundle, true);
            zzayd().zzd("auto", "_e", bundle);
            this.zzjsb = elapsedRealtime;
            this.zzjsd.cancel();
            this.zzjsd.zzs(Math.max(0, DateUtils.MILLIS_PER_HOUR - zzayq().zzjme.get()));
            return true;
        }
        zzayp().zzbba().zzj("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(j));
        return false;
    }

    public final /* bridge */ /* synthetic */ void zzwj() {
        super.zzwj();
    }

    public final /* bridge */ /* synthetic */ zze zzxx() {
        return super.zzxx();
    }
}
