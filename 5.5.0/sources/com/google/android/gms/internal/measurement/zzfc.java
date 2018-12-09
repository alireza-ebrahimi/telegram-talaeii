package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.wrappers.InstantApps;
import com.google.firebase.iid.FirebaseInstanceId;
import java.math.BigInteger;
import java.util.Locale;

public final class zzfc extends zzhi {
    private String zzadm;
    private String zzadt;
    private long zzadx;
    private int zzaen;
    private int zzain;
    private long zzaio;
    private String zztg;
    private String zzth;
    private String zzti;

    zzfc(zzgm zzgm) {
        super(zzgm);
    }

    private final String zzgl() {
        String str = null;
        zzab();
        zzfs();
        if (!zzgh().zzax(this.zzti) || this.zzacw.isEnabled()) {
            try {
                str = FirebaseInstanceId.m8755a().m8775c();
            } catch (IllegalStateException e) {
                zzgf().zziv().log("Failed to retrieve Firebase Instance Id");
            }
        }
        return str;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    final String getGmpAppId() {
        zzch();
        return this.zzadm;
    }

    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    final String zzah() {
        zzch();
        return this.zzti;
    }

    final zzdz zzbh(String str) {
        zzab();
        zzfs();
        String zzah = zzah();
        String gmpAppId = getGmpAppId();
        zzch();
        String str2 = this.zzth;
        long zzip = (long) zzip();
        zzch();
        String str3 = this.zzadt;
        zzch();
        zzab();
        if (this.zzaio == 0) {
            this.zzaio = this.zzacw.zzgc().zzd(getContext(), getContext().getPackageName());
        }
        long j = this.zzaio;
        boolean isEnabled = this.zzacw.isEnabled();
        boolean z = !zzgg().zzakw;
        String zzgl = zzgl();
        zzch();
        long j2 = this.zzadx;
        long zzkb = this.zzacw.zzkb();
        int zziq = zziq();
        zzhh zzgh = zzgh();
        zzgh.zzfs();
        Boolean zzar = zzgh.zzar("google_analytics_adid_collection_enabled");
        boolean z2 = zzar == null || zzar.booleanValue();
        boolean booleanValue = Boolean.valueOf(z2).booleanValue();
        zzgh = zzgh();
        zzgh.zzfs();
        zzar = zzgh.zzar("google_analytics_ssaid_collection_enabled");
        z2 = zzar == null || zzar.booleanValue();
        return new zzdz(zzah, gmpAppId, str2, zzip, str3, 12451, j, str, isEnabled, z, zzgl, j2, zzkb, zziq, booleanValue, Boolean.valueOf(z2).booleanValue(), zzgg().zzjl());
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
        return true;
    }

    protected final void zzin() {
        int i = 1;
        String str = "unknown";
        String str2 = "Unknown";
        int i2 = Integer.MIN_VALUE;
        String str3 = "Unknown";
        String packageName = getContext().getPackageName();
        PackageManager packageManager = getContext().getPackageManager();
        if (packageManager == null) {
            zzgf().zzis().zzg("PackageManager is null, app identity information might be inaccurate. appId", zzfh.zzbl(packageName));
        } else {
            try {
                str = packageManager.getInstallerPackageName(packageName);
            } catch (IllegalArgumentException e) {
                zzgf().zzis().zzg("Error retrieving app installer package name. appId", zzfh.zzbl(packageName));
            }
            if (str == null) {
                str = "manual_install";
            } else if ("com.android.vending".equals(str)) {
                str = TtmlNode.ANONYMOUS_REGION_ID;
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
                zzgf().zzis().zze("Error retrieving package info. appId, appName", zzfh.zzbl(packageName), str3);
            }
        }
        this.zzti = packageName;
        this.zzadt = str;
        this.zzth = str2;
        this.zzain = i2;
        this.zztg = str3;
        this.zzaio = 0;
        zzgi();
        Status initialize = GoogleServices.initialize(getContext());
        int i3 = (initialize == null || !initialize.isSuccess()) ? 0 : 1;
        if (i3 == 0) {
            if (initialize == null) {
                zzgf().zzis().log("GoogleService failed to initialize (no status)");
            } else {
                zzgf().zzis().zze("GoogleService failed to initialize, status", Integer.valueOf(initialize.getStatusCode()), initialize.getStatusMessage());
            }
        }
        if (i3 != 0) {
            Boolean zzhk = zzgh().zzhk();
            if (zzgh().zzhj()) {
                zzgf().zzix().log("Collection disabled with firebase_analytics_collection_deactivated=1");
                i3 = 0;
            } else if (zzhk != null && !zzhk.booleanValue()) {
                zzgf().zzix().log("Collection disabled with firebase_analytics_collection_enabled=0");
                i3 = 0;
            } else if (zzhk == null && GoogleServices.isMeasurementExplicitlyDisabled()) {
                zzgf().zzix().log("Collection disabled with google_app_measurement_enable=0");
                i3 = 0;
            } else {
                zzgf().zziz().log("Collection enabled");
                i3 = 1;
            }
        } else {
            i3 = 0;
        }
        this.zzadm = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzadx = 0;
        zzgi();
        if (this.zzacw.zzka() != null) {
            this.zzadm = this.zzacw.zzka();
        } else {
            try {
                String googleAppId = GoogleServices.getGoogleAppId();
                if (TextUtils.isEmpty(googleAppId)) {
                    googleAppId = TtmlNode.ANONYMOUS_REGION_ID;
                }
                this.zzadm = googleAppId;
                if (i3 != 0) {
                    zzgf().zziz().zze("App package, google app id", this.zzti, this.zzadm);
                }
            } catch (IllegalStateException e3) {
                zzgf().zzis().zze("getGoogleAppId or isMeasurementEnabled failed with exception. appId", zzfh.zzbl(packageName), e3);
            }
        }
        if (VERSION.SDK_INT >= 16) {
            if (!InstantApps.isInstantApp(getContext())) {
                i = 0;
            }
            this.zzaen = i;
            return;
        }
        this.zzaen = 0;
    }

    final String zzio() {
        zzgc().zzll().nextBytes(new byte[16]);
        return String.format(Locale.US, "%032x", new Object[]{new BigInteger(1, r0)});
    }

    final int zzip() {
        zzch();
        return this.zzain;
    }

    final int zziq() {
        zzch();
        return this.zzaen;
    }
}
