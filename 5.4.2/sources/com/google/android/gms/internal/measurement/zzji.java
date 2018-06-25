package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;

public final class zzji extends zzhi {
    private Handler handler;
    @VisibleForTesting
    private long zzaqd = zzbt().elapsedRealtime();
    private final zzeo zzaqe = new zzjj(this, this.zzacw);
    private final zzeo zzaqf = new zzjk(this, this.zzacw);

    zzji(zzgm zzgm) {
        super(zzgm);
    }

    private final void zzaf(long j) {
        zzab();
        zzkr();
        this.zzaqe.cancel();
        this.zzaqf.cancel();
        zzgf().zziz().zzg("Activity resumed, time", Long.valueOf(j));
        this.zzaqd = j;
        if (zzbt().currentTimeMillis() - zzgg().zzaks.get() > zzgg().zzaku.get()) {
            zzgg().zzakt.set(true);
            zzgg().zzakv.set(0);
        }
        if (zzgg().zzakt.get()) {
            this.zzaqe.zzh(Math.max(0, zzgg().zzakr.get() - zzgg().zzakv.get()));
        } else {
            this.zzaqf.zzh(Math.max(0, 3600000 - zzgg().zzakv.get()));
        }
    }

    private final void zzag(long j) {
        zzab();
        zzkr();
        this.zzaqe.cancel();
        this.zzaqf.cancel();
        zzgf().zziz().zzg("Activity paused, time", Long.valueOf(j));
        if (this.zzaqd != 0) {
            zzgg().zzakv.set(zzgg().zzakv.get() + (j - this.zzaqd));
        }
    }

    private final void zzkr() {
        synchronized (this) {
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
        }
    }

    private final void zzkt() {
        zzab();
        zzl(false);
        zzfu().zzk(zzbt().elapsedRealtime());
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    public final /* bridge */ /* synthetic */ void zzft() {
        super.zzft();
    }

    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    protected final boolean zzhh() {
        return false;
    }

    final void zzks() {
        this.zzaqe.cancel();
        this.zzaqf.cancel();
        this.zzaqd = 0;
    }

    public final boolean zzl(boolean z) {
        zzab();
        zzch();
        long elapsedRealtime = zzbt().elapsedRealtime();
        zzgg().zzaku.set(zzbt().currentTimeMillis());
        long j = elapsedRealtime - this.zzaqd;
        if (z || j >= 1000) {
            zzgg().zzakv.set(j);
            zzgf().zziz().zzg("Recording user engagement, ms", Long.valueOf(j));
            Bundle bundle = new Bundle();
            bundle.putLong("_et", j);
            zzig.zza(zzfz().zzkk(), bundle, true);
            zzfv().logEvent("auto", "_e", bundle);
            this.zzaqd = elapsedRealtime;
            this.zzaqf.cancel();
            this.zzaqf.zzh(Math.max(0, 3600000 - zzgg().zzakv.get()));
            return true;
        }
        zzgf().zziz().zzg("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(j));
        return false;
    }
}
