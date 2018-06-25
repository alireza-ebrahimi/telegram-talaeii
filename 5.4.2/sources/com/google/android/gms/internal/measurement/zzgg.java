package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.AppMeasurement.Event;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.android.gms.measurement.AppMeasurement.UserProperty;
import com.google.firebase.analytics.FirebaseAnalytics.C1796a;
import java.io.IOException;
import java.util.Map;

public final class zzgg extends zzjr implements zzei {
    @VisibleForTesting
    private static int zzalo = 65535;
    @VisibleForTesting
    private static int zzalp = 2;
    private final Map<String, Map<String, String>> zzalq = new C0464a();
    private final Map<String, Map<String, Boolean>> zzalr = new C0464a();
    private final Map<String, Map<String, Boolean>> zzals = new C0464a();
    private final Map<String, zzkm> zzalt = new C0464a();
    private final Map<String, Map<String, Integer>> zzalu = new C0464a();
    private final Map<String, String> zzalv = new C0464a();

    zzgg(zzjs zzjs) {
        super(zzjs);
    }

    private final zzkm zza(String str, byte[] bArr) {
        if (bArr == null) {
            return new zzkm();
        }
        zzabx zza = zzabx.zza(bArr, 0, bArr.length);
        zzacg zzkm = new zzkm();
        try {
            zzkm.zzb(zza);
            zzgf().zziz().zze("Parsed config. version, gmp_app_id", zzkm.zzatb, zzkm.zzadm);
            return zzkm;
        } catch (IOException e) {
            zzgf().zziv().zze("Unable to merge remote config. appId", zzfh.zzbl(str), e);
            return new zzkm();
        }
    }

    private static Map<String, String> zza(zzkm zzkm) {
        Map<String, String> c0464a = new C0464a();
        if (!(zzkm == null || zzkm.zzatd == null)) {
            for (zzkn zzkn : zzkm.zzatd) {
                if (zzkn != null) {
                    c0464a.put(zzkn.zzny, zzkn.value);
                }
            }
        }
        return c0464a;
    }

    private final void zza(String str, zzkm zzkm) {
        Map c0464a = new C0464a();
        Map c0464a2 = new C0464a();
        Map c0464a3 = new C0464a();
        if (!(zzkm == null || zzkm.zzate == null)) {
            for (zzkl zzkl : zzkm.zzate) {
                if (TextUtils.isEmpty(zzkl.name)) {
                    zzgf().zziv().log("EventConfig contained null event name");
                } else {
                    Object zzaj = Event.zzaj(zzkl.name);
                    if (!TextUtils.isEmpty(zzaj)) {
                        zzkl.name = zzaj;
                    }
                    c0464a.put(zzkl.name, zzkl.zzasy);
                    c0464a2.put(zzkl.name, zzkl.zzasz);
                    if (zzkl.zzata != null) {
                        if (zzkl.zzata.intValue() < zzalp || zzkl.zzata.intValue() > zzalo) {
                            zzgf().zziv().zze("Invalid sampling rate. Event name, sample rate", zzkl.name, zzkl.zzata);
                        } else {
                            c0464a3.put(zzkl.name, zzkl.zzata);
                        }
                    }
                }
            }
        }
        this.zzalr.put(str, c0464a);
        this.zzals.put(str, c0464a2);
        this.zzalu.put(str, c0464a3);
    }

