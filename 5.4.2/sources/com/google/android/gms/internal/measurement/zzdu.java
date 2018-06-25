package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.p022f.C0464a;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.util.Map;

public final class zzdu extends zzhh {
    private final Map<String, Long> zzadf = new C0464a();
    private final Map<String, Integer> zzadg = new C0464a();
    private long zzadh;

    public zzdu(zzgm zzgm) {
        super(zzgm);
    }

    private final void zza(long j, zzif zzif) {
        if (zzif == null) {
            zzgf().zziz().log("Not logging ad exposure. No active activity");
        } else if (j < 1000) {
            zzgf().zziz().zzg("Not logging ad exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("_xt", j);
            zzig.zza(zzif, bundle, true);
            zzfv().logEvent("am", "_xa", bundle);
        }
    }

    private final void zza(String str, long j) {
        zzfs();
        zzab();
        Preconditions.checkNotEmpty(str);
        if (this.zzadg.isEmpty()) {
            this.zzadh = j;
        }
        Integer num = (Integer) this.zzadg.get(str);
        if (num != null) {
            this.zzadg.put(str, Integer.valueOf(num.intValue() + 1));
        } else if (this.zzadg.size() >= 100) {
            zzgf().zziv().log("Too many ads visible");
        } else {
            this.zzadg.put(str, Integer.valueOf(1));
            this.zzadf.put(str, Long.valueOf(j));
        }
    }

    private final void zza(String str, long j, zzif zzif) {
        if (zzif == null) {
            zzgf().zziz().log("Not logging ad unit exposure. No active activity");
        } else if (j < 1000) {
            zzgf().zziz().zzg("Not logging ad unit exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("_ai", str);
            bundle.putLong("_xt", j);
            zzig.zza(zzif, bundle, true);
            zzfv().logEvent("am", "_xu", bundle);
        }
    }

    private final void zzb(String str, long j) {
        zzfs();
        zzab();
        Preconditions.checkNotEmpty(str);
        Integer num = (Integer) this.zzadg.get(str);
        if (num != null) {
            zzif zzkk = zzfz().zzkk();
            int intValue = num.intValue() - 1;
            if (intValue == 0) {
                this.zzadg.remove(str);
                Long l = (Long) this.zzadf.get(str);
                if (l == null) {
                    zzgf().zzis().log("First ad unit exposure time was never set");
                } else {
                    long longValue = j - l.longValue();
                    this.zzadf.remove(str);
                    zza(str, longValue, zzkk);
                }
                if (!this.zzadg.isEmpty()) {
                    return;
                }
                if (this.zzadh == 0) {
                    zzgf().zzis().log("First ad exposure time was never set");
                    return;
                }
                zza(j - this.zzadh, zzkk);
                this.zzadh = 0;
                return;
            }
            this.zzadg.put(str, Integer.valueOf(intValue));
            return;
        }
        zzgf().zzis().zzg("Call to endAdUnitExposure for unknown ad unit id", str);
    }

    private final void zzl(long j) {
        for (String put : this.zzadf.keySet()) {
            this.zzadf.put(put, Long.valueOf(j));
        }
        if (!this.zzadf.isEmpty()) {
            this.zzadh = j;
        }
    }

    public final void beginAdUnitExposure(String str) {
        if (str == null || str.length() == 0) {
            zzgf().zzis().log("Ad unit id must be a non-empty string");
            return;
        }
        zzge().zzc(new zzdv(this, str, zzbt().elapsedRealtime()));
    }

    public final void endAdUnitExposure(String str) {
        if (str == null || str.length() == 0) {
            zzgf().zzis().log("Ad unit id must be a non-empty string");
            return;
        }
        zzge().zzc(new zzdw(this, str, zzbt().elapsedRealtime()));
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

    public final void zzk(long j) {
        zzif zzkk = zzfz().zzkk();
        for (String str : this.zzadf.keySet()) {
            zza(str, j - ((Long) this.zzadf.get(str)).longValue(), zzkk);
        }
        if (!this.zzadf.isEmpty()) {
            zza(j - this.zzadh, zzkk);
        }
        zzl(j);
    }
}
