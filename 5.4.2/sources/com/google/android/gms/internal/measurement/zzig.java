package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.p022f.C0464a;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Map;

public final class zzig extends zzhi {
    @VisibleForTesting
    protected zzif zzaov;
    private volatile zzif zzaow;
    private zzif zzaox;
    private final Map<Activity, zzif> zzaoy = new C0464a();
    private zzif zzaoz;
    private String zzapa;

    public zzig(zzgm zzgm) {
        super(zzgm);
    }

    private final void zza(Activity activity, zzif zzif, boolean z) {
        zzif zzif2 = this.zzaow == null ? this.zzaox : this.zzaow;
        if (zzif.zzaos == null) {
            zzif = new zzif(zzif.zzul, zzbz(activity.getClass().getCanonicalName()), zzif.zzaot);
        }
        this.zzaox = this.zzaow;
        this.zzaow = zzif;
        zzge().zzc(new zzih(this, z, zzif2, zzif));
    }

    private final void zza(zzif zzif) {
        zzfu().zzk(zzbt().elapsedRealtime());
        if (zzgd().zzl(zzif.zzaou)) {
            zzif.zzaou = false;
        }
    }

    public static void zza(zzif zzif, Bundle bundle, boolean z) {
        if (bundle != null && zzif != null && (!bundle.containsKey("_sc") || z)) {
            if (zzif.zzul != null) {
                bundle.putString("_sn", zzif.zzul);
            } else {
                bundle.remove("_sn");
            }
            bundle.putString("_sc", zzif.zzaos);
            bundle.putLong("_si", zzif.zzaot);
        } else if (bundle != null && zzif == null && z) {
            bundle.remove("_sn");
            bundle.remove("_sc");
            bundle.remove("_si");
        }
    }

    @VisibleForTesting
    private static String zzbz(String str) {
        String[] split = str.split("\\.");
        String str2 = split.length > 0 ? split[split.length - 1] : TtmlNode.ANONYMOUS_REGION_ID;
        return str2.length() > 100 ? str2.substring(0, 100) : str2;
    }

    private final zzif zze(Activity activity) {
        Preconditions.checkNotNull(activity);
        zzif zzif = (zzif) this.zzaoy.get(activity);
        if (zzif != null) {
            return zzif;
        }
        zzif = new zzif(null, zzbz(activity.getClass().getCanonicalName()), zzgc().zzlk());
        this.zzaoy.put(activity, zzif);
        return zzif;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        if (bundle != null) {
            Bundle bundle2 = bundle.getBundle("com.google.firebase.analytics.screen_service");
            if (bundle2 != null) {
                this.zzaoy.put(activity, new zzif(bundle2.getString("name"), bundle2.getString("referrer_name"), bundle2.getLong(TtmlNode.ATTR_ID)));
            }
        }
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzaoy.remove(activity);
    }

    public final void onActivityPaused(Activity activity) {
        zzif zze = zze(activity);
        this.zzaox = this.zzaow;
        this.zzaow = null;
        zzge().zzc(new zzii(this, zze));
    }

    public final void onActivityResumed(Activity activity) {
        zza(activity, zze(activity), false);
        zzhh zzfu = zzfu();
        zzfu.zzge().zzc(new zzdx(zzfu, zzfu.zzbt().elapsedRealtime()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        if (bundle != null) {
            zzif zzif = (zzif) this.zzaoy.get(activity);
            if (zzif != null) {
                Bundle bundle2 = new Bundle();
                bundle2.putLong(TtmlNode.ATTR_ID, zzif.zzaot);
                bundle2.putString("name", zzif.zzul);
                bundle2.putString("referrer_name", zzif.zzaos);
                bundle.putBundle("com.google.firebase.analytics.screen_service", bundle2);
            }
        }
    }

    public final void setCurrentScreen(Activity activity, String str, String str2) {
        if (!zzec.isMainThread()) {
            zzgf().zziv().log("setCurrentScreen must be called from the main thread");
        } else if (this.zzaow == null) {
            zzgf().zziv().log("setCurrentScreen cannot be called while no activity active");
        } else if (this.zzaoy.get(activity) == null) {
            zzgf().zziv().log("setCurrentScreen must be called with an activity in the activity lifecycle");
        } else {
            if (str2 == null) {
                str2 = zzbz(activity.getClass().getCanonicalName());
            }
            boolean equals = this.zzaow.zzaos.equals(str2);
            boolean zzs = zzkc.zzs(this.zzaow.zzul, str);
            if (equals && zzs) {
                zzgf().zziw().log("setCurrentScreen cannot be called with the same class and name");
            } else if (str != null && (str.length() <= 0 || str.length() > 100)) {
                zzgf().zziv().zzg("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            } else if (str2 == null || (str2.length() > 0 && str2.length() <= 100)) {
                Object obj;
                zzfj zziz = zzgf().zziz();
                String str3 = "Setting current screen to name, class";
                if (str == null) {
                    obj = "null";
                } else {
                    String str4 = str;
                }
                zziz.zze(str3, obj, str2);
                zzif zzif = new zzif(str, str2, zzgc().zzlk());
                this.zzaoy.put(activity, zzif);
                zza(activity, zzif, true);
            } else {
                zzgf().zziv().zzg("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            }
        }
    }

    public final void zza(String str, zzif zzif) {
        zzab();
        synchronized (this) {
            if (this.zzapa == null || this.zzapa.equals(str) || zzif != null) {
                this.zzapa = str;
                this.zzaoz = zzif;
            }
        }
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

    public final zzif zzkk() {
        zzch();
        zzab();
        return this.zzaov;
    }

    public final zzif zzkl() {
        zzfs();
        return this.zzaow;
    }
}
