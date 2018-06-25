package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.GuardedBy;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement;

public final class zzcjj extends zzcli {
    private long zzjft = -1;
    private char zzjkg = '\u0000';
    @GuardedBy("this")
    private String zzjkh;
    private final zzcjl zzjki = new zzcjl(this, 6, false, false);
    private final zzcjl zzjkj = new zzcjl(this, 6, true, false);
    private final zzcjl zzjkk = new zzcjl(this, 6, false, true);
    private final zzcjl zzjkl = new zzcjl(this, 5, false, false);
    private final zzcjl zzjkm = new zzcjl(this, 5, true, false);
    private final zzcjl zzjkn = new zzcjl(this, 5, false, true);
    private final zzcjl zzjko = new zzcjl(this, 4, false, false);
    private final zzcjl zzjkp = new zzcjl(this, 3, false, false);
    private final zzcjl zzjkq = new zzcjl(this, 2, false, false);

    zzcjj(zzckj zzckj) {
        super(zzckj);
    }

    @Hide
    static String zza(boolean z, String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            Object obj4 = "";
        }
        Object zzb = zzb(z, obj);
        Object zzb2 = zzb(z, obj2);
        Object zzb3 = zzb(z, obj3);
        StringBuilder stringBuilder = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(obj4)) {
            stringBuilder.append(obj4);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(zzb)) {
            stringBuilder.append(str2);
            stringBuilder.append(zzb);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzb2)) {
            stringBuilder.append(str2);
            stringBuilder.append(zzb2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzb3)) {
            stringBuilder.append(str2);
            stringBuilder.append(zzb3);
        }
        return stringBuilder.toString();
    }

    @Hide
    private static String zzb(boolean z, Object obj) {
        if (obj == null) {
            return "";
        }
        Object valueOf = obj instanceof Integer ? Long.valueOf((long) ((Integer) obj).intValue()) : obj;
        if (valueOf instanceof Long) {
            if (!z) {
                return String.valueOf(valueOf);
            }
            if (Math.abs(((Long) valueOf).longValue()) < 100) {
                return String.valueOf(valueOf);
            }
            String str = String.valueOf(valueOf).charAt(0) == '-' ? "-" : "";
            String valueOf2 = String.valueOf(Math.abs(((Long) valueOf).longValue()));
            return new StringBuilder((String.valueOf(str).length() + 43) + String.valueOf(str).length()).append(str).append(Math.round(Math.pow(10.0d, (double) (valueOf2.length() - 1)))).append("...").append(str).append(Math.round(Math.pow(10.0d, (double) valueOf2.length()) - 1.0d)).toString();
        } else if (valueOf instanceof Boolean) {
            return String.valueOf(valueOf);
        } else {
            if (!(valueOf instanceof Throwable)) {
                return valueOf instanceof zzcjm ? ((zzcjm) valueOf).zzgim : z ? "-" : String.valueOf(valueOf);
            } else {
                Throwable th = (Throwable) valueOf;
                StringBuilder stringBuilder = new StringBuilder(z ? th.getClass().getName() : th.toString());
                String zzjt = zzjt(AppMeasurement.class.getCanonicalName());
                String zzjt2 = zzjt(zzckj.class.getCanonicalName());
                for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                    if (!stackTraceElement.isNativeMethod()) {
                        String className = stackTraceElement.getClassName();
                        if (className != null) {
                            className = zzjt(className);
                            if (className.equals(zzjt) || className.equals(zzjt2)) {
                                stringBuilder.append(": ");
                                stringBuilder.append(stackTraceElement);
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                return stringBuilder.toString();
            }
        }
    }

    private final String zzbbb() {
        String str;
        synchronized (this) {
            if (this.zzjkh == null) {
                this.zzjkh = (String) zzciz.zzjin.get();
            }
            str = this.zzjkh;
        }
        return str;
    }

    protected static Object zzjs(String str) {
        return str == null ? null : new zzcjm(str);
    }

    private static String zzjt(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf != -1 ? str.substring(0, lastIndexOf) : str;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    protected final void zza(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) {
        int i2 = 0;
        if (!z && zzae(i)) {
            zzm(i, zza(false, str, obj, obj2, obj3));
        }
        if (!z2 && i >= 5) {
            zzbq.checkNotNull(str);
            zzcli zzbbp = this.zzjev.zzbbp();
            if (zzbbp == null) {
                zzm(6, "Scheduler not set. Not logging error/warn");
            } else if (zzbbp.isInitialized()) {
                if (i >= 0) {
                    i2 = i;
                }
                if (i2 >= 9) {
                    i2 = 8;
                }
                zzbbp.zzh(new zzcjk(this, i2, str, obj, obj2, obj3));
            } else {
                zzm(6, "Scheduler not initialized. Not logging error/warn");
            }
        }
    }

    protected final boolean zzae(int i) {
        return Log.isLoggable(zzbbb(), i);
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

    public final zzcjl zzbau() {
        return this.zzjki;
    }

    public final zzcjl zzbav() {
        return this.zzjkj;
    }

    public final zzcjl zzbaw() {
        return this.zzjkl;
    }

    public final zzcjl zzbax() {
        return this.zzjkn;
    }

    public final zzcjl zzbay() {
        return this.zzjko;
    }

    public final zzcjl zzbaz() {
        return this.zzjkp;
    }

    public final zzcjl zzbba() {
        return this.zzjkq;
    }

    public final String zzbbc() {
        Pair zzabh = zzayq().zzjlm.zzabh();
        if (zzabh == null || zzabh == zzcju.zzjlk) {
            return null;
        }
        String valueOf = String.valueOf(zzabh.second);
        String str = (String) zzabh.first;
        return new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(str).length()).append(valueOf).append(":").append(str).toString();
    }

    @Hide
    protected final void zzm(int i, String str) {
        Log.println(i, zzbbb(), str);
    }

    public final /* bridge */ /* synthetic */ void zzwj() {
        super.zzwj();
    }

    public final /* bridge */ /* synthetic */ zze zzxx() {
        return super.zzxx();
    }
}
