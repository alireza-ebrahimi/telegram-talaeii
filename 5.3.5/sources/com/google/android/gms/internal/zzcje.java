package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.util.zze;
import com.google.firebase.iid.FirebaseInstanceId;
import com.stripe.android.model.Card;
import java.math.BigInteger;
import java.util.Locale;

public final class zzcje extends zzcli {
    private String zzcm;
    private String zzina;
    private String zziqn;
    private String zzjfl;
    private String zzjfs;
    private long zzjfw;
    private int zzjgl;
    private int zzjjy;
    private long zzjjz;

    zzcje(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final String zzayu() {
        zzwj();
        try {
            return FirebaseInstanceId.getInstance().getId();
        } catch (IllegalStateException e) {
            zzayp().zzbaw().log("Failed to retrieve Firebase Instance Id");
            return null;
        }
    }

    final String getAppId() {
        zzyk();
        return this.zzcm;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    final String getGmpAppId() {
        zzyk();
        return this.zzjfl;
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
        return true;
    }

    protected final void zzbap() {
        int i = 1;
        String str = "unknown";
        String str2 = Card.UNKNOWN;
        int i2 = Integer.MIN_VALUE;
        String str3 = Card.UNKNOWN;
        String packageName = getContext().getPackageName();
        PackageManager packageManager = getContext().getPackageManager();
        if (packageManager == null) {
            zzayp().zzbau().zzj("PackageManager is null, app identity information might be inaccurate. appId", zzcjj.zzjs(packageName));
        } else {
            try {
                str = packageManager.getInstallerPackageName(packageName);
            } catch (IllegalArgumentException e) {
                zzayp().zzbau().zzj("Error retrieving app installer package name. appId", zzcjj.zzjs(packageName));
            }
            if (str == null) {
                str = "manual_install";
            } else if ("com.android.vending".equals(str)) {
                str = "";
            }
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
                if (packageInfo != null) {
                    CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                    if (!TextUtils.isEmpty(applicationLabel)) {
                        str3 = applicationLabel.toString();
                    }
                    str2 = packageInfo.versionName;
                    i2 = packageInfo.versionCode;
                }
            } catch (NameNotFoundException e2) {
                zzayp().zzbau().zze("Error retrieving package info. appId, appName", zzcjj.zzjs(packageName), str3);
            }
        }
        this.zzcm = packageName;
        this.zzjfs = str;
        this.zzina = str2;
        this.zzjjy = i2;
        this.zziqn = str3;
        this.zzjjz = 0;
        Status zzcl = zzbz.zzcl(getContext());
        int i3 = (zzcl == null || !zzcl.isSuccess()) ? 0 : 1;
        if (i3 == 0) {
            if (zzcl == null) {
                zzayp().zzbau().log("GoogleService failed to initialize (no status)");
            } else {
                zzayp().zzbau().zze("GoogleService failed to initialize, status", Integer.valueOf(zzcl.getStatusCode()), zzcl.getStatusMessage());
            }
        }
        if (i3 != 0) {
            Boolean zzjf = zzayr().zzjf("firebase_analytics_collection_enabled");
            if (zzayr().zzazr()) {
                zzayp().zzbay().log("Collection disabled with firebase_analytics_collection_deactivated=1");
                i3 = 0;
            } else if (zzjf != null && !zzjf.booleanValue()) {
                zzayp().zzbay().log("Collection disabled with firebase_analytics_collection_enabled=0");
                i3 = 0;
            } else if (zzjf == null && zzbz.zzakr()) {
                zzayp().zzbay().log("Collection disabled with google_app_measurement_enable=0");
                i3 = 0;
            } else {
                zzayp().zzbba().log("Collection enabled");
                i3 = 1;
            }
        } else {
            i3 = 0;
        }
        this.zzjfl = "";
        this.zzjfw = 0;
        try {
            String zzakq = zzbz.zzakq();
            if (TextUtils.isEmpty(zzakq)) {
                zzakq = "";
            }
            this.zzjfl = zzakq;
            if (i3 != 0) {
                zzayp().zzbba().zze("App package, google app id", this.zzcm, this.zzjfl);
            }
        } catch (IllegalStateException e3) {
            zzayp().zzbau().zze("getGoogleAppId or isMeasurementEnabled failed with exception. appId", zzcjj.zzjs(packageName), e3);
        }
        if (VERSION.SDK_INT >= 16) {
            if (!zzbif.zzdb(getContext())) {
                i = 0;
            }
            this.zzjgl = i;
            return;
        }
        this.zzjgl = 0;
    }

    @WorkerThread
    final String zzbaq() {
        zzayl().zzbcr().nextBytes(new byte[16]);
        return String.format(Locale.US, "%032x", new Object[]{new BigInteger(1, r0)});
    }

    final int zzbar() {
        zzyk();
        return this.zzjjy;
    }

    final int zzbas() {
        zzyk();
        return this.zzjgl;
    }

    @WorkerThread
    final zzcif zzjo(String str) {
        zzwj();
        String appId = getAppId();
        String gmpAppId = getGmpAppId();
        zzyk();
        String str2 = this.zzina;
        long zzbar = (long) zzbar();
        zzyk();
        String str3 = this.zzjfs;
        zzyk();
        zzwj();
        if (this.zzjjz == 0) {
            this.zzjjz = this.zzjev.zzayl().zzab(getContext(), getContext().getPackageName());
        }
        long j = this.zzjjz;
        boolean isEnabled = this.zzjev.isEnabled();
        boolean z = !zzayq().zzjmf;
        String zzayu = zzayu();
        zzyk();
        long zzbbw = this.zzjev.zzbbw();
        int zzbas = zzbas();
        Boolean zzjf = zzayr().zzjf("google_analytics_adid_collection_enabled");
        boolean z2 = zzjf == null || zzjf.booleanValue();
        return new zzcif(appId, gmpAppId, str2, zzbar, str3, 12211, j, str, isEnabled, z, zzayu, 0, zzbbw, zzbas, Boolean.valueOf(z2).booleanValue());
    }

    public final /* bridge */ /* synthetic */ void zzwj() {
        super.zzwj();
    }

    public final /* bridge */ /* synthetic */ zze zzxx() {
        return super.zzxx();
    }
}
