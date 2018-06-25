package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzu;
import com.thin.downloadmanager.BuildConfig;
import java.lang.reflect.InvocationTargetException;

@Hide
public final class zzcik extends zzclh {
    private Boolean zzeba;

    zzcik(zzckj zzckj) {
        super(zzckj);
    }

    public static long zzazs() {
        return ((Long) zzciz.zzjjq.get()).longValue();
    }

    public static long zzazt() {
        return ((Long) zzciz.zzjiq.get()).longValue();
    }

    public static boolean zzazv() {
        return ((Boolean) zzciz.zzjil.get()).booleanValue();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final long zza(String str, zzcja<Long> zzcja) {
        if (str == null) {
            return ((Long) zzcja.get()).longValue();
        }
        Object zzam = zzaym().zzam(str, zzcja.getKey());
        if (TextUtils.isEmpty(zzam)) {
            return ((Long) zzcja.get()).longValue();
        }
        try {
            return ((Long) zzcja.get(Long.valueOf(Long.valueOf(zzam).longValue()))).longValue();
        } catch (NumberFormatException e) {
            return ((Long) zzcja.get()).longValue();
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

    public final boolean zzazr() {
        Boolean zzjf = zzjf("firebase_analytics_collection_deactivated");
        return zzjf != null && zzjf.booleanValue();
    }

    public final String zzazu() {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{"debug.firebase.analytics.app", ""});
        } catch (ClassNotFoundException e) {
            zzayp().zzbau().zzj("Could not find SystemProperties class", e);
        } catch (NoSuchMethodException e2) {
            zzayp().zzbau().zzj("Could not find SystemProperties.get() method", e2);
        } catch (IllegalAccessException e3) {
            zzayp().zzbau().zzj("Could not access SystemProperties.get()", e3);
        } catch (InvocationTargetException e4) {
            zzayp().zzbau().zzj("SystemProperties.get() threw an exception", e4);
        }
        return "";
    }

    public final int zzb(String str, zzcja<Integer> zzcja) {
        if (str == null) {
            return ((Integer) zzcja.get()).intValue();
        }
        Object zzam = zzaym().zzam(str, zzcja.getKey());
        if (TextUtils.isEmpty(zzam)) {
            return ((Integer) zzcja.get()).intValue();
        }
        try {
            return ((Integer) zzcja.get(Integer.valueOf(Integer.valueOf(zzam).intValue()))).intValue();
        } catch (NumberFormatException e) {
            return ((Integer) zzcja.get()).intValue();
        }
    }

    public final boolean zzc(String str, zzcja<Boolean> zzcja) {
        if (str == null) {
            return ((Boolean) zzcja.get()).booleanValue();
        }
        Object zzam = zzaym().zzam(str, zzcja.getKey());
        return TextUtils.isEmpty(zzam) ? ((Boolean) zzcja.get()).booleanValue() : ((Boolean) zzcja.get(Boolean.valueOf(Boolean.parseBoolean(zzam)))).booleanValue();
    }

    public final int zzje(@Size(min = 1) String str) {
        return zzb(str, zzciz.zzjjb);
    }

    @Nullable
    final Boolean zzjf(@Size(min = 1) String str) {
        Boolean bool = null;
        zzbq.zzgv(str);
        try {
            if (getContext().getPackageManager() == null) {
                zzayp().zzbau().log("Failed to load metadata: PackageManager is null");
            } else {
                ApplicationInfo applicationInfo = zzbih.zzdd(getContext()).getApplicationInfo(getContext().getPackageName(), 128);
                if (applicationInfo == null) {
                    zzayp().zzbau().log("Failed to load metadata: ApplicationInfo is null");
                } else if (applicationInfo.metaData == null) {
                    zzayp().zzbau().log("Failed to load metadata: Metadata bundle is null");
                } else if (applicationInfo.metaData.containsKey(str)) {
                    bool = Boolean.valueOf(applicationInfo.metaData.getBoolean(str));
                }
            }
        } catch (NameNotFoundException e) {
            zzayp().zzbau().zzj("Failed to load metadata: Package name not found", e);
        }
        return bool;
    }

    public final boolean zzjg(String str) {
        return BuildConfig.VERSION_NAME.equals(zzaym().zzam(str, "gaia_collection_enabled"));
    }

    final boolean zzjh(String str) {
        return zzc(str, zzciz.zzjju);
    }

    public final /* bridge */ /* synthetic */ void zzwj() {
        super.zzwj();
    }

    public final /* bridge */ /* synthetic */ zze zzxx() {
        return super.zzxx();
    }

    public final boolean zzzu() {
        if (this.zzeba == null) {
            synchronized (this) {
                if (this.zzeba == null) {
                    ApplicationInfo applicationInfo = getContext().getApplicationInfo();
                    String zzany = zzu.zzany();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        boolean z = str != null && str.equals(zzany);
                        this.zzeba = Boolean.valueOf(z);
                    }
                    if (this.zzeba == null) {
                        this.zzeba = Boolean.TRUE;
                        zzayp().zzbau().log("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzeba.booleanValue();
    }
}