    private final void zzbs(String str) {
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        if (this.zzalt.get(str) == null) {
            byte[] zzbd = zzje().zzbd(str);
            if (zzbd == null) {
                this.zzalq.put(str, null);
                this.zzalr.put(str, null);
                this.zzals.put(str, null);
                this.zzalt.put(str, null);
                this.zzalv.put(str, null);
                this.zzalu.put(str, null);
                return;
            }
            zzkm zza = zza(str, zzbd);
            this.zzalq.put(str, zza(zza));
            zza(str, zza);
            this.zzalt.put(str, zza);
            this.zzalv.put(str, null);
        }
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    protected final boolean zza(String str, byte[] bArr, String str2) {
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        zzkm zza = zza(str, bArr);
        if (zza == null) {
            return false;
        }
        zza(str, zza);
        this.zzalt.put(str, zza);
        this.zzalv.put(str, str2);
        this.zzalq.put(str, zza(zza));
        zzjq zzjd = zzjd();
        zzkf[] zzkfArr = zza.zzatf;
        Preconditions.checkNotNull(zzkfArr);
        for (zzkf zzkf : zzkfArr) {
            for (zzkg zzkg : zzkf.zzarz) {
                String zzaj = Event.zzaj(zzkg.zzasc);
                if (zzaj != null) {
                    zzkg.zzasc = zzaj;
                }
                for (zzkh zzkh : zzkg.zzasd) {
                    String zzaj2 = Param.zzaj(zzkh.zzask);
                    if (zzaj2 != null) {
                        zzkh.zzask = zzaj2;
                    }
                }
            }
            for (zzkj zzkj : zzkf.zzary) {
                String zzaj3 = UserProperty.zzaj(zzkj.zzasr);
                if (zzaj3 != null) {
                    zzkj.zzasr = zzaj3;
                }
            }
        }
        zzjd.zzje().zza(str, zzkfArr);
        try {
            zza.zzatf = null;
            byte[] bArr2 = new byte[zza.zzvv()];
            zza.zza(zzaby.zzb(bArr2, 0, bArr2.length));
            bArr = bArr2;
        } catch (IOException e) {
            zzgf().zziv().zze("Unable to serialize reduced-size config. Storing full config instead. appId", zzfh.zzbl(str), e);
        }
        zzhh zzje = zzje();
        Preconditions.checkNotEmpty(str);
        zzje.zzab();
        zzje.zzch();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr);
        try {
            if (((long) zzje.getWritableDatabase().update("apps", contentValues, "app_id = ?", new String[]{str})) == 0) {
                zzje.zzgf().zzis().zzg("Failed to update remote config (got 0). appId", zzfh.zzbl(str));
            }
        } catch (SQLiteException e2) {
            zzje.zzgf().zzis().zze("Error storing remote config. appId", zzfh.zzbl(str), e2);
        }
        return true;
    }

    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    protected final zzkm zzbt(String str) {
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        zzbs(str);
        return (zzkm) this.zzalt.get(str);
    }

    protected final String zzbu(String str) {
        zzab();
        return (String) this.zzalv.get(str);
    }

    protected final void zzbv(String str) {
        zzab();
        this.zzalv.put(str, null);
    }

    final void zzbw(String str) {
        zzab();
        this.zzalt.remove(str);
    }

    final boolean zzbx(String str) {
        return "1".equals(zze(str, "measurement.upload.blacklist_internal"));
    }

    final boolean zzby(String str) {
        return "1".equals(zze(str, "measurement.upload.blacklist_public"));
    }

    public final String zze(String str, String str2) {
        zzab();
        zzbs(str);
        Map map = (Map) this.zzalq.get(str);
        return map != null ? (String) map.get(str2) : null;
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

    public final /* bridge */ /* synthetic */ zzjy zzjc() {
        return super.zzjc();
    }

    public final /* bridge */ /* synthetic */ zzeb zzjd() {
        return super.zzjd();
    }

    public final /* bridge */ /* synthetic */ zzej zzje() {
        return super.zzje();
    }

    final boolean zzn(String str, String str2) {
        zzab();
        zzbs(str);
        if (zzbx(str) && zzkc.zzch(str2)) {
            return true;
        }
        if (zzby(str) && zzkc.zzcb(str2)) {
            return true;
        }
        Map map = (Map) this.zzalr.get(str);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str2);
        return bool == null ? false : bool.booleanValue();
    }

    final boolean zzo(String str, String str2) {
        zzab();
        zzbs(str);
        if (C1796a.ECOMMERCE_PURCHASE.equals(str2)) {
            return true;
        }
        Map map = (Map) this.zzals.get(str);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str2);
        return bool == null ? false : bool.booleanValue();
    }

    final int zzp(String str, String str2) {
        zzab();
        zzbs(str);
        Map map = (Map) this.zzalu.get(str);
        if (map == null) {
            return 1;
        }
        Integer num = (Integer) map.get(str2);
        return num == null ? 1 : num.intValue();
    }
}
